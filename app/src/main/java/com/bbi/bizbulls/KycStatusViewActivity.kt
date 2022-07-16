package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bbi.bizbulls.databinding.ActivityKycStatusViewBinding
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.PersonalUserAll
import com.bbi.bizbulls.model.StatusDataRes
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.DialogDocApprove
import com.bbi.bizbulls.utils.Globals
import com.foldio.android.adapter.StatusDocAdapter
import com.squareup.picasso.Picasso

class KycStatusViewActivity : AppCompatActivity() {
    lateinit var binding:ActivityKycStatusViewBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@KycStatusViewActivity) }

    private lateinit var userData  : PersonalUserAll.Data
    private var docList : List<ApprovalDocRes.Data> = arrayListOf()
    lateinit var statusData: StatusDataRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKycStatusViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusData =intent!!.getSerializableExtra(Globals.REGISTRATION_FEES_DATA)!! as StatusDataRes


        initView()

        setAdapter()

    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="Kyc Status"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }

    }

    var docApprovalAdapter : StatusDocAdapter? = null
    private fun setAdapter() {
        var docList = statusData.kyc!!.doc
        docApprovalAdapter= StatusDocAdapter(docList,this)
       binding?.listDocuments?.adapter = docApprovalAdapter

    }

     fun showDocDialog(data: String?) {

        val docDialogDocApprove = DialogDocApprove(this)
        docDialogDocApprove.show()

        Picasso.get().load(
            data!!)
            .placeholder(R.drawable.img_default)
            .into(docDialogDocApprove.imdDocView)
        docDialogDocApprove.btnCancel!!.setOnClickListener {
            docDialogDocApprove.dismiss()
        }
         docDialogDocApprove.btnCancel!!.setText("Close")
        docDialogDocApprove.txtSpace!!.visibility = View.GONE
        docDialogDocApprove.btnApproval!!.visibility = View.GONE
        docDialogDocApprove.btnApproval!!.setOnClickListener {
            docDialogDocApprove.dismiss()

        }
    }


}