package com.biz.bizbulls.menu.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImagesModel (
    @SerializedName("images")
    val images: ArrayList<String>? = ArrayList(),
) : Serializable