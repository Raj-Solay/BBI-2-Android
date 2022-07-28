package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityMyOfferBinding
import com.foldio.android.adapter.MyOfferAdapter

class MyOfferActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOfferBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="My Offer"
        binding?.nopost?.wallEmptyTv?.text="No Offer Available"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var myOfferAdapter=MyOfferAdapter()
        binding?.rcyMyOffer?.adapter=myOfferAdapter

    }
}