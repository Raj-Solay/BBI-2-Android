package com.bbi.bizbulls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bbi.bizbulls.databinding.ActivityKycDocViewBinding
import com.bbi.bizbulls.model.ApprovalDocRes
import com.bbi.bizbulls.model.PersonalUserAll
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.DialogDocApprove
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.foldio.android.adapter.DocApprovalAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KycDocViewActivity : AppCompatActivity(),DocViewListener {
    lateinit var binding:ActivityKycDocViewBinding
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@KycDocViewActivity) }

    private lateinit var userData  : PersonalUserAll.Data
    private var docList : List<ApprovalDocRes.Data> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityKycDocViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.getSerializableExtra("model") as PersonalUserAll.Data

        Log.d("UserData","id : "+ userData.userId)

        initView()
        getUserList()

        binding.btnCancelFinal.setOnClickListener {
            onBackPressed()
        }
        binding.btnApproveFinal.setOnClickListener {
            callApi()
        }
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.text="Approval Doc View"
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }

        binding.txtName.setText(userData.fullname)
        binding.txtAddress.setText(userData.permanentAdd.toString())
        binding.txtDate.setText(userData.emailid.toString())
        binding.txtFullname.setText(userData.emailid.toString())

        binding.txtCName.setText(userData.fullname.toString())
        binding.txtCGender.setText(userData.gender.toString())

        binding.txtCAddress.setText(userData.presentAdd.toString())
        binding.txtCAge.setText(userData.age.toString()+" Years")

    }
    private fun getUserList(){
        MyProcessDialog.showProgressBar(this, 0)
        val call: Call<ApprovalDocRes> =
            RetrofitClient.getUrl().viewUserDoc(sharedPrefsHelper.authToken,userData.userId.toString())
        call.enqueue(object : Callback<ApprovalDocRes> {
            override fun onResponse(
                call: Call<ApprovalDocRes>,
                response: Response<ApprovalDocRes>
            ) {
                if (response.isSuccessful) {
                    docList= response.body()?.data!!
                    setAdapter(docList)
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ApprovalDocRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }
    var docApprovalAdapter : DocApprovalAdapter? = null
    private fun setAdapter(userList: List<ApprovalDocRes.Data>?) {
        docApprovalAdapter= DocApprovalAdapter(userList,this)
       binding?.listDocuments?.adapter = docApprovalAdapter

        var count = 0
        docList.forEach {
            if(it.isApproved || it!!.documentStatus == "1" )
                count++
        }
       /* if(count >0){
            binding.linerApprove.visibility = View.VISIBLE
            binding.txtBg.visibility = View.VISIBLE
        }else{
            binding.linerApprove.visibility = View.INVISIBLE
            binding.txtBg.visibility = View.INVISIBLE
        }*/
    }

    private fun showDocDialog(data: ApprovalDocRes.Data?) {

        val docDialogDocApprove = DialogDocApprove(this)
        docDialogDocApprove.show()

        Picasso.get().load(
            data!!.documentName)
            .placeholder(R.drawable.img_default)
            .into(docDialogDocApprove.imdDocView)
        docDialogDocApprove.btnCancel!!.setOnClickListener {
            docDialogDocApprove.dismiss()
        }
        if(data.isApproved || data.documentStatus == "1"){
            docDialogDocApprove.btnApproval!!.setText("DisApprove")
        }else{
            docDialogDocApprove.btnApproval!!.setText("Approve")
        }
        docDialogDocApprove.btnApproval!!.setOnClickListener {
            docDialogDocApprove.dismiss()
            docList.forEach {
                if(it.documentId == data.documentId){
                    it.isApproved = !it.isApproved
                }
            }
            docApprovalAdapter?.notifyDataSetChanged()

            var count = 0;
            docList.forEach {
                if(it.isApproved)
                    count++
            }
           /* if(count >0){
                binding.linerApprove.visibility = View.VISIBLE
                binding.txtBg.visibility = View.VISIBLE
            }else{
                binding.linerApprove.visibility = View.INVISIBLE
                binding.txtBg.visibility = View.INVISIBLE
            }*/
        }
    }
    private fun callApi(){
        MyProcessDialog.showProgressBar(this, 0)

        var jsonObject = JsonObject();
        jsonObject.addProperty("user_id",userData.userId)


        var jsonArray = JsonArray()

        var count = 0
        docList.forEach {
            var jsonObjectDoc = JsonObject()
            jsonObjectDoc.addProperty("doc_id",it!!.id.toString())
            if(it!!.documentType.toString() == "null"){
                it!!.documentType = "1"
            }
            if(it!!.isApproved || it!!.documentStatus == "1" ) {
                count++
                jsonObjectDoc.addProperty("document_type","1")
            }else{
                jsonObjectDoc.addProperty("document_type","0")
            }
            jsonArray.add(jsonObjectDoc)

        }
        if(count > 0){
            jsonObject.addProperty("status","approved")
        }else{
            jsonObject.addProperty("status","pending")
        }

        jsonObject.add("data",jsonArray)

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().approveDoc(sharedPrefsHelper.authToken,jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                   finish()
                } else {
                    RetrofitClient.showResponseMessage(this@KycDocViewActivity, response.code())
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@KycDocViewActivity, t)
            }
        })
    }

    override fun onDocView(data: ApprovalDocRes.Data?) {
        showDocDialog(data)
    }
}