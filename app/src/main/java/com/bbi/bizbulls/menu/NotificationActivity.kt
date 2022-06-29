package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.ActivityNotificationBinding
import com.foldio.android.adapter.MyOfferAdapter

class NotificationActivity : AppCompatActivity() {
    var binding:ActivityNotificationBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="My Offer"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var myOfferAdapter= MyOfferAdapter()
        binding?.rcyNotification?.adapter=myOfferAdapter

    }
}