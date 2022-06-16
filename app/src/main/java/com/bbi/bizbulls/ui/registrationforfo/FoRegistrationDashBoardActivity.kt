package com.bbi.bizbulls.ui.registrationforfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bbi.bizbulls.DashboardActivity
import com.bbi.bizbulls.R
import com.bbi.bizbulls.data.foregistration.steps.Data
import com.bbi.bizbulls.databinding.FoActivityDashboardBinding
import com.bbi.bizbulls.ui.registrationforfo.adapters.FoRegistrationDashboardAdapter
import com.bbi.bizbulls.ui.registrationforfo.interfaces.IFoRegistrationStepsClickListener
import com.bbi.bizbulls.utils.CommonUtils

class FoRegistrationDashBoardActivity : AppCompatActivity(), IFoRegistrationStepsClickListener {
    private lateinit var binding: FoActivityDashboardBinding
    private lateinit var foRegistrationViewModel: FranchiseeRegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FoActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foRegistrationViewModel = ViewModelProvider(this)[FranchiseeRegistrationViewModel::class.java]

        if (CommonUtils.isNetworkConnected(this)) {
            // Call remote Api service for FO registration steps
            foRegistrationViewModel.getFoRegistrationSteps(this)
        } else {
            CommonUtils.toast(this, this.resources.getString(R.string.no_internet))
        }

        foRegistrationViewModel.allSteps.observe(this) { steps ->
            setUI(steps)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUI(steps: List<Data>) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = FoRegistrationDashboardAdapter(this, steps, this)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onStepsClickListener(name: String, status: String, position: Int) {
        //Create the bundle
        val bundle = Bundle()
        val i = Intent(this, FranchiseeRegistrationActivity::class.java)
        //Add your data to bundle
        bundle.putString("name", name)
        bundle.putString("status", status)
        bundle.putInt("position", position)
        //Add the bundle to the intent
        i.putExtras(bundle)
        startActivity(i)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val i = Intent(this, DashboardActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

}