package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityReferBinding
import com.biz.bizbulls.menu.adapter.ReferPagerAdapter
import com.google.android.material.tabs.TabLayout

class ReferActivity : AppCompatActivity() {
    lateinit var binding:ActivityReferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityReferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        viewPager()
    }

    private fun initView() {
            binding?.customTitleLayout?.tvTitle?.setText("My Refer")
            binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
    }
    fun viewPager(){

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Invite"))
            val adapter = ReferPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount,this@ReferActivity)
            binding.viewpager.adapter = adapter
            binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewpager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
        }
}