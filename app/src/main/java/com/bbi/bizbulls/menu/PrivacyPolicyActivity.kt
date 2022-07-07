package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityPrivacyPolicyBinding
import com.bbi.bizbulls.databinding.ActivityProjectinfoBinding

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