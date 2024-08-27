package com.plugin.board
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.gson.JsonParser
import java.io.File
import com.zcapi

enum class Method(val value: String) {
    SHUTDOWN("android.shutdown"),
    REBOOT("android.reboot"),
    GET_ENV("android.env.get"),
    PUT_BRIGHTNESS("android.brightness.put"),
    SET_POWER("android.schedule.power"),
}

/**
 * service of `TaskService`
 */
class TaskService: Service() {
    private var displayer = zcapi()
    private var filename: String = "store.bin"
    private var key: String = "MACHINE_SETTING"
    private lateinit var no: String
    private lateinit var topic: String
    private lateinit var method: Method

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        this.displayer.getContext(this)

        val file = File(this.applicationContext.getExternalFilesDir(null), this.filename)
        if (!file.exists()) return

        file.readText(Charsets.UTF_8).apply {
            JsonParser.parseString(this).asJsonObject.getAsJsonObject(key).get("machine_no").asString?.let {
                no = it
                topic = "/no/${no}"
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (this.no.isNotEmpty()) {
            this.performLongTask()
        }

        return START_STICKY
    }

    /**
     * service of `mqtt`
     */
    private fun performLongTask() {
        val m = this.displayer.buildModel
        if (m.startsWith("zc") || m.startsWith("ZC")) {
            when (this.method) {
                Method.SHUTDOWN -> {
                    this.displayer.shutDown()
                }
                Method.REBOOT -> {
                    this.displayer.reboot()
                }
                Method.GET_ENV -> {
                    TODO("Not yet implemented")
                }
                Method.PUT_BRIGHTNESS -> {
                    TODO("Not yet implemented")
                }
                Method.SET_POWER -> {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TODO("Not yet implemented")
    }
}