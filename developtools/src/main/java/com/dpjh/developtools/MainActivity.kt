package com.dpjh.developtools

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
       // stopService(Intent(this,WatchService::class.java))
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        moveTaskToBack(false)
    }
}