package com.bbi.bizbulls.remote

import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.data.signin.ForgotPasswordResponse
import com.bbi.bizbulls.data.signin.LoginResponse
import com.bbi.bizbulls.data.signupresponse.SignupResponse
import com.bbi.bizbulls.model.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @POST("/api/register")
    fun register(@Body model: JsonObject): Call<SignupResponse>

    @POST("/oauth/token")
    fun login(@Body jsonObject: JsonObject): Call<LoginResponse>

    @POST("/api/passwords/reset")
    fun reset(@Body jsonObject: JsonObject): Call<ForgotPasswordResponse>

    @GET("/api/me/profile_links")
    fun foRegistrationSteps(@Header("Authorization") token: String): Call<FoRegistrationSteps>

    @POST("/api/me/personal")
    @FormUrlEncoded
    fun personalDetailsPost(
        @Header("Authorization") token: String,
        @FieldMap data: Map<String, String>): Call<ResponseBody>

    @POST("/api/me/health")
    fun healthDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/express_interest")
    fun expressionInterestDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/checklist")
    fun checkListDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/education")
    fun educationDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/social_identity")
    fun socialIdentityDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/bankinfo")
    fun bankDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/family")
    fun familyDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/children")
    fun childrenDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @POST("/api/me/reference")
    fun personalReferencesDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

////////////////////////////
    // TODO
    @POST("/api/me/kyc/upload")
    @FormUrlEncoded
    fun attachmentsDetailsParams(
        @Header("Authorization") token: String,
        @FieldMap data: Map<String, String>): Call<ResponseBody>


    @Multipart
    @POST("/api/me/kyc/upload")
    fun attachmentsDetailsPost(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part): Call<ResponseBody>

    @POST("/api/me/kyc/upload")
    fun createPost(
        @Header("Authorization") token: String,
        @Body body: RequestBody): Call<ResponseBody>

//////////////////////////////
    @POST("/api/me/authorization")
    fun authorizationDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/status")
    fun getStatus(@Header("Authorization") token: String): Call<StatusData>

    @POST("api/order")
    fun getPaymentToken(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<CashFreeTokenData>

    @POST("/api/update_payment")
    fun updatePayment(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String?): Call<ResponseBody>


    @Multipart
    @POST("/api/assets")
    fun uploadAsset(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part): Call<FileUpload>

    @POST("/api/me/location")
    fun addOfficeLocation(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<AddLocationResponse>

    @POST("/api/me/staff")
    fun addStaffDetail(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/me/staff")
    fun getStaff(@Header("Authorization") token: String): Call<StaffMembersResponse>

    @POST("/api/applyPromo")
    fun applyPromo(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ApplyPromoResponse>

    @GET("/api/rescheduledates")
    fun getDates(@Header("Authorization") token: String): Call<DatesResponse>

    @POST("/api/me/agreement")
    fun uploadAgreements(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonArray): Call<ResponseBody>

    @POST("/api/me/finability")
    fun addFinability(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>
}