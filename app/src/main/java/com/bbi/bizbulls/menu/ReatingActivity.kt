package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityReatingBinding

class ReatingActivity : AppCompatActivity() {
    lateinit var binding:ActivityReatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Rate us")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
    }
}