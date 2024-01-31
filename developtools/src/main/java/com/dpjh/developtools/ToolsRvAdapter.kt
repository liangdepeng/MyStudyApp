package com.dpjh.developtools

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpjh.developtools.databinding.ItemToolsBinding

class ToolsRvAdapter(private val context: Context) :
    RecyclerView.Adapter<ToolsRvAdapter.ViewHolder>() {

    constructor(ctx: Context, list: List<ToolsItemBean>?) : this(ctx) {

    }

    private val dataList = arrayListOf<ToolsItemBean>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ToolsItemBean>?) {
        dataList.clear()
        if (!list.isNullOrEmpty()) {
            dataList.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemToolsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toolsItemBean = dataList[position]
        holder.itemBinding.descTv.text = toolsItemBean.desc
        try {
            holder.itemBinding.icon.setImageResource(toolsItemBean.icon as Int)
        } catch (e :Exception){
            e.printStackTrace()
            holder.itemBinding.icon.setImageResource(R.mipmap.ic_launcher)
        }
       // Glide.with(context).load(toolsItemBean.icon).into(holder.itemBinding.icon)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(val itemBinding: ItemToolsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position < 0 || position > dataList.size - 1) {
                    return@setOnClickListener
                }
                val toolsItemBean = dataList[position]
                context.startActivity(Intent(context, toolsItemBean.clazz))
            }
        }
    }
}