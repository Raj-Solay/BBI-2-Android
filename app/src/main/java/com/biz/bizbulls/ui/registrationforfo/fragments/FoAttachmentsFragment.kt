package com.biz.bizbulls.ui.registrationforfo.fragments

import android.Manifest.permission.*
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.biz.bizbulls.R
import com.biz.bizbulls.databinding.FoFrgAttachmentsBinding
import com.biz.bizbulls.model.AssetUploadReq
import com.biz.bizbulls.model.AssetsRes
import com.biz.bizbulls.model.DocumentsViewRes
import com.biz.bizbulls.remote.RetrofitClient
import com.biz.bizbulls.sharedpref.SharedPrefsManager
import com.biz.bizbulls.ui.registrationforfo.FranchiseeRegistrationViewModel
import com.biz.bizbulls.utils.CommonUtils
import com.biz.bizbulls.utils.FileUtils
import com.biz.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class FoAttachmentsFragment(private val stepPosition: Int, private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgAttachmentsBinding
    private var imageUri: Uri? = null
    private var selectedDocument = ""
    private var panCardURI: Uri? = null
    private var aadhaarCardURI: Uri? = null
    private var photoURI: Uri? = null
    private var addressURI: Uri? = null
    private var individualURI: Uri? = null
    private var bbiURI: Uri? = null
    private var uid: String = ""

    /*
    * Response result from Gallery
    * Getting the Image path from choose the Gallery option for Image upload
    * */
    private val imageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            //  binding.imgpancard.setImageURI(it)
            setPreviewImage(selectedDocument, it)
            println("________ImagePathGallery - ${it.path}")
        } else {
            CommonUtils.toast(
                    requireActivity(),
                    requireActivity().resources.getString(R.string.something_wrong)
            )
        }
    }

    /*
    * Response result from Camera
    * Getting the Image path from choose the Camera option for Image upload
    * */
    private val imageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            //  binding.imgpancard.setImageURI(imageUri)
            imageUri?.let { it1 -> setPreviewImage(selectedDocument, it1) }
            println("_______ImagePathCamera - ${imageUri?.path}")
        } else {
            CommonUtils.toast(
                    requireActivity(),
                    requireActivity().resources.getString(R.string.something_wrong)
            )
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
        val result1 = ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> if (grantResults.isNotEmpty()) {
                val locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (locationAccepted && cameraAccepted) {
                    //permission granted
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                            //You need to allow access to both the permissions
                            return
                        }
                    }
                }
            }
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FoFrgAttachmentsBinding.inflate(inflater, container, false)

        binding.imgpancard.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentPanCard)
            selectImage(selectedDocument)
        }

        binding.imgaadharcard.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentAadhaar)
            selectImage(selectedDocument)
        }

        binding.imgaResidence.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentResidential)
            selectImage(selectedDocument)
        }

        binding.imgaRecentPhotographOfApplicant.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentPhoto)
            selectImage(selectedDocument)
        }

        binding.imgaIndividually.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentIndividuallyField)
            selectImage(selectedDocument)
        }
        binding.imgaBBI.setOnClickListener {
            selectedDocument = requireActivity().resources.getString(R.string.attachmentarbitary)
            selectImage(selectedDocument)
        }
        binding.stepSubmit.setOnClickListener {
            // CommonUtils.toast(requireActivity(), "Coming soon")
            sendAttachmentDetail()
        }

        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                getRecordFromAPI(false)
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                getRecordFromAPI(true)
                binding.stepSubmit.setText("Update")
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                binding.stepSubmit.setText("Submit")
            }
        }
        return binding.root
    }

    private fun getRecordFromAPI(isFromEdit: Boolean) {
        MyProcessDialog.showProgressBar(requireContext(), 0)
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        val call = RetrofitClient.getUrl().documentGet(sharedPrefsHelper.authToken)
        call?.enqueue(object : Callback<DocumentsViewRes> {
            override
            fun onResponse(
                    call: Call<DocumentsViewRes>,
                    responseObject: Response<DocumentsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (!responseObject.body()!!.data.isNullOrEmpty() && responseObject.body()?.data?.get(0) != null) {
                        setUpDataInUI(responseObject.body()?.data)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<DocumentsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }

    private fun setUpDataInUI(data: List<DocumentsViewRes.Data>?) {
        uid = data?.get(0)?.id.toString()

       var rList = data?.reversed()
        try {
            if (rList?.get(0) != null) {
                // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
               /* Picasso.get().load(rList.get(0).documentName.toString())
                    .into(binding.imgpancard)*/
                val assetReq = AssetUploadReq()
                //image/jpeg
                assetReq.mimeType = "image/jpeg"
                assetReq.name = rList.get(0).documentName.toString()
                assetReq.id = "1"
                uploadFileList.add(0, assetReq)
            }
        }catch (e : Exception){

        }

        try {
            if(rList?.size!! > 1){
                if (rList?.get(1) != null) {
                    // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
                   /* Picasso.get().load(rList.get(1).documentName.toString())
                        .into(binding.imgaadharcard)*/
                    val assetReq = AssetUploadReq()
                    //image/jpeg
                    assetReq.mimeType = "image/jpeg"
                    assetReq.name = rList.get(1).documentName.toString()
                    assetReq.id = "1"
                    uploadFileList.add(1, assetReq)
                }
            }
        }catch (e : Exception){

        }

        try {
            if(rList?.size!! > 2){
                if (rList?.get(2) != null) {
                    // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
                    /*Picasso.get().load(rList.get(2).documentName.toString())
                        .into(binding.imgaResidence)*/
                    val assetReq = AssetUploadReq()
                    //image/jpeg
                    assetReq.mimeType = "image/jpeg"
                    assetReq.name = rList.get(2).documentName.toString()
                    assetReq.id = "2"
                    uploadFileList.add(2, assetReq)
                }
            }
        }catch (e : Exception){

        }

        try {
            if(rList?.size!! > 3){
                if (rList?.get(3) != null) {
                    // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
                  /*  Picasso.get().load(rList.get(3).documentName.toString())
                        .into(binding.imgaIndividually)*/
                    val assetReq = AssetUploadReq()
                    //image/jpeg
                    assetReq.mimeType = "image/jpeg"
                    assetReq.name = rList.get(3).documentName.toString()
                    assetReq.id = "3"
                    uploadFileList.add(3, assetReq)
                }
            }
        }catch (e : Exception){

        }

        try {
            if(rList?.size!! > 4){
                if (rList?.get(4) != null) {
                    // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
                   /* Picasso.get().load(rList.get(4).documentName.toString())
                        .into(binding.imgaRecentPhotographOfApplicant)*/
                    val assetReq = AssetUploadReq()
                    //image/jpeg
                    assetReq.mimeType = "image/jpeg"
                    assetReq.name = rList.get(4).documentName.toString()
                    assetReq.id = "4"
                    uploadFileList.add(4, assetReq)
                }
            }

        }catch (e : Exception){

        }

        try {
            if (rList?.get(5) != null) {
                // Picasso.get().load(data.get(0)?.documentName).into(holder.itemBinding.itemIcon)
               /* Picasso.get().load(rList.get(5).documentName.toString())
                    .into(binding.imgaBBI)*/
                val assetReq = AssetUploadReq()
                //image/jpeg
                assetReq.mimeType = "image/jpeg"
                assetReq.name = rList.get(5).documentName.toString()
                assetReq.id = "5"
                uploadFileList.add(5, assetReq)
            }

        }catch (e : Exception){

        }


        var isEditable = false
        when (actionType) {
            CommonUtils.ACTION_TYPE_VIEW -> {
                isEditable = false
                binding.stepSubmit.visibility = View.INVISIBLE
            }
            CommonUtils.ACTION_TYPE_EDIT -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
            CommonUtils.ACTION_TYPE_ADD -> {
                isEditable = true
                binding.stepSubmit.visibility = View.VISIBLE
            }
        }

        binding.checkPanCard.isEnabled = isEditable
        binding.checkAadharCard.isEnabled = isEditable
        binding.checkResidenceProof.isEnabled = isEditable
        binding.checkIndividually.isEnabled = isEditable
        binding.checkRecentPhotographOfApplicant.isEnabled = isEditable
        binding.checkArbitaryDocuments.isEnabled = isEditable


    }


    var uploadFileList = arrayListOf<AssetUploadReq>()

    /* 1	Pan card
     2	Aadhaar
     3	Residence Proof
     4	Bank Statement/ Passbook
     5	downloaded & Signed Registration Form
     6	BIZ BULLS Arbitrary Agreement*/
    private fun uploadFileOnServer(uri: Uri, type: Int) {
        var fileUtils = FileUtils(requireContext())

        val file = File(fileUtils.getPath(uri))

        var mimeType = requireContext().contentResolver.getType(uri!!)
        val requestBody: RequestBody = RequestBody.create(mimeType?.toMediaTypeOrNull(), file)
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("file",
                file.name, requestBody)

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        var call: Call<AssetsRes>? = null
        call = RetrofitClient.getUrl()
                .uploadAsset1(sharedPrefsHelper.authToken, part)
        MyProcessDialog.showProgressBar(requireContext(), 0)
        call?.enqueue(object : Callback<AssetsRes> {
            override
            fun onResponse(
                    call: Call<AssetsRes>,
                    responseObject: Response<AssetsRes>) {
                if (responseObject.code() == 201 || responseObject.code() == 200) {
                    try{
                        var assetReq = AssetUploadReq()
                        var assetRes = responseObject.body()?.data
                        //image/jpeg
                        var fType = mimeType?.split("/")
                        assetReq.mimeType = fType?.get(1).toString()
                        assetReq.name = assetRes?.links!!.full.toString()
                        assetReq.id = type.toString()
                        uploadFileList.add(type - 1, assetReq)
                    }catch (e :Exception){

                    }

                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<AssetsRes>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })
    }

    private fun sendAttachmentDetail() {


        // uploadFileOnServer()

        // return


        if (binding.checkPanCard.isChecked) {

        }
        if (binding.checkAadharCard.isChecked) {

        }
        if (binding.checkResidenceProof.isChecked) {

        }
        if (binding.checkIndividually.isChecked) {

        }
        if (binding.checkRecentPhotographOfApplicant.isChecked) {

        }
        if (binding.checkArbitaryDocuments.isChecked) {

        }


        var array = JsonArray()
        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        uploadFileList.forEach {
            val jsonObjectBBI = JsonObject()
            jsonObjectBBI.addProperty("user_id", "" + sharedPrefsHelper.registerFormUserId)
            jsonObjectBBI.addProperty("document_id", "" + it.id)
            jsonObjectBBI.addProperty("document_name", "" + it.name)
            jsonObjectBBI.addProperty("document_type", "" + it.mimeType)
            array.add(jsonObjectBBI)
        }

        Log.d("JSONArray", "" + array.toString())
        // Call remote Api service to save the Document Detail

        FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(),
            array, stepPosition, actionType, uid)
    }

    fun getImageRequestBody(path: String, key: String): MultipartBody.Part? {
        val file = File(path)
        val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

    /**
     * get URI from Bitmap
     */
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
    private fun setPreviewImage(selectedDocument: String, uri: Uri) {
        when (selectedDocument) {
            requireActivity().resources.getString(R.string.attachmentPanCard) -> {
               // binding.imgpancard.setImageURI(uri)
                panCardURI = uri
                uploadFileOnServer(uri, 1)
            }
            requireActivity().resources.getString(R.string.attachmentAadhaar) -> {
            //    binding.imgaadharcard.setImageURI(uri)
             /*   val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri))
                } else {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                }*/
                aadhaarCardURI = uri
                uploadFileOnServer(uri, 2)
            }
            requireActivity().resources.getString(R.string.attachmentResidential) -> {
             //   binding.imgaResidence.setImageURI(uri)
                addressURI = uri
                uploadFileOnServer(uri, 3)
            }
            requireActivity().resources.getString(R.string.attachmentPhoto) -> {
            //    binding.imgaRecentPhotographOfApplicant.setImageURI(uri)
                photoURI = uri
                uploadFileOnServer(uri, 4)
            }
            requireActivity().resources.getString(R.string.attachmentIndividuallyField) -> {
             //   binding.imgaIndividually.setImageURI(uri)
                individualURI = uri
                uploadFileOnServer(uri, 5)
            }
            requireActivity().resources.getString(R.string.attachmentarbitary) -> {
              //  binding.imgaBBI.setImageURI(uri)
                bbiURI = uri
                uploadFileOnServer(uri, 6)
            }
        }

    }

    private fun createImageUri(fileName: String): Uri? {
        val name = fileName.replace("\\s".toRegex(), "")
        val image = File(context?.filesDir, "$name.png")
        return context?.let {
            FileProvider.getUriForFile(
                    it,
                    "com.biz.bizbulls.fileProvider",
                    image)
        }
    }

    private fun selectImage(fileName: String) {
        if (checkPermission()) {
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Add Photo!")
            builder.setItems(options) { dialog, item ->
                if (options[item] == "Take Photo") {
                    imageUri = createImageUri(fileName)!!
                    imageFromCamera.launch(imageUri)
                } else if (options[item] == "Choose from Gallery") {
                    imageFromGallery.launch("image/*")
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        } else {
            requestPermission()
        }

    }
}