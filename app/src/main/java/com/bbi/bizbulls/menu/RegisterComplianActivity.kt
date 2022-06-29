package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityRegisterComplianBinding

class RegisterComplianActivity : AppCompatActivity() {
    var binding:ActivityRegisterComplianBinding?=null
    val CompType =arrayOf("Select Your Role","Type1","Type2","Type3")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterComplianBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Register Complain")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        val monthsadapter = ArrayAdapter(this, R.layout.spinner_item, CompType)
        binding?.spComptype?.adapter = monthsadapter
    }
}