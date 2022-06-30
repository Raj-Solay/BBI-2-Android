package com.bbi.bizbulls.remote

import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.data.signin.ForgotPasswordResponse
import com.bbi.bizbulls.data.signin.LoginResponse
import com.bbi.bizbulls.data.signupresponse.SignupResponse
import com.bbi.bizbulls.model.*
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

    @GET("/api/me/")
    fun foRegistrationSteps(@Header("Authorization") token: String): Call<FoRegistrationSteps>

    /*--Personal Details api--*/
    @POST("/api/me/personal")
    fun personalDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/me/personal")
    fun personalDetailsGet(
            @Header("Authorization") token: String): Call<PersonalDetailsViewRes>

    @PUT("/api/me/personal/{user_id}")
    fun personalDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    /*--Health Details api--*/
    @POST("/api/me/health")
    fun healthDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/me/health")
    fun healthDetailsGet(
            @Header("Authorization") token: String): Call<HealthDetailsViewRes>

    @PUT("/api/me/health/{user_id}")
    fun healthDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    /*--Express interest api--*/
    @POST("/api/me/express_interest")
    fun expressionInterestDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/me/express_interest")
    fun expressionInterestDetailsGet(
            @Header("Authorization") token: String): Call<ExpressionDetailsViewRes>

    @PUT("/api/me/express_interest/{user_id}")
    fun expressionInterestDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    /*--Check list--*/
    @POST("/api/me/checklist")
    fun checkListDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @GET("/api/me/checklist")
    fun checkListDetailsGET(
            @Header("Authorization") token: String): Call<ResponseBody>

    @PUT("/api/me/checklist/{user_id}")
    fun checkListDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    /*--Education list--*/
    @POST("/api/me/education")
    fun educationDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/education/{user_id}")
    fun educationDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/education")
    fun educationDetailsGet(
            @Header("Authorization") token: String): Call<EducationDetailsViewRes>

    /*--social_identity--*/

    @POST("/api/me/social_identity")
    fun socialIdentityDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/social_identity/{user_id}")
    fun socialIdentityDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/social_identity")
    fun socialIdentityDetailsGet(
            @Header("Authorization") token: String): Call<ResponseBody>

    /*--bank info--*/
    @POST("/api/me/bankinfo")
    fun bankDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/bankinfo/{user_id}")
    fun bankDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/bankinfo")
    fun bankDetailsGet(
            @Header("Authorization") token: String): Call<BranchDetailsViewRes>

    /*--family--*/
    @POST("/api/me/family")
    fun familyDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/family/{user_id}")
    fun familyDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/family")
    fun familyDetailsGet(
            @Header("Authorization") token: String): Call<FamilyDetailsViewRes>

    /*--children--*/
    @POST("/api/me/children")
    fun childrenDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/children/{user_id}")
    fun childrenDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/children")
    fun childrenDetailsGet(
            @Header("Authorization") token: String): Call<ChildrenDetailsViewRes>

    /*--refrence--*/
    @POST("/api/me/reference")
    fun personalReferencesDetailsPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/reference/{user_id}")
    fun personalReferencesDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/reference")
    fun personalReferencesDetailsGet(
            @Header("Authorization") token: String): Call<ResponseBody>

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

    @PUT("/api/me/authorization/{user_id}")
    fun authorizationDetailsPut(
            @Header("Authorization") token: String,
            @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/authorization")
    fun authorizationDetailsGet(
            @Header("Authorization") token: String): Call<ResponseBody>
}