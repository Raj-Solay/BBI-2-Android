package com.bbi.bizbulls.ui.registrationforfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoActivityRegistrationBinding
import com.bbi.bizbulls.ui.registrationforfo.fragments.*
import com.bbi.bizbulls.utils.CommonUtils

class FranchiseeRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: FoActivityRegistrationBinding
    private lateinit var foRegistrationViewModel: FranchiseeRegistrationViewModel
    private var _selectedStepName = MutableLiveData<String>()
    private val selectedStepName: LiveData<String> get() = _selectedStepName
    private var stepName = ""
    private var stepStatus = ""
    private var stepPosition = 0
    private var actionType = CommonUtils.ACTION_TYPE_ADD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FoActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foRegistrationViewModel = ViewModelProvider(this)[FranchiseeRegistrationViewModel::class.java]
        init()

        selectedStepName.observe(this) { stepsName ->
            binding.foStepHeaderName.text = stepsName
        }

        binding.foBack.setOnClickListener {
            activityCalling()
        }

    }

    private fun init() {
        //Get the bundle
        val bundle = intent.extras
        bundle?.getString("name")?.also { stepName = it }
        bundle?.getString("status")?.also { stepStatus = it }
        bundle?.getInt("position")?.also { stepPosition = it }
        bundle?.getInt("actionType")?.also { actionType = it }

        when (stepPosition) {
            1 -> {
                fragmentCalling(FoHealthDetailsFragment(stepPosition,actionType))
            }
            2 -> {
                fragmentCalling(FoExpressionOfInterestFragment(stepPosition,actionType))
            }
            3 -> {
                fragmentCalling(FoCheckListFragment(stepPosition,actionType)) //Pending
            }
            4 -> {
                fragmentCalling(FoAcademicEducationFragment(stepPosition,actionType))
            }
            5 -> {
                fragmentCalling(FoSocialIdentityFragment(stepPosition,actionType)) //Pending
            }
            6 -> {
                fragmentCalling(FoBankAccountFragment(stepPosition,actionType))
            }
            7 -> {
                fragmentCalling(FoFamilyFragment(stepPosition,actionType))
            }
            8 -> {
                fragmentCalling(FoChildDetailsFragment(stepPosition,actionType))
            }
            9 -> {
                fragmentCalling(FoPersonalReferenceFragment(stepPosition,actionType))
            }
            10 -> {
                fragmentCalling(FoAttachmentsFragment(stepPosition,actionType))
            }
            11 -> {
                fragmentCalling(FoAuthorizationFragment(stepPosition,actionType))
            }
            else -> {
                fragmentCalling(FoPersonalDetailsFragment(stepPosition,actionType))
            }
        }

    }

    private fun fragmentCalling(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        _selectedStepName.value = stepName
    }

    private fun activityCalling() {
        val i = Intent(this, FoRegistrationDashBoardActivity::class.java)
        startActivity(i)
        finish()
    }

    companion object {
        /*
        * This function for navigation to FoRegistrationDashBoardActivity
        * */
        fun activityCalling(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            val i = Intent(context, FoRegistrationDashBoardActivity::class.java)
            context.startActivity(i)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            activityCalling()
            true
        } else super.onKeyDown(keyCode, event)
    }
}