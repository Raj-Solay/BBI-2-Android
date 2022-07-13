package com.bbi.bizbulls.remote

import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.data.signin.ForgotPasswordResponse
import com.bbi.bizbulls.data.signin.LoginResponse
import com.bbi.bizbulls.data.signupresponse.SignupResponse
import com.bbi.bizbulls.model.*
import com.google.gson.JsonArray
import com.bbi.bizbulls.model.StatusData
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
    fun userRoleDetails(@Header("Authorization") token: String): Call<UserDetails>

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
            @Header("Authorization") token: String): Call<CheckListDetailsViewRes>

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
            @Header("Authorization") token: String): Call<SocialIdentifyViewRes>

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
            @Header("Authorization") token: String): Call<PersonalReferenceViewRes>

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
            @Header("Authorization") token: String): Call<AuthorizationViewRes>

    /*--Document api--*/
    @POST("/api/me/document")
    fun documentPost(
            @Header("Authorization") token: String,
            @Body jsonArray: JsonArray): Call<ResponseBody>

    @PUT("/api/me/document/{user_id}")
    fun documentPut(
            @Header("Authorization") token: String,
            @Body jsonArray: JsonArray,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/document")
    fun documentGet(
            @Header("Authorization") token: String): Call<DocumentsViewRes>

    /*--upload image--*/

    @Multipart
    @POST("/api/assets")
    fun uploadAsset(
            @Header("Authorization") token: String,
            @Part image: MultipartBody.Part): Call<AssetsRes>

    @GET("/api/status")
    fun getStatus(@Header("Authorization") token: String): Call<StatusData>

    /*--Work History--*/
    @POST("/api/me/work")
    fun workHistoryPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/work/{user_id}")
    fun workHistoryPut(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/work")
    fun workHistoryGet(
        @Header("Authorization") token: String): Call<ResponseBody>

    /*--Referral Details--*/
    @POST("/api/me/referral")
    fun referralPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/referral/{user_id}")
    fun referralPut(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/referral")
    fun referralGet(
        @Header("Authorization") token: String): Call<ReferralDetailsView>

    /*--Leave and Holidays--*/
    @POST("/api/me/leave")
    fun leaveHolidaysPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/leave/{user_id}")
    fun leaveHolidaysPut(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/leave")
    fun leaveHolidaysGet(
        @Header("Authorization") token: String): Call<LeavePolicyView>

    /*--Professional Reference--*/
    @POST("/api/me/professional_references")
    fun professionalReferencesPost(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject): Call<ResponseBody>

    @PUT("/api/me/professional_references/{user_id}")
    fun professionalReferencesPut(
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject,@Path(value = "user_id", encoded = true) userId : String): Call<ResponseBody>

    @GET("/api/me/professional_references")
    fun professionalReferencesGet(
        @Header("Authorization") token: String): Call<ProfessionalReferenceViewRes>

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