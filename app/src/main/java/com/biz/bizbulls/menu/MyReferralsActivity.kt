package com.biz.bizbulls.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.biz.bizbulls.databinding.ActivityMyReferralsBinding
import com.foldio.android.adapter.ServicesAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class MyReferralsActivity : AppCompatActivity() {

private lateinit var binding: ActivityMyReferralsBinding
    lateinit var itemList:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = ActivityMyReferralsBinding.inflate(layoutInflater)
     setContentView(binding.root)
        itemList= ArrayList()
initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("My Referrals")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        val layout = FlexboxLayoutManager(this)
        itemList.add("Food Delivery")
        itemList.add("Genie Service")
        itemList.add("Book a Ride")
        itemList.add("Maintenance Boy")
        itemList.add("Property Pasand")
        itemList.add("Docter Appointment")
        binding.rcyTags.layoutManager = layout
        var  addTagsAdapter = ServicesAdapter(itemList)
        binding.rcyTags.adapter = addTagsAdapter
    }
}