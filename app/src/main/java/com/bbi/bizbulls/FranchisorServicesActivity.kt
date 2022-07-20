package com.bbi.bizbulls

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbi.bizbulls.databinding.ActivityFranchisorServicesBinding

class FranchisorServicesActivity : AppCompatActivity() {
   lateinit var binding:ActivityFranchisorServicesBinding
    var model_id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFranchisorServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                model_id = getInt("model_id")            }
        }
        initView()
    }
    private fun initView() {
        binding?.customTitleLayout?.tvTitle?.setText("Franchisor Services")
        binding?.customTitleLayout?.ivBack?.setOnClickListener { onBackPressed() }
        if (model_id==0) {
            binding.title.text = this.getResources().getString(R.string.Branding_Solutions)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Branding_Solutions_text)
        }
        if (model_id==1) {
            binding.title.text = this.getResources().getString(R.string.Incompetence)
            binding.tvTitleText.text = this.getResources().getString(R.string.Incompetence_text)
        }
        if (model_id==2) {
            binding.title.text = this.getResources().getString(R.string.Speedy_wide_coverage)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Speedy_wide_coverage_text)
        }
        if (model_id==3) {
            binding.title.text =
                this.getResources().getString(R.string.Reliable_Hiring_of_Franchisee)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Reliable_Hiring_of_Franchisee_text)
        }
        if (model_id==4) {
            binding.title.text = this.getResources().getString(R.string.Licensing_Documentation)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Licensing_Documentation_text)
        }
        if (model_id==5) {
            binding.title.text = this.getResources().getString(R.string.Set_up_Positioning)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Set_up_Positioning_text)
        }
        if (model_id==6) {
            binding.title.text = this.getResources().getString(R.string.Vendor_Management)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Vendor_Management_text)
        }
        if (model_id==7) {
            binding.title.text = this.getResources().getString(R.string.Business_Networking)
            binding.tvTitleText.text =
                this.getResources().getString(R.string.Business_Networking_text)
        }
     }
}