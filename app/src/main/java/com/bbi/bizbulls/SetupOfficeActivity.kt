package com.bbi.bizbulls

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbi.bizbulls.databinding.ActivitySetupOfficeBinding
import com.bbi.bizbulls.model.FileUpload
import com.bbi.bizbulls.model.StaffMembersResponse
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.adapter.StaffMemberAdapter
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.bumptech.glide.Glide
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SetupOfficeActivity : AppCompatActivity() {
    lateinit var mAdapter: StaffMemberAdapter
    private lateinit var binding: ActivitySetupOfficeBinding
    private var imagePath1 = ""
    private var imagePath2 = ""
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@SetupOfficeActivity) }
    var staffList = mutableListOf<StaffMembersResponse.StaffMember>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupOfficeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnAddCounceller.setOnClickListener{
                val intent = Intent(this@SetupOfficeActivity, AddStaffDetailActivity::class.java)
                intent.putExtra(Globals.TYPE,1)
                startActivityForResult(intent, Globals.REQUEST_CODE_ADD_STAFF)
            }
            btnAddFieldStaff.setOnClickListener{
                val intent = Intent(this@SetupOfficeActivity, AddStaffDetailActivity::class.java)
                intent.putExtra(Globals.TYPE,2)
                startActivityForResult(intent, Globals.REQUEST_CODE_ADD_STAFF)
            }
        }
        binding.btnsubmitrefferance.setOnClickListener {
            onBackPressed()
        }
        setListData()
        binding?.backNavigation?.setOnClickListener {
            finish()
        }
        binding?.llUploadPhoto1?.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->
                    Glide.with(this@SetupOfficeActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgPhoto1)
                    uploadDocument(File(result.get(0).path), 1)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadPhoto2?.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->
                    Glide.with(this@SetupOfficeActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgPhoto2)
                    uploadDocument(File(result.get(0).path), 2)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        getStaff(this@SetupOfficeActivity)
    }
    private fun setListData() {
        binding?.recycler?.layoutManager = LinearLayoutManager(this)
        mAdapter = StaffMemberAdapter(
            this,staffList
        )
        binding?.recycler?.adapter = mAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Globals.REQUEST_CODE_ADD_STAFF && resultCode == RESULT_OK) {
            getStaff(this@SetupOfficeActivity)
        }

    }
    private fun getStaff(context: Context) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<StaffMembersResponse> =
            RetrofitClient.getUrl().getStaff(sharedPrefsHelper.authToken)
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<StaffMembersResponse> {
            override
            fun onResponse(
                call: Call<StaffMembersResponse>,
                responseObject: Response<StaffMembersResponse>
            ) {
                if (responseObject.isSuccessful) {

                    staffList = responseObject.body()?.data as MutableList<StaffMembersResponse.StaffMember>
                    mAdapter = StaffMemberAdapter(
                        this@SetupOfficeActivity,staffList
                    )
                    binding?.recycler?.adapter = mAdapter
                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<StaffMembersResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }
    private fun uploadDocument(file: File, type: Int) {
        val uri = FileProvider.getUriForFile(
            this,
            this.packageName + ".fileProvider",
            file
        )
        val requestBody =
            RequestBody.create(contentResolver.getType(uri)?.toMediaTypeOrNull(), file)
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)

        val sharedPrefsHelper by lazy { SharedPrefsManager(this@SetupOfficeActivity) }
        var call: Call<FileUpload>? = null
        call = RetrofitClient.getUrl()
            .uploadAsset(sharedPrefsHelper.authToken, part)
        if (MyProcessDialog.myAlertDialog != null && !MyProcessDialog.myAlertDialog?.isShowing!!)
            MyProcessDialog.showProgressBar(this@SetupOfficeActivity, 0)

        call.enqueue(object : Callback<FileUpload> {
            override
            fun onResponse(
                call: Call<FileUpload>,
                responseObject: Response<FileUpload>
            ) {
                if (responseObject.isSuccessful) {
                    if (type == 1) {
                        imagePath1 = responseObject.body()?.data?.path ?: ""
                    }
                    if (type == 2) {
                        imagePath2 = responseObject.body()?.data?.path ?: ""
                    }
                } else {
                    RetrofitClient.showResponseMessage(
                        this@SetupOfficeActivity,
                        responseObject.code()
                    )

                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@SetupOfficeActivity, t)
            }
        })
    }
}