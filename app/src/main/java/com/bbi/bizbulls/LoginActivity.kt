package com.bbi.bizbulls

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bbi.bizbulls.utils.MyProcessDialog
import com.bbi.bizbulls.data.signin.LoginResponse
import com.bbi.bizbulls.databinding.ActivityLoginBinding
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.CommonUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val sharedPrefsHelper by lazy { SharedPrefsManager(this@LoginActivity) }
    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun init() {
        binding!!.txtloginweblink.setOnClickListener(this)
        binding!!.ivShowpBtn.setOnClickListener(this)
        binding!!.submitlogin.setOnClickListener(this)
        binding!!.tvFgpassword.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view === binding!!.txtloginweblink) {
            val uriUrl = Uri.parse(this@LoginActivity.getString(R.string.bizBullsIndia_url))
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            launchBrowser.addCategory(Intent.CATEGORY_BROWSABLE)
            startActivity(launchBrowser)
        }
        if (view === binding!!.ivShowpBtn) {
            if (binding!!.etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding!!.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding!!.ivShowpBtn.setImageResource(R.drawable.ic_visibility)
            } else {
                binding!!.ivShowpBtn.setImageResource(R.drawable.ic_visibility_off)
                binding!!.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
        if (view === binding!!.submitlogin) {
            hideKeyboard(this)
            if (CommonUtils.isNetworkConnected(this@LoginActivity)) {
                if (CommonUtils.isValidPhone(binding!!.etUsername.text.toString().trim()) ||
                    CommonUtils.isValidEmail(binding!!.etUsername.text.toString().trim())
                ) {
                    if (binding!!.etPassword.text.toString().isNotEmpty()) {
                        login()
                    } else {
                        Toast.makeText(this, "Please type correct password!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please type correct user name as mobile number or email address!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                CommonUtils.toast(this, "Please check your network connection.")
            }
        }
        if (view === binding!!.tvFgpassword) {
            val fp = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(fp)
        }
    }

    private fun login() {
        MyProcessDialog.showProgressBar(this, 0)
        val jsonObject = JsonObject()
        jsonObject.addProperty("grant_type", "password")
        jsonObject.addProperty("client_id", "2")
        jsonObject.addProperty("client_secret", "Uc54V4JnJ7fw95UwZ9IWoRxQafMh0TZy2f5L83kI")
        jsonObject.addProperty("username", binding!!.etUsername.text.toString().trim { it <= ' ' })
        jsonObject.addProperty("password", binding!!.etPassword.text.toString().trim { it <= ' ' })
        val call: Call<LoginResponse> = RetrofitClient.getUrl().login( jsonObject)
            call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200) {
                    if (response.body()!!.expiresIn > 0) {
                        sharedPrefsHelper.isLogin = true
                        sharedPrefsHelper.authToken = response.body()?.tokenType + " " + response.body()?.accessToken
                        sharedPrefsHelper.userName = response.body()?.user?.data?.name.toString()
                        sharedPrefsHelper.phone = response.body()?.user?.data?.phone.toString()
                        sharedPrefsHelper.email = response.body()?.user?.data?.email.toString()
                        sharedPrefsHelper.userPicture = response.body()?.user?.data?.profilePic.toString()
                        sharedPrefsHelper.userId = response.body()?.user?.data?.id.toString()
                        MyProcessDialog.dismiss()
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                        val i = Intent(this@LoginActivity, DashboardActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(i)
                        CommonUtils.toast(this@LoginActivity, "Login Successfully!")
                    } else {
                        CommonUtils.toast(this@LoginActivity, this@LoginActivity.resources.getString(R.string.something_wrong))
                    }
                } else {
                    CommonUtils.toast(this@LoginActivity, this@LoginActivity.resources.getString(R.string.something_wrong))
                }
                MyProcessDialog.dismiss()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(this@LoginActivity, t)

            }
        })
    }
}