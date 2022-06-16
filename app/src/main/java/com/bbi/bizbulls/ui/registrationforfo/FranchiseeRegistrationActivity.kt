package com.bbi.bizbulls.ui.registrationforfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.FoActivityRegistrationBinding
import com.bbi.bizbulls.ui.registrationforfo.fragments.*

class FranchiseeRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: FoActivityRegistrationBinding
    private var stepName = ""
    private var stepStatus = ""
    private var stepPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FoActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

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

        when (stepPosition) {
            1 -> {
                fragmentCalling(FoHealthDetailsFragment())
            }
            2 -> {
                fragmentCalling(FoExpressionOfInterestFragment())
            }
            3 -> {
                fragmentCalling(FoCheckListFragment())
            }
            4 -> {
                fragmentCalling(FoAcademicEducationFragment())
            }
            5 -> {
                fragmentCalling(FoSocialIdentityFragment())
            }
            6 -> {
                fragmentCalling(FoBankAccountFragment())
            }
            7 -> {
                fragmentCalling(FoFamilyFragment())
            }
            8 -> {
                fragmentCalling(FoChildDetailsFragment())
            }
            9 -> {
                fragmentCalling(FoPersonalReferenceFragment())
            }
            10 -> {
                fragmentCalling(FoAttachmentsFragment())
            }
            11 -> {
                fragmentCalling(FoAuthorizationFragment())
            }
            else -> {
                fragmentCalling(FoPersonalDetailsFragment())
            }
        }

    }

    private fun fragmentCalling(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
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