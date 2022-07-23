package com.bbi.bizbulls

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.databinding.ActivityAgreementsStatusBinding
import com.bbi.bizbulls.media.Adapter
import com.bbi.bizbulls.model.FileUpload
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.FileSeletionDialog
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.FileUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yanzhenjie.album.Action
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.widget.divider.Api21ItemDivider
import com.yanzhenjie.album.widget.divider.Divider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AgreementsStatusActivity : AppCompatActivity() {
    var binding: ActivityAgreementsStatusBinding? = null
    private var mAlbumFiles: ArrayList<AlbumFile>? = null
    private var mAlbumFilesThirdParty: ArrayList<AlbumFile>? = null
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@AgreementsStatusActivity) }

    private var mAdapter: Adapter? = null
    private var mAdapterThirdParty: Adapter? = null

    private var franchiseeImageList = mutableListOf<String>()
    private var thirdPartyImageList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementsStatusBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        setFranchiseeData()
        setThirdPartyData()

        binding?.backNavigation?.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding?.btnCancel?.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding?.btnSubmit?.setOnClickListener {
               if(franchiseeImageList.isNotEmpty()/* && thirdPartyImageList.isNotEmpty()*/)
               {
                   uploadAgreements()
               }
            else{
                Toast.makeText(this@AgreementsStatusActivity, getString(R.string.atlest_one_error),Toast.LENGTH_SHORT).show()
               }
        }

        binding?.llUploadFranchiseeAgreement?.setOnClickListener {
            showDialog()
        }

        binding?.llUploadThirdPartyAgreement?.setOnClickListener {
            Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(3)
                .checkedList(mAlbumFilesThirdParty)
                .onResult { result ->
                    mAlbumFilesThirdParty = result
                    mAdapterThirdParty?.notifyDataSetChanged(mAlbumFilesThirdParty)
                    mAlbumFilesThirdParty?.forEach {
                        uploadDocument(File(it.path),2)
                    }
                }
                .onCancel(object : Action<String?> {
                    override fun onAction(result: String) {}
                })
                .start()
        }

    }
    fun showDialog(){
        var fileSelectionDialog = FileSeletionDialog(this@AgreementsStatusActivity)
        fileSelectionDialog.show()

        fileSelectionDialog.txtImage!!.setOnClickListener {
            fileSelectionDialog.dismiss()
            selectImageIntent()
        }
        fileSelectionDialog.txtPdf!!.setOnClickListener {
            fileSelectionDialog.dismiss()
            selectPdfIntent()
        }
    }

    fun selectPdfIntent(){
        val intentPDF = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, true)
        }
        startActivityForResult(
            Intent.createChooser(intentPDF, "Open with"),
            1001
        )
    }
    fun selectImageIntent(){
        Album.image(this) // Image selection.
            .multipleChoice()
            .camera(true)
            .columnCount(3)
            .selectCount(3)
            .checkedList(mAlbumFiles)
            .onResult { result ->
                mAlbumFiles = result
                mAdapter?.notifyDataSetChanged(mAlbumFiles)
                mAlbumFiles?.forEach {
                    uploadDocument(File(it.path),1)
                }
            }
            .onCancel(object : Action<String?> {
                override fun onAction(result: String) {}
            })
            .start()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                1001 -> {
                    data?.let {
                        it.data?.also { uri ->
                            Log.d("UploadPDF","URI : "+ uri)
                            var fileUtils = FileUtils(this@AgreementsStatusActivity)

                            Log.d("UploadPDF","getPath : "+   fileUtils.getPath(uri))
                            uploadDocument(File(fileUtils.getPath(uri)),1)
                        }
                    }
                }
            }
        }
    }
    private fun setFranchiseeData() {
        binding?.recyclerViewFranchisee?.layoutManager = GridLayoutManager(this, 3)
        val divider: Divider = Api21ItemDivider(Color.TRANSPARENT, 10, 10)
        binding?.recyclerViewFranchisee?.addItemDecoration(divider)
        mAdapter = Adapter(
            this
        ) { view, position -> }.also { mAdapter = it }
        binding?.recyclerViewFranchisee?.adapter = mAdapter
    }

    private fun setThirdPartyData() {
        binding?.recyclerViewThirdParty?.layoutManager = GridLayoutManager(this, 3)
        val divider: Divider = Api21ItemDivider(Color.TRANSPARENT, 10, 10)
        binding?.recyclerViewThirdParty?.addItemDecoration(divider)
        mAdapterThirdParty = Adapter(
            this
        ) { view, position -> }.also { mAdapterThirdParty = it }
        binding?.recyclerViewThirdParty?.adapter = mAdapterThirdParty
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

        val sharedPrefsHelper by lazy { SharedPrefsManager(this@AgreementsStatusActivity) }
        var call: Call<FileUpload>? = null
        call = RetrofitClient.getUrl()
            .uploadAsset(sharedPrefsHelper.authToken, part)
        if(MyProcessDialog.myAlertDialog!=null && !MyProcessDialog.myAlertDialog?.isShowing!!)
        MyProcessDialog.showProgressBar(this@AgreementsStatusActivity, 0)

        call.enqueue(object : Callback<FileUpload> {
            override
            fun onResponse(
                call: Call<FileUpload>,
                responseObject: Response<FileUpload>
            ) {
                if (responseObject.isSuccessful) {
                    if (type == 1) {
                        Toast.makeText(this@AgreementsStatusActivity,"Upload completed",Toast.LENGTH_SHORT).show()
                        responseObject.body()?.data?.path?.let { franchiseeImageList.add(it) }
                    }
                    if (type == 2) {

                        responseObject.body()?.data?.path?.let { thirdPartyImageList.add(it) }
                    }
                } else {
                    RetrofitClient.showResponseMessage(
                        this@AgreementsStatusActivity,
                        responseObject.code()
                    )

                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AgreementsStatusActivity, t)
            }
        })
    }

    private fun uploadAgreements() {
        MyProcessDialog.showProgressBar(this@AgreementsStatusActivity, 0)
        val jsonObjectArray = JsonArray()
        franchiseeImageList.forEach {
            val jsonObject = JsonObject()
            jsonObject.addProperty("file_type", "client_franchisee_agreements")
            jsonObject.addProperty("file_name", it)
            jsonObjectArray.add(jsonObject)
        }
        thirdPartyImageList.forEach {
            val jsonObject = JsonObject()
            jsonObject.addProperty("file_type", "third_party_agreement")
            jsonObject.addProperty("file_name", it)
            jsonObjectArray.add(jsonObject)
        }

        val call: Call<ResponseBody> =
            RetrofitClient.getUrl().uploadAgreements(sharedPrefsHelper.authToken, jsonObjectArray)
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
                        this@AgreementsStatusActivity,
                        this@AgreementsStatusActivity.resources.getString(R.string.something_wrong)
                    )
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@AgreementsStatusActivity, t)
            }
        })
    }
}