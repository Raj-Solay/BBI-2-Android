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
import com.bbi.bizbulls.ui.registrationforfo.FoRegistrationDashBoardActivity


class ProjectInfoActivity : AppCompatActivity(), View.OnClickListener {
    var binding: ActivityProjectinfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectinfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.tvRegister.setOnClickListener(this)
        binding!!.backNavigation.setOnClickListener(this)
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
    lateinit var sharedPrefsManager: SharedPrefsManager
    private fun showAlert(){
        sharedPrefsManager= applicationInfo?.let { SharedPrefsManager(this) }!!
        AlertDialog.Builder(this)
          //  .setIcon(null)
            .setTitle("Dear "+ sharedPrefsManager.userName)
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