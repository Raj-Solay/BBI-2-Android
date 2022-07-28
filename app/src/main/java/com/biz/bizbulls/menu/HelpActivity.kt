package com.biz.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biz.bizbulls.databinding.ActivityHelpctivityBinding
import com.foldio.android.adapter.AddTagsAdapter

import com.google.android.flexbox.FlexboxLayoutManager

class HelpActivity : AppCompatActivity() {
    lateinit var binding: ActivityHelpctivityBinding
    lateinit var itemList:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHelpctivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemList= ArrayList()
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Help")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        val layout = FlexboxLayoutManager(this)
        itemList.add("Back Account")
        itemList.add("Fund Transfer")
        itemList.add("Credit Card")
        itemList.add("Debit Card")
        itemList.add("Fund Transfer")
        itemList.add("Loan")
        binding.rcyTags.layoutManager = layout
      var  addTagsAdapter = AddTagsAdapter(itemList)
        binding.rcyTags.adapter = addTagsAdapter
    }
}