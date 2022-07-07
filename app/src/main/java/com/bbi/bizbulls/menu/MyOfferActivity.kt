package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityMyOfferBinding
import com.foldio.android.adapter.MyOfferAdapter

class MyOfferActivity : AppCompatActivity() {
    var binding: ActivityMyOfferBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOfferBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="My Offer"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var myOfferAdapter=MyOfferAdapter()
        binding?.rcyMyOffer?.adapter=myOfferAdapter

    }
}