package com.biz.bizbulls.menu.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GetAllUserDataModel (
    @SerializedName("id")
    val id:String,
  @SerializedName("name")
    val name:String,
  @SerializedName("uuid")
    val uuid:String,
  @SerializedName("email")
    val email:String,
  @SerializedName("email_verified_at")
    val email_verified_at:String,
  @SerializedName("phone")
    val phone:String,
  @SerializedName("created_at")
    val created_at:String,
  @SerializedName("updated_at")
    val updated_at:String,
  @SerializedName("profile_pic")
    val profile_pic:String,
 @SerializedName("role_id")
    val role_id:String) : Serializable