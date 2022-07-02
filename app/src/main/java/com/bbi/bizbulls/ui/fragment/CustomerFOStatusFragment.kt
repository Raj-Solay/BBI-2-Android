package com.bbi.bizbulls.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.AgreementsStatusActivity
import com.bbi.bizbulls.R
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
    val isCustomer = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        binding.layoutkycincomplete.setOnClickListener(this)
        binding.btnaddlocationidentity.setOnClickListener(this)
        binding.btnaddsetup.setOnClickListener(this)
        binding.btnaddagreement.setOnClickListener(this)
        binding.btnaddlicense.setOnClickListener(this)
        binding.txtviewcontactdetails.setOnClickListener(this)
        binding.scrollfostatus.visibility = View.VISIBLE
        binding.layoutinfostatus.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geSteps(requireActivity())
    }

    override fun onClick(view: View) {
        if (view === binding.layoutkycincomplete) {
            setKycComplete()
        }
        if (view === binding.btnregister) {
//            Intent i = new Intent(getActivity(), AddPaymentOrderDetailActivity.class);
//            i.putExtra("foco","fo");
//            startActivityForResult(i,506);
            showfinancialRelibility()
        }
        if (view === binding.btnaddlocationidentity) {
           setLocationDone()
//                        Intent i = new Intent(getActivity(), LocateOfficeAddress.class);
//            i.putExtra("temp","fo");
//            startActivity(i);
        }
        if (view === binding.btnaddagreement) {
            setAgreementDone()
        }
        if (view === binding.btnaddsetup) {
           setOfficeSetupDone()
            //            Intent i = new Intent(getActivity(), SetupFinancialActivity.class);
//            startActivity(i);
        }
        if (view === binding.btnaddlicense) {
           setLicenceDone()
        }
        if (view === binding.txtviewcontactdetails) {
            requireActivity().finish()
            sharedPrefsHelper.forceLogout(requireActivity())
        }
    }

    private fun setLicenceDone() {
        binding.layoutlicenseincomplete.visibility = View.GONE
        binding.layoutlicensecomplete.visibility = View.VISIBLE
        binding.imglicensestatus.setImageResource(R.drawable.ic_done)
        binding.txtinfomsg.visibility = View.VISIBLE
        binding.txtviewcontactdetails.visibility = View.VISIBLE
    }

    private fun setOfficeSetupDone() {
        binding.layoutsetupincomplete.visibility = View.GONE
        binding.layoutsetupcomplete.visibility = View.VISIBLE
        binding.layoutlicenseincomplete.visibility = View.VISIBLE
        binding.imgsetupstatus.setImageResource(R.drawable.ic_done)
    }

    private fun setAgreementDone() {
        val i =  Intent(getActivity(), AgreementsStatusActivity::class.java);
        startActivity(i);

        binding.layoutagreementincomplete.visibility = View.GONE
        binding.layoutagreementcomplete.visibility = View.VISIBLE
        binding.layoutsetupincomplete.visibility = View.VISIBLE
        binding.imgagreementstatus.setImageResource(R.drawable.ic_done)
    }

    private fun setLocationDone() {
        binding.layoutlocationidentityincomplete.visibility = View.GONE
        binding.layoutlocationidentitycomplete.visibility = View.VISIBLE
        binding.layoutagreementincomplete.visibility = View.VISIBLE
        binding.imglocationidentity.setImageResource(R.drawable.ic_done)
    }

    private fun setKycComplete() {
        binding.layoutkycincomplete.visibility = View.GONE
        binding.layoutkyccomplete.visibility = View.VISIBLE
        binding.laoutregisterincomplete.visibility = View.VISIBLE
        binding.imgkycstatus.setImageResource(R.drawable.ic_done)
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
                    binding.apply {
                        txtcustomerid.text = data?.number
                        txtCustomerstatus.text = data?.customerStatus
                        txtcustomername.text = data?.customerName
                        txtcustomeraddress.text = data?.businessName
                        txtcustomertime.text = data?.date
                    }
                    if(isCustomer){
                        binding.apply {
                            bbiLayout.bbiLayout.visibility= View.VISIBLE
                            finabilityLayout.finabilityLayout.visibility= View.VISIBLE
                            siteVisitLayout.finabilityLayout.visibility= View.VISIBLE
                            franchiseeFeeLayout.franchiseeFeeLayout.visibility= View.VISIBLE
                        }
                    }

                    if(data?.kyc?.status.equals("Completed",true))
                    {
                        setKycComplete()
                    }
                    if(data?.registrationFees?.status.equals("Completed",true))
                    {
                        showfinancialRelibility()
                    }
                    if(data?.locationUpdate?.status.equals("Completed",true))
                    {
                        setLocationDone()
                    }
                    if(data?.agreement?.status.equals("Completed",true))
                    {
                        setAgreementDone()
                    }
                    if(data?.setup?.status.equals("Completed",true))
                    {
                        setOfficeSetupDone()
                    }
                    if(data?.licence?.status.equals("Completed",true))
                    {
                        setLicenceDone()
                    }
                    binding.btnregister.setOnClickListener {
                        data?.registrationFees?.let { it1 ->


                        }
                    }

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