package com.bbi.bizbulls.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.data.foregistration.steps.FoRegistrationSteps
import com.bbi.bizbulls.databinding.FragmentStatusBinding
import com.bbi.bizbulls.model.StatusData
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.MyProcessDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerFOStatusFragment : Fragment(), View.OnClickListener {
    private val sharedPrefsHelper by lazy { SharedPrefsManager(requireActivity()) }
    private lateinit var binding: FragmentStatusBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        binding.layoutkycincomplete.setOnClickListener(this)
        binding.btnregister.setOnClickListener(this)
        binding.btnaddlocationidentity.setOnClickListener(this)
        binding.btnaddsetup.setOnClickListener(this)
        binding.btnaddagreement.setOnClickListener(this)
        binding.btnaddlicense.setOnClickListener(this)
        binding.txtviewcontactdetails.setOnClickListener(this)
        binding.scrollfostatus.visibility = View.VISIBLE
        binding.layoutinfostatus.visibility = View.GONE
        return binding.root
    }

    override fun onClick(view: View) {
        if (view === binding.layoutkycincomplete) {
            binding.layoutkycincomplete.visibility = View.GONE
            binding.layoutkyccomplete.visibility = View.VISIBLE
            binding.laoutregisterincomplete.visibility = View.VISIBLE
            binding.imgkycstatus.setImageResource(R.drawable.ic_done)
        }
        if (view === binding.btnregister) {
//            Intent i = new Intent(getActivity(), AddPaymentOrderDetailActivity.class);
//            i.putExtra("foco","fo");
//            startActivityForResult(i,506);
            showfinancialRelibility()
        }
        if (view === binding.btnaddlocationidentity) {
            binding.layoutlocationidentityincomplete.visibility = View.GONE
            binding.layoutlocationidentitycomplete.visibility = View.VISIBLE
            binding.layoutagreementincomplete.visibility = View.VISIBLE
            binding.imglocationidentity.setImageResource(R.drawable.ic_done)
            //            Intent i = new Intent(getActivity(), LocateOfficeAddress.class);
//            i.putExtra("temp","fo");
//            startActivity(i);
        }
        if (view === binding.btnaddagreement) {
            binding.layoutagreementincomplete.visibility = View.GONE
            binding.layoutagreementcomplete.visibility = View.VISIBLE
            binding.layoutsetupincomplete.visibility = View.VISIBLE
            binding.imgagreementstatus.setImageResource(R.drawable.ic_done)
        }
        if (view === binding.btnaddsetup) {
            binding.layoutsetupincomplete.visibility = View.GONE
            binding.layoutsetupcomplete.visibility = View.VISIBLE
            binding.layoutlicenseincomplete.visibility = View.VISIBLE
            binding.imgsetupstatus.setImageResource(R.drawable.ic_done)
            //            Intent i = new Intent(getActivity(), SetupFinancialActivity.class);
//            startActivity(i);
        }
        if (view === binding.btnaddlicense) {
            binding.layoutlicenseincomplete.visibility = View.GONE
            binding.layoutlicensecomplete.visibility = View.VISIBLE
            binding.imglicensestatus.setImageResource(R.drawable.ic_done)
            binding.txtinfomsg.visibility = View.VISIBLE
            binding.txtviewcontactdetails.visibility = View.VISIBLE
        }
        if (view === binding.txtviewcontactdetails) {
            requireActivity().finish()
            sharedPrefsHelper.forceLogout(requireActivity())
        }
    }

    private fun showfinancialRelibility() {
        binding.laoutregisterincomplete.visibility = View.GONE
        binding.laoutregistercomplete.visibility = View.VISIBLE
        binding.layoutlocationidentityincomplete.visibility = View.VISIBLE
        binding.imgregisterstatus.setImageResource(R.drawable.ic_done)
    }
    /**
     * Get Fo registration steps
     */
    fun geSteps(context: Context) {
        val sharedPrefsHelper by lazy { SharedPrefsManager(context) }

        val call: Call<StatusData> =
            RetrofitClient.getUrl().getStatus(sharedPrefsHelper.authToken)
        println("________URL ::${call.request().url}")
        println("________authToken ::${sharedPrefsHelper.authToken}")
        MyProcessDialog.showProgressBar(context, 0)
        call.enqueue(object : Callback<StatusData> {
            override
            fun onResponse(
                call: Call<StatusData>,
                responseObject: Response<StatusData>
            ) {
                if (responseObject.isSuccessful) {
                   val data =  responseObject.body()

                } else {
                    RetrofitClient.showResponseMessage(context, responseObject.code())
                }
                MyProcessDialog.dismiss()
            }

            override
            fun onFailure(call: Call<StatusData>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(context, t)
            }
        })
    }
}