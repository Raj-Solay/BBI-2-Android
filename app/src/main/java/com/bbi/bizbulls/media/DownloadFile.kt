package com.bbi.bizbulls.media

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bbi.bizbulls.remote.RetrofitClient
import com.bbi.bizbulls.utils.MyProcessDialog
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*


object DownloadFile {
  val TAG ="DownloadFile"
     fun download(url: String?, ctx:Context) {
        MyProcessDialog.showProgressBar(ctx, 0)
        val call: Call<ResponseBody> = RetrofitClient.getUrl().downloadFileWithDynamicUrlSync(url)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                MyProcessDialog.dismiss()
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file")
                    val writtenToDisk: Boolean = writeResponseBodyToDisk(response.body(),ctx)
                    Log.d(TAG, "file download was a success? $writtenToDisk")
                } else {
                    Log.d(TAG, "server contact failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MyProcessDialog.dismiss()
                RetrofitClient.showFailedMessage(ctx, t)
            }
        })
    }
    private fun writeResponseBodyToDisk(body: ResponseBody?, ctx: Context): Boolean {
        return try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(ctx.getExternalFilesDir(null),File.separator.toString()+Date() + "_Bizzbull.png")
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                val fileSize = body?.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body?.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read: Int? = inputStream?.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    read?.let { outputStream?.write(fileReader, 0, it) }
                    fileSizeDownloaded += read?.toLong()!!
                    Log.d(TAG, "file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream?.flush()

                try {
                    Toast.makeText(ctx,"File downloaded at: ${futureStudioIconFile.absolutePath}",Toast.LENGTH_SHORT).show()
                    val map = MimeTypeMap.getSingleton()
                    val ext = MimeTypeMap.getFileExtensionFromUrl(futureStudioIconFile.name)
                    var type = map.getMimeTypeFromExtension(ext)

                    if (type == null) type = "*/*"
                    val intent = Intent(Intent.ACTION_VIEW)

                    val data = FileProvider.getUriForFile(
                        ctx,
                        ctx.packageName + ".fileProvider",
                        futureStudioIconFile
                    )

                    intent.setDataAndType(data, type)
                    ctx.startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }
                true
            } catch (e: IOException) {
                false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            false
        }
    }
}