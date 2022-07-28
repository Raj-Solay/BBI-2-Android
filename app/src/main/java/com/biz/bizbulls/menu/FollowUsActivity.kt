package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityFollowUsBinding
import com.foldio.android.adapter.FollowerAdapter

class FollowUsActivity : AppCompatActivity() {
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