package com.bbi.bizbulls.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.BuildConfig
import com.bbi.bizbulls.databinding.DialogMessagesBinding
import com.bbi.bizbulls.enums.Environment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object CommonUtils {

    /** Globally accessible build environment */
    val environment = when (BuildConfig.BUILD_TYPE) {
        "release" -> Environment.PRODUCTION
        "qa" -> Environment.QA
        else -> Environment.DEVELOPMENT
    }

    const val  ACTION_TYPE_ADD = 0
    const val  ACTION_TYPE_VIEW = 1
    const val  ACTION_TYPE_EDIT = 2

    fun toast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    fun showServiceDialog(context: Context){
        AlertDialog.Builder(context)
            //  .setIcon(null)
            //.setTitle("Dear "+ sharedPrefsManager.userName)
            .setMessage("This service is not available in your location presently. Our executive will approach you shortly.")
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->


                })

            .show()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && target?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } == true
    }

    fun isValidPhone(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && target?.let { Patterns.PHONE.matcher(it).matches() } == true
    }
    @SuppressLint("SimpleDateFormat")
    fun commonDatePickerDialog(context: Context, datePickerListener: DatePickerListener) {
        //Get yesterday's date
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(context,
            { datePicker: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                var date: String? =
                    selectedDay.toString() + "/" + (selectedMonth + 1) + "/" + selectedYear
                try {
                    date = SimpleDateFormat("d/M/yyyy").parse(date)?.let {
                        SimpleDateFormat("yyyy-MM-dd").format(
                            it
                        )
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                datePickerListener.setDate(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    interface DatePickerListener {
        fun setDate(dateStr: String?)
    }

    /**
     * It is used for check the device's internet connectivity
     */
    fun isNetworkConnected(context: Context): Boolean {
        val result: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork ?: return false
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    /**
     *  This function used to get the device IP
     */
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     *  This function used to show waiting spinner
     *
     * @param context activity or fragment context
     */
    fun showError(context: Context, msg: String) {
        val dialog = Dialog(context)
        val binding: DialogMessagesBinding =
            DialogMessagesBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.textMessage.text = msg
        binding.OkMessage.setOnClickListener {
            dialog.dismiss()
        }

        //now that the dialog is set up, it's time to show it
        dialog.show()
    }

    //private method of your class
    fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    fun getUserIdFromToken() : String{
        return ""
    }

}