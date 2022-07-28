package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityNotificationBinding
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
        binding?.customTitleLayout?.tvTitle?.text="Notification"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var myOfferAdapter= MyOfferAdapter()
        binding?.rcyNotification?.adapter=myOfferAdapter

    }
}