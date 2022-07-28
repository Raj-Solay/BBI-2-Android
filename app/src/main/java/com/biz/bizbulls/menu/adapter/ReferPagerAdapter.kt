package com.biz.bizbulls.menu.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.biz.bizbulls.menu.ReferActivity
import com.foldio.fra.ReferFragment

class ReferPagerAdapter(var fm: FragmentManager,var tabCount: Int,referActivity: ReferActivity) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ReferFragment()
            }
            else -> return ReferFragment()
        }
    }
    override fun getCount(): Int {
        return tabCount
    }
}