//package com.dpjh.miji
//
//import android.os.Bundle
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
//import com.google.android.material.tabs.TabLayout
//import androidx.viewpager.widget.ViewPager
//import androidx.appcompat.app.AppCompatActivity
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import com.dpjh.miji.ui.main.SectionsPagerAdapter
//import com.dpjh.miji.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = binding.viewPager
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = binding.tabs
//        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = binding.fab
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("知道了") { }.show()
//        }
//    }
//}