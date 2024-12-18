package com.plugin.board

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.datatypes.MqttUtf8String
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.zcapi
import io.github.cakioe.Signatory
import java.io.File
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.timerTask

enum class Method(val value: String) {
    SHUTDOWN("android.shutdown"), // 关机, turn off the power
    REBOOT("android.reboot"), // 重启, reboot the machine
    GET_ENV("android.env.get"), // 环境参数, get env arguments
    PUT_BRIGHTNESS("android.brightness.put"), // 亮度, change the brightness of android system
    SET_POWER("android.schedule.power"), // 定时关机, timed shutdown
    DEFAULT("android.heartbeat"), // 心跳, heartbeat of machine
    OFFLINE("android.offline"), // 设备下线通知（遗嘱消息）, message of machine offline<mqtt>
}

/**
 * service of `TaskService`
 */
class TaskService : Service() {
    private lateinit var client: Mqtt5AsyncClient
    private lateinit var timer: Timer
    private lateinit var signer: Signatory
    private var options: PluginOptions? = null

    // android client publish topic for server
    private val publishTopic: String = "/server/android"

    // android client subscribe topic from server
    private lateinit var subscribeTopic: String
    private lateinit var no: String

    /**
     * displayer: the display board of screen, from`zc`
     */
    private val displayer = zcapi()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        this.displayer.getContext(applicationContext)
    }

    @SuppressLint("NewApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val no: String? = intent?.getStringExtra("no")
        val options: String? = intent?.getStringExtra("options")
        if (no == null || options == null) {
            return START_STICKY
        }

        this.no = no.toString()
        this.subscribeTopic = "/no/${this.no}"

        Gson().fromJson(options, PluginOptions::class.java)?.let { it ->
            this.options = it
            this.signer = Signatory(it.app_key)

            this.client = MqttClient.builder()
                .useMqttVersion5()
                .identifier(this.no)
                .serverHost(it.broker)
                .serverPort(it.port)
                .buildAsync()
            this.performLongTask()
        }

        return START_STICKY
    }

    /**
     * service of `mqtt`
     */
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun performLongTask() {
        val ctx = this
        try {
            //send an async connect
            val ack = this.client.connectWith().cleanStart(true).noSessionExpiry().keepAlive(60)
                .simpleAuth()
                .username(this.options!!.username)
                .password(this.options!!.password.toByteArray())
                .applySimpleAuth().send().whenComplete { _, throwable ->
                    if (throwable != null) {
                        throwable.printStackTrace()
                        return@whenComplete
                    }

                    // 每隔 5分钟 发送心跳检测
                    this.timer = Timer()
                    this.timer.schedule(timerTask {
                        heartbeat()
                    }, 0, 300000) // 300000 毫秒
                }.join()

            if (!ack.isSessionPresent) {
                //only the call to join is blocking
                this.client.subscribeWith().topicFilter(this.subscribeTopic)
                    .qos(MqttQos.EXACTLY_ONCE)
                    .callback { publish: Mqtt5Publish ->
                        try {
                            val params = String(publish.payloadAsBytes, StandardCharsets.UTF_8)
                            val payload: Map<String, Any> = signer.decryptBase64String(params)

                            val method = payload["method"]
                            val sign = payload["sign"]
                            if (method == null || sign == null) {
                                return@callback
                            }

                            if (sign !is String || !this.signer.checkSignature(payload, sign)) {
                                this.logger("checkout.log", payload)
                                return@callback
                            }

                            /**
                             * store payload log
                             */
                            this.logger("payload.log", payload)

                            when (method) {
                                Method.SHUTDOWN.value -> {
                                    this.displayer.shutDown()
                                    this.onDestroy()
                                }

                                Method.REBOOT.value -> {
                                    this.displayer.reboot()
                                    this.onDestroy()
                                }

                                else -> TODO()
                            }
                        } catch (e: Exception) {
                            if (e.message != null) {
                                this.logger("subscribe.log", mapOf("error" to e.message as Any))
                            }
                            return@callback
                        }
                    }.send()
            }

        } catch (e: Exception) {
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        } finally {
            if (!client.state.isConnected) {
                this.logger("connect.log", mapOf("error" to "connect fail"))
            }
        }
    }

    /**
     * heartbeat for android online status
     *
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun heartbeat() {
        val payload: Map<String, Any> = mapOf(
            "client_id" to this.no,
            "method" to Method.DEFAULT.value,
        )
        val code = this.signer.toBase64String(payload)
        this.client.publishWith().topic(this.publishTopic)
            .payload(code.toByteArray(StandardCharsets.UTF_8))
            .contentType(MqttUtf8String.of("application/base64"))
            .qos(MqttQos.EXACTLY_ONCE)
            .retain(false)
            .send()
    }

    /**
     * 设备下线通知（遗嘱消息）
     *
     */
    private fun offline() {
        val payload: Map<String, Any> = mapOf(
            "client_id" to this.no,
            "method" to Method.OFFLINE.value,
        )
        this.logger("offline.log", payload)

        val code = this.signer.toBase64String(payload)
        this.client.publishWith().topic(this.publishTopic)
            .payload(code.toByteArray(StandardCharsets.UTF_8))
            .contentType(MqttUtf8String.of("application/base64"))
            .qos(MqttQos.EXACTLY_ONCE)
            .retain(false)
            .send()
    }

    /**
     * record log for android
     *
     */
    private fun logger(filename: String, payload: Map<String, Any>) {
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).format(Date())
        val tmpl = "$now | ${Gson().toJson(payload)}\n"

        val buf = File(this.applicationContext.getExternalFilesDir(null), filename)
        buf.appendText(tmpl)
    }

    override fun onDestroy() {
        super.onDestroy()

        // 设备下线
        this.offline()

        // 停止定时器
        this.timer.apply {
            this.cancel()
        }

        // 断开客户端连接
        this.client.apply {
            if (this.state.isConnected) {
                client.unsubscribeWith().topicFilter(subscribeTopic).send()
                client.disconnect()
            }
        }
    }
}