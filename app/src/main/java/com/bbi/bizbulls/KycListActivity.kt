package com.bbi.bizbulls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityKycListBinding
import com.foldio.android.adapter.KycApprovalAdapter

class KycListActivity : AppCompatActivity() {
    lateinit var binding:ActivityKycListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKycListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="KYC Approval"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var kycApprovalAdapter= KycApprovalAdapter()
        binding?.rcyKycApproval?.adapter=kycApprovalAdapter
       binding.llFilter.setOnClickListener {
           val intent = Intent(this, FilterActivity::class.java)
           startActivity(intent)
       }
    }
}