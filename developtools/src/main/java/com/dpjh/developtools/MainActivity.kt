package com.dpjh.developtools

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dpjh.developtools.activitytools.BaseActivity
import com.dpjh.developtools.activitytools.FloatWindowManager
import com.dpjh.developtools.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mAdapter by lazy { ToolsRvAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolsRv.apply {
            layoutManager = GridLayoutManager(this@MainActivity,4)
            adapter = mAdapter
        }

        binding.openVv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.csdn.net/Mr_Liangxiaobai/article/details/135952439"))
            startActivity(intent)
        }

        mAdapter.setData(getItemPages())

//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel("channel_test", "测试通知渠道id", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        val notification = NotificationCompat.Builder(this, "channel_test")
//            .setContentTitle("通知标题")
//            .setContentText("一条通知内容内容一条通知内容内容一条通知内容内容一条通知内容内容")
//            .setWhen(System.currentTimeMillis())
//            .setAutoCancel(false)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)).build()
//        notificationManager.notify(1,notification)
    }

    private fun getItemPages(): List<ToolsItemBean> {
        return arrayListOf<ToolsItemBean>().apply {
            for (item in ToolsClassEnum.values()){
                add(ToolsItemBean(item.title,item.icon,item.functionCode,item.activityClass))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FloatWindowManager.getInstance().release()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        moveTaskToBack(false)
    }
}