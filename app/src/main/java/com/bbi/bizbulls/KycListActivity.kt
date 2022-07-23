package com.bbi.bizbulls

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bbi.bizbulls.databinding.ActivityKycListBinding
import com.bbi.bizbulls.model.PersonalUserAll
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.MyProcessDialog
import com.foldio.android.adapter.KycApprovalAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KycListActivity : AppCompatActivity() {
    lateinit var binding:ActivityKycListBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@KycListActivity) }

    var approval_type = 0;
    private var userList : ArrayList<PersonalUserAll.Data> = arrayListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKycListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        approval_type = intent.getIntExtra("APPROVAL_TYPE",0)
        Log.d("ATYPE",""+ approval_type)

        initView()
        getUserList()
    }
    private fun initView() {
        var toolBarTitle = ""
        if(approval_type == 0){
            toolBarTitle = "KYC Approval"
        }else if(approval_type == 1){
            toolBarTitle = "Location Approval"
        }else if(approval_type == 2){
            toolBarTitle = "Agreements Approval"
        }else if(approval_type == 3){
            toolBarTitle = "SetUp Approval"
        }else if(approval_type == 4){
            toolBarTitle = "License Approval"
        }

        binding?.customTitleLayout?.tvTitle?.text = toolBarTitle
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
       /* var kycApprovalAdapter= KycApprovalAdapter()
        binding?.rcyKycApproval?.adapter=kycApprovalAdapter*/
       binding.llFilter.setOnClickListener {
           val intent = Intent(this, FilterActivity::class.java)
           startActivity(intent)
       }

    }
    private fun getUserList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<PersonalUserAll> =
            RetrofitClient.getUrl().personalAll(sharedPrefsHelper.authToken)
        call.enqueue(object : Callback<PersonalUserAll> {
            override fun onResponse(
                call: Call<PersonalUserAll>,
                response: Response<PersonalUserAll>
            ) {
                if (response.isSuccessful) {
                    userList = response?.body()?.data!!
                    if(userList.size > 0){
                        binding.txtEmptyRecord.visibility = View.GONE
                        binding.rcyKycApproval.visibility = View.VISIBLE
                    }else{
                        binding.txtEmptyRecord.visibility = View.VISIBLE
                        binding.rcyKycApproval.visibility = View.GONE
                    }
                    setAdapter(userList)
                } else {
                    RetrofitClient.showResponseMessage(this@KycListActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<PersonalUserAll>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycListActivity, t)
            }
        })
    }
    private fun setAdapter(userList: ArrayList<PersonalUserAll.Data>) {
        var kycApprovalAdapter= KycApprovalAdapter(userList,approval_type)
        binding?.rcyKycApproval?.adapter=kycApprovalAdapter
        kycApprovalAdapter.addList(userList)

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               filterAdapter(binding.edtSearch.text.toString(),kycApprovalAdapter,userList)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    fun filterAdapter(
        filter: String,
        kycApprovalAdapter: KycApprovalAdapter,
        userList: ArrayList<PersonalUserAll.Data>
    ) {
        var tmpList : ArrayList<PersonalUserAll.Data> = arrayListOf()
        userList.forEach {
            if(it.whatsappnumber == null){
                it.whatsappnumber = 0
            }
            if(it.fullname!!.toLowerCase().contains(filter.toLowerCase()) || it.whatsappnumber.toString().contains(filter)){
                tmpList.add(it)
            }
        }
        kycApprovalAdapter.addList(tmpList)
        kycApprovalAdapter.notifyDataSetChanged()
    }
}