package com.dpjh.miji.jihuang

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpjh.miji.R
import com.dpjh.miji.databinding.ItemJihMijiBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.tryCatch
import com.dpzz.lib_base.util.ClipBroadUtil
import com.dpzz.lib_base.util.ToastUtil


class JIHSearchAdapter(val mActivity: Activity) :
    RecyclerView.Adapter<JIHSearchAdapter.ViewHolder>() {

    private val dataList = arrayListOf<String>()
    private var searchKey: String? = null

    inner class ViewHolder(val itemBinding: ItemJihMijiBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<String>?, key: String) {
        this.searchKey = key
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
        holder.itemBinding.descTv.setTypeface(null, Typeface.NORMAL)
        if (!listStr.isNullOrEmpty()) {
            if (listStr.size == 2) {
                holder.itemBinding.descTv.text =
                    biaoJiSign(content = listStr[0], searchKey)// listStr[0]
                holder.itemBinding.codeTv.text =
                    biaoJiSign(content = listStr[1], searchKey)//listStr[1]
//                biaoJiSign(content = listStr[0],searchKey)
//                biaoJiSign(content = listStr[1],searchKey)
            } else {
                if (listStr[0].startsWith("---")) {
                    val str = listStr[0].replace("---", "")
                    holder.itemBinding.descTv.text = biaoJiSign(content = str, searchKey) //str
                    holder.itemBinding.descTv.setTypeface(null, Typeface.BOLD)
                } else {
                    holder.itemBinding.descTv.text = biaoJiSign(content = listStr[0], searchKey) // listStr[0]
                }
                holder.itemBinding.codeTv.visibility = View.GONE
            }
        } else {
            holder.itemBinding.descTv.text = biaoJiSign(content = dataList[position], searchKey) // dataList[position]
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

        // Log.e("123dd","${holder.itemBinding.codeTv.currentTextColor}" )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun biaoJiSign(content: String?, searchKey: String?): SpannableString? {
        if (content.isNullOrEmpty() || searchKey.isNullOrEmpty())
            return SpannableString(content ?: "")
        val startIndex = content.indexOf(searchKey)
        val spannableString = SpannableString(content)
      //  Log.e("123321", "$searchKey   $startIndex   $content")
        if (startIndex >= 0) {
            tryCatch({
                spannableString.setSpan(
                    object : ForegroundColorSpan(mActivity.resources.getColor(R.color.purple_500)) {
                        override fun updateDrawState(textPaint: TextPaint) {
                            super.updateDrawState(textPaint)
                            textPaint.isUnderlineText = true
                        }
                    },
                    startIndex, startIndex + searchKey.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }, onError = {
                Log.e("456654","$it")
            })
        }
        return spannableString
    }
}