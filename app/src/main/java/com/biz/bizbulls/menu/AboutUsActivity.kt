package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityAboutUsBinding

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