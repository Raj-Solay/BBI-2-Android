package com.bbi.bizbulls.ui.registrationforfo.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoFrgAttachmentsBinding
import com.bbi.bizbulls.utils.CommonUtils
import java.io.File


class FoAttachmentsFragment : Fragment() {
    private lateinit var binding: FoFrgAttachmentsBinding
    private var imageUri : Uri? = null
    private var selectedDocument = ""
    private  var panCardURI: Uri? = null
    private  var aadhaarCardURI: Uri? = null
    private  var photoURI: Uri? = null
    private  var addressURI: Uri? = null
    private  var individualURI: Uri? = null

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
            CommonUtils.toast(requireActivity(), "Coming soon")
          //  sendAttachmentDetail()
        }

        return binding.root
    }

    private fun sendAttachmentDetail() {
        // TODO
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