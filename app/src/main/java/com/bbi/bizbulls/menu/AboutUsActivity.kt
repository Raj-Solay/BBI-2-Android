package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityAboutUsBinding
import com.bbi.bizbulls.databinding.ActivityProjectinfoBinding

class AboutUsActivity : AppCompatActivity() {
    var binding:ActivityAboutUsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("About Us")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
    }
}