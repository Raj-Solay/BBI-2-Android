package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    var binding:ActivityPrivacyPolicyBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Privacy Policy")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }

    }
}