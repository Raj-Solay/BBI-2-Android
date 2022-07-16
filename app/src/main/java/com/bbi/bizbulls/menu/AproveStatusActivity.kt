package com.bbi.bizbulls.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityFollowUsBinding
import com.bbi.bizbulls.menu.data.GetAllUserDataModel
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.foldio.android.adapter.AproveStatusAdapter
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AproveStatusActivity : AppCompatActivity(), AproveStatusAdapter.userassign {
    lateinit var binding:ActivityFollowUsBinding
    lateinit var aproveList:ArrayList<GetAllUserDataModel>
    lateinit var aproveStatusAdapter:AproveStatusAdapter
    lateinit var userassign: AproveStatusAdapter.userassign
    val aproveArray = arrayOf<String>("Administrator","User","FO Team","Vendor","HR1 Administrator","User","FO Team","Vendor","HR")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFollowUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        aproveList= ArrayList()
        userassign=this
        initView()
        getUserList()
    }
    private fun initView() {
        binding.customTitleLayout.tvTitle?.setText("Follow us")
        binding.customTitleLayout.ivBack?.setOnClickListener { onBackPressed() }

    }

    fun getUserList() {
        val sharedPrefsHelper by lazy { SharedPrefsManager(this) }
        MyProcessDialog.showProgressBar(this@AproveStatusActivity, 0)
        val call: Call<List<GetAllUserDataModel>> = RetrofitClient.getUrl().AllUser(sharedPrefsHelper.authToken)
        call.enqueue(object : Callback<List<GetAllUserDataModel>> {
            override fun onResponse(
                call: Call<List<GetAllUserDataModel>>,
                response: Response<List<GetAllUserDataModel>>
            ) {
                if (response.code() == 200) {
                    if (response.body()!!.size> 0) {
                        aproveList =response.body()as ArrayList<GetAllUserDataModel>
                        aproveStatusAdapter= AproveStatusAdapter(aproveList,aproveArray,this@AproveStatusActivity,userassign)
                        binding.followedRecyclerView.adapter=aproveStatusAdapter
                       } else {
                     }
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<List<GetAllUserDataModel>>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AproveStatusActivity, t)

            }
        })
    }

    override fun selectUser(int: Int) {
        roleassign(int)
    }
    fun roleassign(int: Int) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(this) }
        MyProcessDialog.showProgressBar(this@AproveStatusActivity, 0)
        val jsonObject = JsonObject()
      //  jsonObject.addProperty("user_id", sharedPrefsHelper.userId)
        jsonObject.addProperty("user_id", "1")
        jsonObject.addProperty("role_id", int)

        val call: Call<GetAllUserDataModel> = RetrofitClient.getUrl().roleassign(sharedPrefsHelper.authToken,jsonObject)
        call.enqueue(object : Callback<GetAllUserDataModel> {
            override fun onResponse(
                call: Call<GetAllUserDataModel>,
                response: Response<GetAllUserDataModel>          ) {
                if (response.code() == 200) {
                    if (response.body()!= null) {
                        var  GetAllUserDataModel =response.body() as GetAllUserDataModel
                        CommonUtils.toast(this@AproveStatusActivity, "Role successfully assigned ")
                    } else {
                    }
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<GetAllUserDataModel>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AproveStatusActivity, t)

            }
        })
    }

}