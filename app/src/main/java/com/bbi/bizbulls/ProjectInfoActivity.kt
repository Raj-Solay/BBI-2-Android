package com.bbi.bizbulls

import android.R
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bbi.bizbulls.databinding.ActivityProjectinfoBinding
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.ui.fragment.CustomerFOStatusFragment
import com.bbi.bizbulls.ui.registrationforfo.FoRegistrationDashBoardActivity
import com.bbi.bizbulls.utils.CommonUtils

class ProjectInfoActivity : AppCompatActivity(), View.OnClickListener {
    var binding: ActivityProjectinfoBinding? = null
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@ProjectInfoActivity) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectinfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.tvRegister.setOnClickListener(this)
        binding!!.backNavigation.setOnClickListener(this)

        if(sharedPrefsHelper.isFormCompleted){
            binding!!.tvRegister.text = "Status"
        }else{
            binding!!.tvRegister.text = "Register"
        }
        binding!!.tvRegister.setOnClickListener {
            if(sharedPrefsHelper.isFormCompleted){
                CommonUtils.isRedirectToStatus = true
                val i = Intent(this, DashboardActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                finish()
            }else{
               showAlert()
            }

        }
    }

    override fun onClick(view: View) {
        if (view === binding!!.backNavigation) {
            finish()
            val i = Intent(this@ProjectInfoActivity, DashboardActivity::class.java)
            startActivity(i)
        } else if (view === binding!!.tvRegister) {
            showAlert()
        }

    }
    private fun showAlert(){

        AlertDialog.Builder(this)
          //  .setIcon(null)
            .setTitle("Dear "+ sharedPrefsHelper.userName)
            .setMessage("To proceed with your registration process, you must update your profile first.")
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    val i = Intent(this@ProjectInfoActivity, FoRegistrationDashBoardActivity::class.java)
                    startActivity(i)
                    finish()

                })
            .setNegativeButton(R.string.no, null)
           // .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }
}