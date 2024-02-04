package com.dpjh.developtools.activitytools

import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpjh.developtools.Constants
import com.dpjh.developtools.R
import com.dpjh.developtools.databinding.ActivityToolsPageBinding
import java.util.*


class ActivityToolsPage : BaseActivity() {

    private val TAG = "ActivityToolsPage6666"

    private val binding by lazy { ActivityToolsPageBinding.inflate(layoutInflater) }

    private val mAdapter by lazy { ActivityInfoAdapter(this) }

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

        if (!isUsageAccessGranted(this)) {
            showInitDialog()
        } else {
            startRecord()
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter(Constants.ACTION_CURRENT_ACTIVITY))
    }

    private var lastPage: String? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val packageName = intent?.getStringExtra("currentPackageName")
            val activity = intent?.getStringExtra("currentActivity")
            val time = intent?.getStringExtra("currentTime")
            if (activity.isNullOrEmpty())
                return
            if (!TextUtils.equals(lastPage, activity)) {
                val appInfoBean = AppInfoBean()
                appInfoBean.appName = PackageUtils.getAppName(this@ActivityToolsPage, packageName)
                appInfoBean.iconUrl = PackageUtils.getAppIcon(this@ActivityToolsPage, packageName)
                appInfoBean.currentActivity = activity
                mAdapter.addData(appInfoBean)
                lastPage = activity

            }
        }
    }

    private fun initView() {
        binding.switchFloatingWindow.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    FloatWindowManager.getInstance().showWindow()
                } else {
                    FloatWindowManager.getInstance().hideWindow()
                }
            }
        })

        binding.activitysRv.apply {
            layoutManager = LinearLayoutManager(this@ActivityToolsPage)
            adapter = mAdapter
        }

        binding.switchPlaceholder.setOnClickListener {
            if (FloatWindowManager.getInstance().isHasPermission) {
                binding.switchPlaceholder.visibility = View.GONE
                binding.switchFloatingWindow.isChecked = true
            } else {
                showFloatWindowPermissionDialog()
            }
        }
        if (FloatWindowManager.getInstance().isHasPermission) {
            binding.switchPlaceholder.visibility = View.GONE
            if (FloatWindowManager.getInstance().isShow) {
                binding.switchFloatingWindow.isChecked = true
            }
        } else {
            binding.switchPlaceholder.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        if (FloatWindowManager.getInstance().isHasPermission) {
            binding.switchPlaceholder.visibility = View.GONE
        } else {
            binding.switchPlaceholder.visibility = View.VISIBLE
        }
    }

    private fun showFloatWindowPermissionDialog() {
        val msg = "《${getString(R.string.app_name)}》-使用悬浮窗需要手动授予权限，请务必授予权限，否则无法正常使用"
        val spannableString = SpannableString(msg)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this,R.color.purple_500)),0,8,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("提示")
            .setMessage(spannableString)
            .setNegativeButton("点击跳转", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    FloatWindowManager.getInstance().requestPermission(this@ActivityToolsPage)
                    dialog?.dismiss()
                }
            }).create().show()
    }

    private fun startRecord() {
        startService(Intent(this, WatchService::class.java))
//        Thread {
//            while (true) {
//                monitorAppUsage(this)
//                SystemClock.sleep(1000)
//            }
//        }.start()
    }

    private fun showInitDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("提示")
            .setMessage("首次使用需要初始化，请务必授予必要的权限，否则无法正常使用")
            .setNegativeButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    showRequestAppUsageDialog()
                    dialog?.dismiss()
                }
            }).create().show()
    }

    private fun showRequestAppUsageDialog() {
        val msg = "《${getString(R.string.app_name)}》-查看当前Activity-需要授权\"查看应用使用记录访问权限\",点击确定去手动授权"
        val spannableString = SpannableString(msg)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(this,R.color.purple_500)),0,8,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("提示")
            .setMessage(spannableString)
            .setNegativeButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    requestUsageAccess(this@ActivityToolsPage)
                    dialog?.dismiss()
                }
            }).create().show()
    }

    // 检查权限
    private fun isUsageAccessGranted(context: Context): Boolean {
        val appOps = context.getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageAccess(context: Context) {
        if (!isUsageAccessGranted(context)) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivityForResult(intent, 233)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 233) {
            if (isUsageAccessGranted(this)) {
                Toast.makeText(this, "已成功 获得 应用使用记录访问权限", Toast.LENGTH_LONG).show()
                startRecord()
            } else {
                Toast.makeText(this, "未检测到 应用使用记录访问权限，访问失败\n请退出重试", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == FloatWindowManager.OVERLAY_PERMISSION_REQUEST_CODE) {
            if (FloatWindowManager.getInstance().isHasPermission) {
                Toast.makeText(this, "已成功 获得 悬浮窗权限", Toast.LENGTH_LONG).show()
                handler.postDelayed({
                    binding.switchFloatingWindow.isChecked = true
                    if (!isUsageAccessGranted(this)){
                        Toast.makeText(this, "未检测到 应用使用记录访问权限，访问失败\n请退出重试", Toast.LENGTH_LONG).show()
                    }
                }, 1000)
            } else {
                Toast.makeText(this, "悬浮窗权限 未授权", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        super.onDestroy()
    }
}