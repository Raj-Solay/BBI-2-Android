package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityFilterBinding
import com.foldio.android.adapter.FilterAdapter

class FilterActivity : AppCompatActivity() {
    lateinit var binding:ActivityFilterBinding
   lateinit var filterAdapter:FilterAdapter
    val indusstry = arrayOf("a", "b", "c","d")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
    }
    fun initview(){
        filterAdapter=FilterAdapter(indusstry)
        binding.rcyFilter.adapter=filterAdapter
        binding.tvIndusstry.setOnClickListener {

        }
    }
}