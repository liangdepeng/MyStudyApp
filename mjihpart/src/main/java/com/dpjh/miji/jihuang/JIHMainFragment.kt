package com.dpjh.miji.jihuang

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpjh.miji.R
import com.dpjh.miji.databinding.FragmentJihMainBinding
import com.dpzz.lib_base.base.BaseFragment
import com.dpzz.lib_base.tryCatch
import java.util.*

class JIHMainFragment : BaseFragment<FragmentJihMainBinding>() {

    private val titles = arrayListOf<String>()
    private val fragmentMap = hashMapOf<String, Fragment>()
    private var lastShowFragment: Fragment? = null
    private val leftAdapter by lazy { JIHTextAdapter(mContext) }

    companion object {
        fun newInstance(dataMap: LinkedHashMap<String, ArrayList<String>>): JIHMainFragment {
            val fragment = JIHMainFragment()
            val bundle = Bundle()
            bundle.putSerializable("dataMap", dataMap)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initContentView() {
        val linkedHashMap =
            arguments?.getSerializable("dataMap") as? LinkedHashMap<String, ArrayList<String>>
        linkedHashMap?.forEach {
            val title = it.key.replace("--", "").replace("：","")
            val fragment = JiHgFragment.newInstance(it.value)
            if (lastShowFragment == null) {
                lastShowFragment = fragment
            }
            titles.add(title)
            fragmentMap[title] = fragment
        }
        mViewBinding.leftRv.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = leftAdapter
            leftAdapter.setData(titles)
            leftAdapter.setItemClickListener(object : JIHTextAdapter.OnItemClickListener {
                override fun onClick(v: View, item: String?) {
                    showFragment(fragmentMap[item])
                }
            })
        }
        showFragment(lastShowFragment)
        //  mViewBinding.leftRv.getChildAt(0).performClick()
    }

    fun showFragment(fragment: Fragment?) {
        if (fragment == null || ((fragment == lastShowFragment) && fragment.isVisible)) return
        tryCatch({
            val beginTransaction = childFragmentManager.beginTransaction()
            if (lastShowFragment != null) {
                beginTransaction.hide(lastShowFragment!!)
            }
            if (!fragment.isAdded) {
                beginTransaction.add(R.id.right_fl, fragment)
            }
            beginTransaction.show(fragment)
            beginTransaction.commit()
            lastShowFragment = fragment
        })
    }

}