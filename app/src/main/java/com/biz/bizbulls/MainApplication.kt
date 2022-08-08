package com.biz.bizbulls

import android.app.Application
import android.content.Context
import com.biz.bizbulls.media.MediaLoader
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
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

        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        Album.initialize(
            AlbumConfig.newBuilder(this)
            .setAlbumLoader(MediaLoader())
            .build())
    }

    fun getAppContext(): Context? {
        return context
    }
}