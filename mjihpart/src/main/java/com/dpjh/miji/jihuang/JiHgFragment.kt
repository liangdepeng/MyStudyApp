package com.dpjh.miji.jihuang

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpjh.miji.databinding.FragmentJhListBinding
import com.dpzz.lib_base.base.BaseFragment

class JiHgFragment : BaseFragment<FragmentJhListBinding>() {

    companion object{
        fun newInstance(dataList:ArrayList<String>):JiHgFragment{
            val fragment = JiHgFragment()
            val bundle = Bundle()
            bundle.putStringArrayList("datas",dataList)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mAdapter by lazy { JIHAdapter(mActivity) }

    override fun initContentView() {
        val dataList = arguments?.getStringArrayList("datas")
        mViewBinding.recyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
            mAdapter.setData(dataList)
        }
    }

}