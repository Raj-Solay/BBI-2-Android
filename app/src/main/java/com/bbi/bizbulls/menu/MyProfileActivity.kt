package com.bbi.bizbulls.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbi.bizbulls.databinding.ActivityMyProfileBinding
import com.foldio.android.adapter.PaymentMethodAdapter


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
       binding.rcyPayment.setLayoutManager(LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true  )
       )
       var paymentMethodAdapter=PaymentMethodAdapter()
       binding.rcyPayment.adapter=paymentMethodAdapter
    }
}