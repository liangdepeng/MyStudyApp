package com.dpjh.miji.jihuang

import android.app.ProgressDialog
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dpjh.miji.R
import com.dpjh.miji.databinding.ActivityJihMainBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.util.SPUtil
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class JiHMainActivity : BaseActivity<ActivityJihMainBinding>() {

    companion object {
        const val FILE_SAVE_TAG = "FILE_SAVE_TAG"
    }

    private var styleFragment: Fragment? = null


    private val progressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("初始化...")
        }
    }

    override fun initData() {
//       lifecycleScope.launch {
//           ProgressDialog(this@JiHMainActivity).show()
//           Log.e("tagg"," 222 "+Thread.currentThread().toString())
//           delay(5000)
//           Log.e("tagg"," 333 "+Thread.currentThread().toString())
//           ToastUtil.show2("tips")
//
//           withContext(Dispatchers.IO){
//               Log.e("tagg"," 444  "+Thread.currentThread().toString())
//           }
//       }
//        Thread({
//            Thread.sleep(3000)
//            Log.e("tagg"," 000  "+Thread.currentThread().toString())
//        }).start()

        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                progressDialog.show()
            }
            val inputStream = assets.open("mij.txt")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = bufferedReader.readLine()
            val fileInfoList = arrayListOf<String>()
            Log.e("file", "开始读取 ${Thread.currentThread()}")
            while (line != null) {
                if (!TextUtils.isEmpty(line)) {
                    fileInfoList.add(line)
                    Log.e("file", line)
                }
                line = bufferedReader.readLine()
            }
            Log.e("file", "结束读取 ${fileInfoList.size}行")

            val mijStr = Gson().toJson(fileInfoList)
            SPUtil.put(FILE_SAVE_TAG, mijStr)

            val map = linkedMapOf<String, ArrayList<String>>()
            val tempList = arrayListOf<String>()
            var tempTitle = fileInfoList[0]
            var allList = arrayListOf<String>()

            fileInfoList.forEach {
                if (it[2] != '-' && it.startsWith("--")) {
                    if (tempList.isNotEmpty()) {
                        val list = arrayListOf<String>()
                        list.addAllSafely(tempList)
                        map[tempTitle] = list
                        tempList.clear()
                        tempTitle = it
                    }
                } else {
                    tempList.add(it)
                }
            }
            //   delay(5000)

            withContext(Dispatchers.Main) {
                Log.e("file", "结束读取 -- ${map.size} ${Thread.currentThread()}")
                bindViewPager(map)
                delay(1000)
                progressDialog.dismiss()
            }
        }
    }

    private fun bindViewPager(map: LinkedHashMap<String, java.util.ArrayList<String>>) {
        val fragments = arrayListOf<Fragment>()
        val titles = arrayListOf<String>()
        map.forEach {
            val title = it.key.replace("--", "").replace("：", "")
            titles.add(title)
            fragments.add(JiHgFragment.newInstance(it.value))

            if (!title.contains("使用方法")){
                GlobalData.globalSearchSortList.add(title)
                GlobalData.globalSearchList1.addAllSafely(it.value)
                GlobalData.globalSearchMap[title] = ArrayList(it.value)
            }
        }
        mViewBinding.viewPager.offscreenPageLimit = fragments.size
        mViewBinding.viewPager.adapter =
            ViewPagerMyAdapter(supportFragmentManager, fragments, titles)
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)

        if (styleFragment == null)
            styleFragment = JIHMainFragment.newInstance(map)
    }

    private fun switchDisplayStyle(fragment: Fragment?) {
        if (fragment == null)
            return
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            if (fragment.isVisible)
                beginTransaction.hide(fragment)
            else
                beginTransaction.show(fragment)
        } else {
            //beginTransaction.setCustomAnimations()
            beginTransaction.replace(R.id.fragment_fl, fragment)
        }
        beginTransaction.commit()
    }

    override fun initView() {
        setSupportActionBar(mViewBinding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_item -> {
                startActivity(Intent(this,JIHSearchActivity::class.java))
            }
            R.id.switch_item -> {
                switchDisplayStyle(styleFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
        //  super.onBackPressed()
    }
}