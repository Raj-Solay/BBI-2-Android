package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bbi.bizbulls.databinding.ActivityFilterBinding
import com.foldio.android.adapter.FilterAdapter

class FilterActivity : AppCompatActivity() {
    lateinit var binding:ActivityFilterBinding
   lateinit var clientfilterAdapter:FilterAdapter
   lateinit var indusstryfilterAdapter:FilterAdapter
   lateinit var stagefilterAdapter:FilterAdapter
    val client = arrayOf("Biz Bulls India", "Uncle Kitchens", "Freaky Bakes","Shahi Shagun","Treatmore","Icy Doves")
    val indusstry = arrayOf("Franchise", "Food & Beverages", "Retail","Manufacturing","Automobile","Consumer / FMCG")
     val stage = arrayOf("KYC", "Setup", "Agreement","Licence")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }
    fun initview(){
        indusstryfilterAdapter=FilterAdapter(indusstry)
        binding.rcyFilter.adapter=indusstryfilterAdapter
        binding.tvIndusstry.setOnClickListener {
            indusstryfilterAdapter=FilterAdapter(indusstry)
            binding.rcyFilter.adapter=indusstryfilterAdapter
            binding.llLocation.visibility=View.GONE
            binding.rcyFilter.visibility=View.VISIBLE

            setSelectColor()
            binding.tvIndusstry.setBackgroundResource(R.color.white);
        }
        binding.tvClose.setOnClickListener { onBackPressed() }
        binding.tvClient.setOnClickListener {
            clientfilterAdapter=FilterAdapter(client)
            binding.rcyFilter.adapter=clientfilterAdapter
            binding.llLocation.visibility=View.GONE
            binding.rcyFilter.visibility=View.VISIBLE
            setSelectColor()
            binding.tvClient.setBackgroundResource(R.color.white);

        }
        binding.tvStage.setOnClickListener {
            stagefilterAdapter=FilterAdapter(stage)
            binding.rcyFilter.adapter=stagefilterAdapter
            binding.llLocation.visibility=View.GONE
            binding.rcyFilter.visibility=View.VISIBLE
            setSelectColor()
            binding.tvStage.setBackgroundResource(R.color.white);

        }
        binding.tvLocation.setOnClickListener {
            setSelectColor()
            binding.tvLocation.setBackgroundResource(R.color.white);
            binding.llLocation.visibility=View.VISIBLE
            binding.rcyFilter.visibility=View.GONE
        }
    }
    fun setSelectColor(){
        binding.tvStage.setBackgroundResource(R.color.image_border)
        binding.tvClient.setBackgroundResource(R.color.image_border)
        binding.tvIndusstry.setBackgroundResource(R.color.image_border)
        binding.tvLocation.setBackgroundResource(R.color.image_border)
    }
}