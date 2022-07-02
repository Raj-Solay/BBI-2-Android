package com.bbi.bizbulls.remote

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import com.bbi.bizbulls.R
import com.bbi.bizbulls.databinding.DialogMessagesBinding
import com.bbi.bizbulls.sharedpref.SharedPrefsManager
import com.bbi.bizbulls.utils.Globals
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private var retrofit: Retrofit? = null
    private fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())/* Converter Factory to convert your Json to gson */
                .build()
        }
        return retrofit!!
    }

    fun getUrl(): APIService {
        return getClient().create(APIService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES).addInterceptor(interceptor)
            .build()
    }


    /**
     *  This function used to show the response messages from API
     *
     * @param context activity or fragment context
     */
    fun showResponseMessage(context: Context, responseCode: Int) {
        val dialog = Dialog(context)
        val binding: DialogMessagesBinding =
            DialogMessagesBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        // TODO WHen release the application change the cancelable true to false
        dialog.setCancelable(true)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        println("________Response Code  ::$responseCode")
        when (responseCode) {
            401 -> {
                binding.textMessage.text = context.resources.getString(R.string.unAuthenticated)
            }
            422 -> {
                binding.textMessage.text = context.resources.getString(R.string.inValidData)
            }
            else -> {
                binding.textMessage.text = context.resources.getString(R.string.something_wrong)
            }

        }

        binding.OkMessage.setOnClickListener {
            dialog.dismiss()
            if (responseCode == 401) {
                val sharedPrefsHelper by lazy { SharedPrefsManager(context) }
                sharedPrefsHelper.forceLogout(context)
            }
        }

        //now that the dialog is set up, it's time to show it
         dialog.show()
    }

    /**
     *  This function used to show the failed response messages from API
     *
     * @param context activity or fragment context
     */
    fun showFailedMessage(context: Context,  t: Throwable) {
        val dialog = Dialog(context)
        val binding: DialogMessagesBinding =
            DialogMessagesBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val errorMessage = when (t) {
            is IOException -> context.resources.getString(R.string.no_internet)
            is HttpException -> context.resources.getString(R.string.something_wrong)
            else -> t.localizedMessage
        }
        binding.textMessage.text = errorMessage

        binding.OkMessage.setOnClickListener {
            dialog.dismiss()
        }

        //now that the dialog is set up, it's time to show it
        dialog.show()
    }
}