package com.dpjh.miji.jihuang

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpjh.miji.databinding.ItemJihMijiBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.util.ClipBroadUtil
import com.dpzz.lib_base.util.ToastUtil


class JIHAdapter(val mActivity: Activity) : RecyclerView.Adapter<JIHAdapter.ViewHolder>() {

    private val dataList = arrayListOf<String>()

    inner class ViewHolder(val itemBinding: ItemJihMijiBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<String>?) {
        dataList.clear()
        dataList.addAllSafely(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemJihMijiBinding.inflate(LayoutInflater.from(mActivity), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listStr = dataList[position].split("：")
        holder.itemBinding.codeTv.visibility = View.VISIBLE
        holder.itemBinding.descTv.setTypeface(null,Typeface.NORMAL)
        if (!listStr.isNullOrEmpty()) {
            if (listStr.size == 2) {
                holder.itemBinding.descTv.text = listStr[0]
                holder.itemBinding.codeTv.text = listStr[1]
            } else {
                if (listStr[0].startsWith("---")){
                    val str = listStr[0].replace("---", "")
                    holder.itemBinding.descTv.text = str
                    holder.itemBinding.descTv.setTypeface(null,Typeface.BOLD)
                } else {
                    holder.itemBinding.descTv.text = listStr[0]
                }
                holder.itemBinding.codeTv.visibility = View.GONE
            }
        } else {
            holder.itemBinding.descTv.text = dataList[position]
            holder.itemBinding.codeTv.visibility = View.GONE
        }
        holder.itemBinding.space.visibility = View.GONE
        if (position == itemCount - 1) {
            holder.itemBinding.space.visibility = View.VISIBLE
        }
        holder.itemBinding.codeTv.setonMyClickListener {
            ClipBroadUtil.copyTxt(holder.itemBinding.codeTv.text.toString().trim())
            ToastUtil.show2("${holder.itemBinding.descTv.text.toString().trim()}  code 复制到剪切板成功")
        }

        Log.e("123dd","${holder.itemBinding.codeTv.currentTextColor}" )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}