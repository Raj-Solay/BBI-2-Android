package com.bbi.bizbulls

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bbi.bizbulls.databinding.ActivityAddStaffDetailBinding
import com.bbi.bizbulls.model.FileUpload
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStaffDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStaffDetailBinding
    private var imagePath = ""
    private var resumePath = ""
    private var staffType = 0
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@AddStaffDetailActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStaffDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffType = intent.getIntExtra(Globals.TYPE,0)
        binding?.backNavigation?.setOnClickListener {
            finish()
        }
        binding?.llUploadPhoto?.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->
                    Glide.with(this@AddStaffDetailActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgPhoto)
                    uploadDocument(File(result.get(0).path), 1)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.llUploadResume?.setOnClickListener {
            Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .onResult { result ->
                    Glide.with(this@AddStaffDetailActivity)
                        .load(Uri.fromFile(File(result.get(0).path)))
                        .into(binding.imgResume)
                    uploadDocument(File(result.get(0).path), 2)
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }
        binding.btnAdd.setOnClickListener {
            if(TextUtils.isEmpty(binding.etFirstName.text.toString().trim()))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.fname),Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(binding.etLastName.text.toString().trim()))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.lastn),Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(binding.etMobileNumber.text.toString().trim()))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.mnumber),Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(binding.etEmailAdress.text.toString().trim()))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.email_address),Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(imagePath))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.select_photo),Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(resumePath))
            {
                Toast.makeText(this@AddStaffDetailActivity,getString(R.string.select_resume),Toast.LENGTH_SHORT).show()
            }
            else{
                addStaffDetail()
            }

        }
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

        val sharedPrefsHelper by lazy { SharedPrefsManager(this@AddStaffDetailActivity) }
        var call: Call<FileUpload>? = null
        call = RetrofitClient.getUrl()
            .uploadAsset(sharedPrefsHelper.authToken, part)
        if (MyProcessDialog.myAlertDialog != null && !MyProcessDialog.myAlertDialog?.isShowing!!)
            MyProcessDialog.showProgressBar(this@AddStaffDetailActivity, 0)

        call.enqueue(object : Callback<FileUpload> {
            override
            fun onResponse(
                call: Call<FileUpload>,
                responseObject: Response<FileUpload>
            ) {
                if (responseObject.isSuccessful) {
                    if (type == 1) {
                        imagePath = responseObject.body()?.data?.path ?: ""
                    }
                    if (type == 2) {
                        resumePath = responseObject.body()?.data?.path ?: ""
                    }
                } else {
                    RetrofitClient.showResponseMessage(
                        this@AddStaffDetailActivity,
                        responseObject.code()
                    )

                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AddStaffDetailActivity, t)
            }
        })
    }

    private fun addStaffDetail() {
        MyProcessDialog.showProgressBar(this@AddStaffDetailActivity, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("first_name", binding.etFirstName.text.toString().trim())
        jsonObject.addProperty("last_name", binding.etLastName.text.toString().trim())
        jsonObject.addProperty("email", binding.etEmailAdress.text.toString().trim())
        jsonObject.addProperty("mobile", binding.etMobileNumber.text.toString().trim())
        jsonObject.addProperty("photo", imagePath)
        jsonObject.addProperty("resume", resumePath)
        jsonObject.addProperty("type", staffType)
        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().addStaffDetail(sharedPrefsHelper.authToken, jsonObject)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    CommonUtils.toast(
                        this@AddStaffDetailActivity,
                        this@AddStaffDetailActivity.resources.getString(R.string.something_wrong)
                    )
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AddStaffDetailActivity, t)
            }
        })
    }
}