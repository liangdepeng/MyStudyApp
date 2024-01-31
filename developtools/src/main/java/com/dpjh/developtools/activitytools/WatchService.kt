package com.dpjh.developtools.activitytools

import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dpjh.developtools.Constants
import java.text.SimpleDateFormat
import java.util.*

class WatchService : Service() {

    private val TAG = "WatchService"

    private var taskkTimer: Timer? = null

    override fun onCreate() {
        super.onCreate()
      //  Log.e(TAG,"onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (taskkTimer == null) {
            taskkTimer = Timer()
            taskkTimer?.scheduleAtFixedRate(MyTask(), 0L, 1000L)
            Log.e(TAG,"taskkTimer == null")
        }
      //  Log.e(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
      //  Log.e(TAG,"onTaskRemoved")
    }

    override fun onDestroy() {
        super.onDestroy()
      //  Log.e(TAG,"onDestroy")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
      //  Log.e(TAG,"onStart")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    internal inner class MyTask : TimerTask() {

        private val timeFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")

        override fun run() {
            queryTopActivity(this@WatchService)
        }

        private fun queryTopActivity(context: Context) {
            val usageStatsManager =
                context.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
            val endTime = System.currentTimeMillis()
            val startTime = endTime - 5 * 60 * 1000
            val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
            var pkgName = ""
            var className = ""
            var time = ""
            while (usageEvents.hasNextEvent()) {
                val event = UsageEvents.Event()
                usageEvents.getNextEvent(event)
                when (event.eventType) {
                    UsageEvents.Event.ACTIVITY_RESUMED -> {
                        className = event.className
                        pkgName = event.packageName
                        time = timeFormat.format(Date(event.timeStamp))
                    }
                    UsageEvents.Event.ACTIVITY_PAUSED -> {
                        if (TextUtils.equals(event.packageName, pkgName)) {
                            pkgName = ""
                        }
                    }
                }
            }
//
            if (pkgName.isNotEmpty()) {

                LocalBroadcastManager.getInstance(this@WatchService)
                    .sendBroadcast(Intent(Constants.ACTION_CURRENT_ACTIVITY)
                        .putExtra("currentPackageName",pkgName)
                        .putExtra("currentActivity",className)
                        .putExtra("currentTime",time))

                FloatWindowManager.getInstance().updateCurrentData(
                    PackageUtils.getAppName(this@WatchService,pkgName),pkgName
                    ,className,PackageUtils.getAppIcon(this@WatchService,pkgName))

             //   Log.e(TAG, "ONRESUME $pkgName  $className  $time")
            }
        }
    }
}