package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
   fun initView(){
        binding?.customTitleLayout?.tvTitle?.setText("My Profile")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
    }
}