package com.dpjh.miji.jihuang

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpjh.miji.databinding.ActivityJihMijSearchBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.util.ToastUtil
import kotlinx.coroutines.launch

class JIHSearchActivity : BaseActivity<ActivityJihMijSearchBinding>() {

    private val mAdapter by lazy { JIHSearchAdapter(mActivity) }

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
        }
        mViewBinding.edittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty())
                    queryData(s.toString())
            }

        })
    }

    private fun queryData(key: String) {
        lifecycleScope.launch {
            val resultList = arrayListOf<String>()
            if (GlobalData.globalSearchSortList.contains(key)) {
                resultList.addAllSafely(GlobalData.globalSearchMap[key])
            } else {
                for (item in GlobalData.globalSearchList1) {
                    if (item.contains(key)) {
                        resultList.add(item)
                    }
                }
            }
            mAdapter.setData(resultList, key)
        }
    }
}