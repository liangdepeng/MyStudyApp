package com.dpjh.miji.jihuang

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpjh.miji.databinding.ActivityJihMijSearchBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.util.SPUtil
import com.dpzz.lib_base.util.ToastUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.launch

class JIHSearchActivity : BaseActivity<ActivityJihMijSearchBinding>() {

    private val mAdapter by lazy { JIHSearchAdapter(mActivity) }

    private val mHistoryRvAdapter by lazy { JIHhistorySearchAdapter(mActivity) }

    override fun initData() {

    }

    override fun initView() {
        mViewBinding.clearBtn.setonMyClickListener {
            mViewBinding.edittext.setText("")
        }
        mViewBinding.searchRv.apply {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = mAdapter
        }
        mViewBinding.searchBtn.setonMyClickListener {
            val key = mViewBinding.edittext.text?.toString()?.trim()
            if (key.isNullOrEmpty()) {
                ToastUtil.show2("查询内容不能为空")
                return@setonMyClickListener
            }
            queryData(key)
            addHistoryKeys(key)
        }
        mViewBinding.edittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {

            }

            override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {

            }

            override fun afterTextChanged(s : Editable?) {
                if (!s.isNullOrEmpty()) queryData(s.toString())
            }
        })
        mViewBinding.historySearchRv.apply {
            layoutManager = FlexboxLayoutManager(this@JIHSearchActivity)
            adapter = mHistoryRvAdapter
            mHistoryRvAdapter.setItemClickListener { adapter, position ->
                val itemData = mHistoryRvAdapter.getItemData(position)
                queryData(itemData)
                sortHistoryKeys(itemData)
            }
            mHistoryRvAdapter.setData(getHistoryKeys())
        }
    }

    private fun addHistoryKeys(key : String?) {
        if (key.isNullOrEmpty())
            return
        if (getHistoryKeys().contains(key)) {
            sortHistoryKeys(key)
        } else {
            val keyStr = SPUtil.getString(Constants.KEY_HISTORY_KEYS, "")
            SPUtil.put(Constants.KEY_HISTORY_KEYS, if (keyStr.isNullOrEmpty()) key else "$key|$keyStr")
        }
    }

    private fun sortHistoryKeys(itemData : String?) {
        if (itemData.isNullOrEmpty()) return
        val keyStr = SPUtil.getString(Constants.KEY_HISTORY_KEYS, "")
        if (keyStr.isNullOrEmpty()) return
        if (keyStr.startsWith(itemData)) return
        val keyList = keyStr.split("|")
        val keyWrapList = ArrayList<String>(keyList)
        if (keyWrapList.contains(itemData)) {
            keyWrapList.remove(itemData)
            keyWrapList.add(0, itemData)
        } else {
            keyWrapList.add(0, itemData)
        }
        keyWrapList.joinToString("|")
        SPUtil.put(Constants.KEY_HISTORY_KEYS, buildString {
            for (item in keyWrapList) {
                append(item)
            }
        })

    }

    private fun getHistoryKeys() : ArrayList<String> {
        val keyStr = SPUtil.getString(Constants.KEY_HISTORY_KEYS, "")
        if (keyStr.isNullOrEmpty()) return arrayListOf()
        return keyStr.split("|").toCollection(arrayListOf<String>())
    }

    private fun queryData(key : String) {
        lifecycleScope.launch {
            val resultList = arrayListOf<String>()
            if (GlobalData.globalSearchSortList.contains(key)) { // 标题匹配 添加标题下面一整组
                resultList.addAllSafely(GlobalData.globalSearchMap[key])
            } else {
                for (item in GlobalData.globalSearchList1) { // 所有 子列表扫描匹配
                    if (item.contains(key)) {
                        resultList.add(item)
                    }
                }
            }

            // 源数据库查询
            if (resultList.isEmpty() && GlobalData.dataBaseInitFinished.get()) {
                GlobalData.dataBaseSearchList.filterNotNull().forEach {
                    if (it.jhDesc.contains(key)) {
                        resultList.add("${it.jhDesc}[数据库]：${it.jhCode}")
                    }
                }
            }
            if (resultList.isNotEmpty()){
                mViewBinding.historySearchRv.visibility = View.GONE
            }
            mAdapter.setData(resultList, key)
        }
    }
}