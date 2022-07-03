package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgAttachmentsBinding
import com.bbi.bizbulls.model.AttachmentsViewRes
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.FileUtils
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class FoAttachmentsFragment(private val stepPosition: Int,private var actionType: Int) : Fragment() {
    private lateinit var binding: FoFrgAttachmentsBinding
    private var imageUri : Uri? = null
    private var selectedDocument = ""
    private  var panCardURI: Uri? = null
    private  var aadhaarCardURI: Uri? = null
    private  var photoURI: Uri? = null
    private  var addressURI: Uri? = null
    private  var individualURI: Uri? = null
    private var uid : String = ""
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
        call?.enqueue(object : Callback<AttachmentsViewRes> {
            override
            fun onResponse(
                    call: Call<AttachmentsViewRes>,
                    responseObject: Response<AttachmentsViewRes>) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (responseObject.body()?.data?.get(0)  != null) {
                        setUpDataInUI(responseObject.body()?.data?.get(0)!!)
                    }
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<AttachmentsViewRes>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

    }
    private fun setUpDataInUI(data: AttachmentsViewRes.Data) {
        uid = data?.id.toString()
    }



    private fun sendAttachmentDetail() {

        if(binding.checkPanCard.isChecked){

        }
        if(binding.checkAadharCard.isChecked){

        }
        if(binding.checkResidenceProof.isChecked){

        }
        if(binding.checkIndividually.isChecked){

        }
        if(binding.checkRecentPhotographOfApplicant.isChecked){

        }

        var fileUtils = FileUtils(requireContext())
        //upload video
        Log.d("AssetLog","panCardURI : "+ panCardURI)
        Log.d("AssetLog","panCardURI : "+ fileUtils.getPath(panCardURI))
        Log.d("AssetLog","aadhaarCardURI : "+ aadhaarCardURI)
        Log.d("AssetLog","aadhaarCardURI : "+ addressURI)
        Log.d("AssetLog","photoURI : "+ photoURI)
        Log.d("AssetLog","individualURI : "+ individualURI)

        val file  = File(fileUtils.getPath(panCardURI))

        val `in`: InputStream = FileInputStream(file)
        val buf: ByteArray = ByteArray(`in`.available())
        while (`in`.read(buf) !== -1);

        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name,
                        RequestBody.create("application/octet-stream".toMediaTypeOrNull(),
                                buf))
                .build()

     //   val requestBody: RequestBody = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, body)

        Log.d("AssetLog","file : "+ file.path)

        val requestBody1: RequestBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
        val partFile1: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.getName(), requestBody1)

        val sharedPrefsHelper by lazy { SharedPrefsManager(requireContext()) }
        var call: Call<ResponseBody>? = null
        call = RetrofitClient.getUrl()
                .uploadAsset(sharedPrefsHelper.authToken, partFile1)
        MyProcessDialog.showProgressBar(requireContext(), 0)
        call?.enqueue(object : Callback<ResponseBody> {
            override
            fun onResponse(
                    call: Call<ResponseBody>,
                    responseObject: Response<ResponseBody>) {
                if (responseObject.code() == 201 || responseObject.code() == 200) {
                    // sharedPrefsHelper.personalDetailID = responseObject.body()?.data?.id.toString()
                  //  responseSuccessMessage(context, stepPosition,0)
                } else {
                    RetrofitClient.showResponseMessage(requireContext(), responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(requireContext(), t)
            }
        })

        val jsonObject = JsonObject()
        jsonObject.addProperty("document_id", "")
        jsonObject.addProperty("document_name", "")
        jsonObject.addProperty("document_type", "")


        // Call remote Api service to save the Document Detail
        var array = JsonArray()
        array.add(jsonObject)
        Log.d("JsonArray","Arary : "+ array.toString())
        //FranchiseeRegistrationViewModel().sendDetailPostRequest(requireActivity(), array, stepPosition,actionType,uid)
    }

    private fun setPreviewImage(selectedDocument: String, uri: Uri){
        when (selectedDocument) {
            requireActivity().resources.getString(R.string.attachmentPanCard) -> {
                binding.imgpancard.setImageURI(uri)
                panCardURI = uri
            }
            requireActivity().resources.getString(R.string.attachmentAadhaar) -> {
                binding.imgaadharcard.setImageURI(uri)
                aadhaarCardURI = uri
            }
            requireActivity().resources.getString(R.string.attachmentResidential) -> {
                binding.imgaResidence.setImageURI(uri)
                addressURI = uri
            }
            requireActivity().resources.getString(R.string.attachmentPhoto) -> {
                binding.imgaRecentPhotographOfApplicant.setImageURI(uri)
                photoURI = uri
            }
            requireActivity().resources.getString(R.string.attachmentIndividuallyField) -> {
                binding.imgaIndividually.setImageURI(uri)
                individualURI = uri
            }
        }

    }
    private fun createImageUri(fileName: String): Uri? {
        val name = fileName.replace("\\s".toRegex(), "")
        val image = File(context?.filesDir , "$name.png")
        return context?.let {
            FileProvider.getUriForFile(
                it,
                "com.bbi.bizbulls.fileProvider",
                image)
        }
    }
    private fun selectImage(fileName: String) {
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
    }
}