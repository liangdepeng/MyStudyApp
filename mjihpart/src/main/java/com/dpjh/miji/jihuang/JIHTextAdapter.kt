package com.dpjh.miji.jihuang

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpjh.miji.databinding.ItemJihmjTextBinding
import com.dpzz.lib_base.addAllSafely
import com.dpzz.lib_base.setonMyClickListener

class JIHTextAdapter(val mContext: Context) : RecyclerView.Adapter<JIHTextAdapter.ViewHolder>() {

    private val list = arrayListOf<String>()
    private var clickIndex: Int = 0
    private var listener :OnItemClickListener?=null

    inner class ViewHolder(val binding: ItemJihmjTextBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<String>?) {
        list.clear()
        list.addAllSafely(data)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener :OnItemClickListener?){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemJihmjTextBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = list[position]
        holder.binding.textTv.text = title
        holder.binding.textTv.setTextColor(-1979711488)
        holder.binding.textTv.setTextColor(Color.BLACK)
        holder.binding.textTv.setTypeface(null, Typeface.NORMAL)
        if (position == clickIndex) {
            holder.binding.textTv.setTextColor(Color.parseColor("#FF6200EE"))
            holder.binding.textTv.setTypeface(null, Typeface.BOLD)
        }
        holder.itemView.setonMyClickListener {
            clickIndex = holder.adapterPosition
            listener?.onClick(it,list[clickIndex])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, item: String?)
    }
}