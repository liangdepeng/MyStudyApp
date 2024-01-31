package com.dpzz.mvpart

import android.app.Activity
import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import com.dpzz.lib_base.http.DownloadUtil
import com.dpzz.lib_base.http.DownloadUtil.OnDownloadListener
import com.dpzz.lib_base.http.HttpResponse
import com.dpzz.lib_base.http.RequestManager
import com.dpzz.lib_base.http.RequestParams
import com.dpzz.lib_base.tryCatch
import com.dpzz.lib_base.util.PackageUtil
import com.dpzz.lib_base.util.ToastUtil
import com.dpzz.mvpart.databinding.DialogUpdateTipBinding
import org.json.JSONObject
import java.io.File

class AppUpdateManager {

    companion object {

        private val handler = Handler(Looper.getMainLooper())

        fun checkAppUpdate(activity: Activity?) {
            val requestParams =
                RequestParams("https://dpstore007.oss-cn-hangzhou.aliyuncs.com/uejn.json")
            RequestManager.getInstance()
                .startRequest(requestParams, object : HttpResponse<JSONObject> {
                    override fun onSuccess(data: JSONObject?, requestParams: RequestParams?) {
                        tryCatch(action = {
                            val ver = data?.optInt("ver", 0)?:0
                            if (ver>PackageUtil.getVersionCode(activity,activity?.application?.packageName)) {
                                showUpdateDialog(activity, data?.optString("dUrl"))
                            }
                        }, onError = {
                            it.printStackTrace()
                            Log.e("AppUpdateManager", it.message ?: "未知错误")
                        })
                    }

                    override fun onFailed(requestParams: RequestParams?, e: Exception?) {
                        Log.e("AppUpdateManager", e?.message ?: "未知错误")
                    }
                })
        }

        private fun showUpdateDialog(activity: Activity?, dUrl: String?) {
            if (activity == null || activity.isFinishing || activity.isDestroyed)
                return
            val binding: DialogUpdateTipBinding =
                DialogUpdateTipBinding.inflate(activity.layoutInflater)

            val dialog: AlertDialog = AlertDialog.Builder(activity, R.style.standard_dialog_style)
                .setCancelable(false)
                .setView(binding.root)
                .create()

            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.okBtn.setOnClickListener(View.OnClickListener {
                val bytes = Base64.decode(dUrl, Base64.DEFAULT)
                val url = String(bytes)
                dialog.dismiss()
                ToastUtil.show2("开始下载，稍后自动安装")
                val path: String = activity.filesDir.absolutePath
                DownloadUtil.get().download(url, path, object : OnDownloadListener {
                    override fun onDownloadSuccess(file: File) {
                        // installApk(file.getPath());
                        handler.postDelayed({
                            PackageUtil.installApk(activity, file.path)
                        }, 3000)
                    }
                    override fun onDownloading(progress: Int) {}
                    override fun onDownloadFailed() {}
                })
            })

            binding.cancelBtn.setOnClickListener(View.OnClickListener {
                ToastUtil.show2("开始下载，稍后自动安装")
                // dialog.dismiss();
            })

            dialog.show()
        }

    }

}