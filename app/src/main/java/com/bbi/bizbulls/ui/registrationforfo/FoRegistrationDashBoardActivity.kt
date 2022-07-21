package com.bbi.bizbulls.ui.registrationforfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.DashboardActivity
import com.bbi.bizbulls.ProjectInfoActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.data.health.FormStatus
import com.bbi.bizbulls.databinding.FoActivityDashboardBinding
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.registrationforfo.FranchiseeRegistrationActivity
import com.bbi.bizbulls.ui.registrationforfo.adapters.FoRegistrationDashboardAdapter
import com.bbi.bizbulls.ui.registrationforfo.interfaces.IFoRegistrationStepsClickListener
import com.bbi.bizbulls.utils.CommonUtils
import com.bbi.bizbulls.utils.Globals
import com.bbi.bizbulls.utils.MyProcessDialog
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoRegistrationDashBoardActivity : AppCompatActivity(), IFoRegistrationStepsClickListener {
    private lateinit var binding: FoActivityDashboardBinding
    lateinit var  sharedPrefsHelper:SharedPrefsManager
    private lateinit var foRegistrationViewModel: FranchiseeRegistrationViewModel
    private var userRole = 0
    private var userId = "";
    lateinit var  adapter: FoRegistrationDashboardAdapter
    var listSteps = arrayListOf<Data>()

    private fun activityCalling() {
        val i = Intent(this, ProjectInfoActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FoActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefsHelper= SharedPrefsManager(this)
        foRegistrationViewModel = ViewModelProvider(this)[FranchiseeRegistrationViewModel::class.java]

        if (CommonUtils.isNetworkConnected(this)) {
            // Call remote Api service for FO registration steps
           // foRegistrationViewModel.callRegisterApi(this,sharedPrefsHelper)
        } else {
            CommonUtils.toast(this, this.resources.getString(R.string.no_internet))
        }

        foRegistrationViewModel.allSteps.observe(this) { steps ->
         //   setUI(steps)
        }
        binding.foBack.setOnClickListener {
            activityCalling()
        }
        try{
            userRole =  sharedPrefsHelper.role.toInt()
            userId =  sharedPrefsHelper.userId
            Log.d("Useid","1=>" + userRole)
            Log.d("Useid","2=>" + userId)
        }catch (e : Exception){

        }
        binding.btnNextProcess.setOnClickListener {
            if(!isFormStatusComplted){
                Toast.makeText(this@FoRegistrationDashBoardActivity,"Please completed the Form process First.",Toast.LENGTH_SHORT).show()
            }else{
                CommonUtils.isRedirectToStatus = true
                val i = Intent(this, DashboardActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                finish()
            }
        }

        listSteps.add(Data(1,R.drawable.icn_personal_profile,"Personal Profile","1",0))
        listSteps.add(Data(2,R.drawable.icn_health_details,"Health Details","",0))


       /* if(Globals.USER_TYPE_EMPLOYEE == userRole){

        }else{
            listSteps.add(Data(3,R.drawable.icn_interest_details,"Expression of interest details","",0))
            listSteps.add(Data(4,R.drawable.icn_checklist_details,"Check list details","",0))

        }*/

        listSteps.add(Data(3,R.drawable.icn_interest_details,"Expression of interest details","",0))
        listSteps.add(Data(4,R.drawable.icn_checklist_details,"Check list details","",0))

        listSteps.add(Data(5,R.drawable.icn_education,"Academic education details","",0))
        listSteps.add(Data(6,R.drawable.icn_social_identify,"Social Identity details","",0))
        listSteps.add(Data(7,R.drawable.icn_bank_details,"Bank details","",0))
        listSteps.add(Data(8,R.drawable.icn_family_details,"Family details","",0))
        listSteps.add(Data(9,R.drawable.icn_children_details,"Children details","",0))
        listSteps.add(Data(10,R.drawable.icn_personal_refrence,"Personal references details","",0))

        if(Globals.USER_TYPE_EMPLOYEE == userRole){
       /*     listSteps.add(Data(13,R.drawable.icn_family_details,"Work History","",0))
            listSteps.add(Data(14,R.drawable.icn_family_details,"Professional References","",0))
            listSteps.add(Data(15,R.drawable.icn_family_details,"Leave & Holiday Requests","",0))
            listSteps.add(Data(16,R.drawable.icn_family_details,"Referral Details","",0))
*/
        }else{
//            listSteps.add(Data(11,R.drawable.icn_attachment,"Attachment details",""))
        }

        listSteps.add(Data(11,R.drawable.icn_attachment,"Attachment details","",0))

        listSteps.add(Data(12,R.drawable.icn_authorization,"Authorization details","",0))

        var gson = Gson();
        var json  = gson.toJson(listSteps)
       // Log.d("Response : ","Gsn : " + json)

        setUI(listSteps)

        getstatus()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUI(steps: List<Data>) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = FoRegistrationDashboardAdapter(this, steps, this)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onStepsClickListener(model: Data, position: Int, actionType: Int) {
        //Create the bundle
        val bundle = Bundle()
        val i = Intent(this, FranchiseeRegistrationActivity::class.java)
        //Add your data to bundle
        bundle.putString("name", model.linkName)
        bundle.putString("status", model.profileUpdatedOn)
        bundle.putInt("id", model.id.toInt())
        bundle.putInt("position", position)
        bundle.putInt("actionType", actionType)
        //Add the bundle to the intent
        i.putExtras(bundle)
        startActivity(i)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val i = Intent(this, ProjectInfoActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
    var isFormStatusComplted = false
    private fun getstatus() {
        MyProcessDialog.showProgressBar(this@FoRegistrationDashBoardActivity, 0)
        var token:String=sharedPrefsHelper.authToken
        val call = RetrofitClient.getUrl().formStatus(token)
        call?.enqueue(object : Callback<FormStatus> {
            override
            fun onResponse(
                call: Call<FormStatus>,
                responseObject: Response<FormStatus>
            ) {
                if (responseObject.code() == 200 || responseObject.code() == 201) {
                    if (responseObject.body()?.data!= null) {
                        listSteps.get(0).status= responseObject.body()!!.data.personal
                        listSteps.get(1).status= responseObject.body()!!.data.Healthdetail
                        listSteps.get(2).status= responseObject.body()!!.data.UserExpressionInterest
                        listSteps.get(3).status= responseObject.body()!!.data.UserChecklist
                        listSteps.get(4).status= responseObject.body()!!.data.Educationaldetail
                        listSteps.get(5).status= responseObject.body()!!.data.UserSocialIdentityDetail
                        listSteps.get(6).status= responseObject.body()!!.data.Bankdetail
                        listSteps.get(7).status= responseObject.body()!!.data.FamilyDetail
                        listSteps.get(8).status= responseObject.body()!!.data.Childrendetail
                        listSteps.get(9).status= responseObject.body()!!.data.PersonalReference
                        listSteps.get(10).status= responseObject.body()!!.data.UserDocument
                        listSteps.get(11).status= responseObject.body()!!.data.Authorization

                        var isCompleted = 0
                        if(responseObject.body()!!.data.personal == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.Healthdetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.UserExpressionInterest == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.UserChecklist == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.Educationaldetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.UserSocialIdentityDetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.Bankdetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.FamilyDetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.Childrendetail == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.PersonalReference == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.UserDocument == 1){
                            isCompleted++
                        }
                        if(responseObject.body()!!.data.Authorization  == 1){
                            isCompleted++
                        }

                        isFormStatusComplted = isCompleted == 12
                        sharedPrefsHelper.isFormCompleted = isFormStatusComplted
                        if(isFormStatusComplted){
                            binding.btnNextProcess.alpha = 1f
                            sharedPrefsHelper.isFromStepInit = true
                        }else{
                            binding.btnNextProcess.alpha = 0.6f;
                        }
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    RetrofitClient.showResponseMessage(this@FoRegistrationDashBoardActivity, responseObject.code())

                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<FormStatus>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@FoRegistrationDashBoardActivity, t)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        if(CommonUtils.isFormEdit){
            CommonUtils.isFormEdit = false
        }
    }
}