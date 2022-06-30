package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityTermsConditionBinding

class TermsConditionActivity : AppCompatActivity() {
    var binding:ActivityTermsConditionBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTermsConditionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Terms And Conditions")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }

    }
}
