package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityFollowUsBinding
import com.foldio.android.adapter.FollowerAdapter

class AproveStatusActivity : AppCompatActivity() {
    lateinit var binding:ActivityFollowUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFollowUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Follow us")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        var followerAdapter=FollowerAdapter()
        binding.followedRecyclerView.adapter=followerAdapter
    }
}