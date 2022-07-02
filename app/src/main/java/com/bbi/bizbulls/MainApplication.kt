package com.bbi.bizbulls

import android.app.Application
import android.content.Context
import com.bbi.bizbulls.media.MediaLoader
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig


/**
 * Created by Daniel.
 */
class MainApplication : Application() {

    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        Album.initialize(
            AlbumConfig.newBuilder(this)
            .setAlbumLoader(MediaLoader())
            .build())
    }

    fun getAppContext(): Context? {
        return context
    }
}