package com.dpzz.lib_base.update

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import com.dpzz.lib_base.R
import com.dpzz.lib_base.databinding.DialogUpdateTipssBinding
import com.dpzz.lib_base.http.DownloadUtil
import com.dpzz.lib_base.http.DownloadUtil.OnDownloadListener
import com.dpzz.lib_base.http.HttpResponse
import com.dpzz.lib_base.http.RequestManager
import com.dpzz.lib_base.http.RequestParams
import com.dpzz.lib_base.tryCatch
import com.dpzz.lib_base.util.PackageUtil
import com.dpzz.lib_base.util.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            val binding: DialogUpdateTipssBinding =
                DialogUpdateTipssBinding.inflate(activity.layoutInflater)

            val dialog: AlertDialog = AlertDialog.Builder(activity, R.style.update_dialog_style)
                .setCancelable(false)
                .setView(binding.root)
                .create()

            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            val progressDialog = ProgressDialog(activity)

            binding.okBtn.setOnClickListener(View.OnClickListener {
                val bytes = Base64.decode(dUrl, Base64.DEFAULT)
                val url = String(bytes)
                val path: String = activity.filesDir.absolutePath
                ToastUtil.show2("开始下载，稍后自动安装")
                MainScope().launch(Dispatchers.Main){
                    dialog.dismiss()
                    delay(2000)
                    DownloadUtil.get().download(url, path, object : OnDownloadListener {
                        override fun onDownloadSuccess(file: File) {
                            // installApk(file.getPath());
                            handler.postDelayed({
                                PackageUtil.installApk(activity, file.path)
                                progressDialog.dismiss()
                            }, 3000)
                        }
                        override fun onDownloading(progress: Int) {
                            progressDialog.progress = if (progress>95) 95 else progress
                        }
                        override fun onDownloadFailed() {}
                    })

                }

            })

            binding.cancelBtn.setOnClickListener(View.OnClickListener {
                ToastUtil.show2("开始下载，稍后自动安装")
                // dialog.dismiss();
            })

            dialog.show()
        }

    }

}