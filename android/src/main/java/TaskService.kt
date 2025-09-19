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
import com.plugin.board.ui.initialize
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
     * ui: the display board of screen, from`zc`
     */
    private val board = initialize(Build.MODEL)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * Initializes the board instance with the application context.
     *
     * Called when the service is created.
     */
    override fun onCreate() {
        super.onCreate()
        this.board.initialize(applicationContext)
    }

    /**
     * Starts the service with the given intent and flags.
     *
     * Starts the `Mqtt5AsyncClient` with the given intent and flags.
     * The intent must contain the "no" and "options" extra strings.
     * The "options" extra string must contain the JSON of a `PluginOptions` object.
     *
     * @param intent the intent to start the service with
     * @param flags the flags to start the service with
     * @param startId the start ID of the service
     * @return the result of starting the service
     */
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
     * Long-running operation performing the initialization of the MQTT client.
     *
     * This function is called when the service is started and is responsible for
     * initializing the MQTT client and connecting to the MQTT broker.
     *
     * If the connection is successful, it will start a timer to send a heartbeat every 5 minutes.
     * If the connection is unsuccessful, it will log an error message.
     *
     * @throws Exception if an error occurs while connecting to the MQTT broker
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
                                    this.board.shutdown()
                                    this.onDestroy()
                                }

                                Method.REBOOT.value -> {
                                    this.board.reboot()
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
     * Send a heartbeat message to the MQTT broker.
     *
     * This function sends a message to the MQTT broker with the topic
     * specified in `publishTopic` and the payload specified in `payload`.
     * The message is sent with the QoS of EXACTLY_ONCE and the retain flag
     * set to false.
     *
     * The payload is a Map containing the client ID and the method
     * DEFAULT.
     *
     * @throws Exception if the message cannot be sent
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
     * Publish an offline message to the MQTT broker.
     *
     * This function sends a message to the MQTT broker with the topic
     * specified in `publishTopic` and the payload specified in `payload`.
     * The message is sent with the QoS of EXACTLY_ONCE and the retain flag
     * set to false.
     *
     * The payload is a Map containing the client ID and the method
     * OFFLINE.
     *
     * The message is logged in the file specified in `filename`.
     *
     * @throws Exception if the message cannot be sent
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
     * Writes a log message to the file specified by `filename`.
     *
     * The log message is in the format of "yyyy-MM-dd HH:mm:ss:SSS | <payload>\n"
     * where `<payload>` is the JSON representation of `payload`.
     *
     * @param filename the name of the file to write to
     * @param payload the payload to write to the file
     */
    private fun logger(filename: String, payload: Map<String, Any>) {
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).format(Date())
        val tmpl = "$now | ${Gson().toJson(payload)}\n"

        val buf = File(this.applicationContext.getExternalFilesDir(null), filename)
        buf.appendText(tmpl)
    }

    /**
     * Called by the system to indicate that the service is no longer
     * used and is being released. The service should clean up all its
     * resources and stop any ongoing operations.
     *
     * This implementation calls the following methods in order:
     * 1. `offline`
     * 2. `cancel` on the timer
     * 3. `unsubscribeWith` on the client with the topic filter specified in
     *     `subscribeTopic`
     * 4. `disconnect` on the client
     */
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