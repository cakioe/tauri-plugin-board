package com.plugin.board
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

/**
 * service of `TaskService`
 */
class TaskService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        println("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.performLongTask()
        return START_STICKY
    }

    /**
     * service of `mqtt`
     */
    private fun performLongTask() {
        Toast.makeText(this.applicationContext, "onStartCommand", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }
}