package com.bbi.bizbulls.menu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbi.bizbulls.databinding.ActivityMyProfileBinding
import com.foldio.android.adapter.PaymentMethodAdapter
import com.foldio.android.adapter.PaymentSettingAdapter
import com.google.android.material.tabs.TabLayout


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
       binding.llView.setOnClickListener { binding.llViewAll.visibility=View.VISIBLE }
       binding.rcyPayment.setLayoutManager(LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false  )
       )
       var paymentMethodAdapter=PaymentMethodAdapter()
       binding.rcyPayment.adapter=paymentMethodAdapter
       var paymentSettingAdapter=PaymentSettingAdapter()
       binding.rcyPaymentAll.adapter=paymentSettingAdapter
    }

}