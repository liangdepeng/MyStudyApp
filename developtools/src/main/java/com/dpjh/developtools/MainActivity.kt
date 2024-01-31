package com.dpjh.developtools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dpjh.developtools.activitytools.FloatWindowManager
import com.dpjh.developtools.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mAdapter by lazy { ToolsRvAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolsRv.apply {
            layoutManager = GridLayoutManager(this@MainActivity,4)
            adapter = mAdapter
        }

        binding.openVv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.csdn.net/Mr_Liangxiaobai/article/details/135952439"))
            startActivity(intent)
        }

        mAdapter.setData(getItemPages())
    }

    private fun getItemPages(): List<ToolsItemBean> {
        return arrayListOf<ToolsItemBean>().apply {
            for (item in ToolsClassEnum.values()){
                add(ToolsItemBean(item.title,item.icon,item.activityClass))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FloatWindowManager.getInstance().release()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        moveTaskToBack(false)
    }
}