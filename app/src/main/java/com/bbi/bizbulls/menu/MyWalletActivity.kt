package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityMyWalletBinding
import com.bbi.bizbulls.menu.adapter.ImageSlideAdapter
import com.bbi.bizbulls.menu.data.ImagesModel
import me.relex.circleindicator.CircleIndicator

class MyWalletActivity : AppCompatActivity() {
    var binding:ActivityMyWalletBinding?=null
    private lateinit var imagesModel:ArrayList<String>
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyWalletBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        imagesModel= ArrayList()
        initView()
    }

    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("My Wallet")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
            imagesModel.add("http://52.203.13.92/foldiaApp/images/no_image_available.jpg")
            imagesModel.add("http://52.203.13.92/foldiaApp/images/no_image_available.jpg")
            imagesModel.add("http://52.203.13.92/foldiaApp/images/no_image_available.jpg")
            imagesModel.add("http://52.203.13.92/foldiaApp/images/no_image_available.jpg")
            viewPagerAdapter = ImageSlideAdapter(this, imagesModel)
            binding?.viewpager?.adapter = viewPagerAdapter
            binding?.indicator?.setViewPager(binding?.viewpager)
            }
}