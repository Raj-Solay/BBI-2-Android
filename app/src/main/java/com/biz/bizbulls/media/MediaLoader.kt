package com.biz.bizbulls.media

import android.widget.ImageView
import com.biz.bizbulls.R

import com.bumptech.glide.Glide

import com.yanzhenjie.album.AlbumFile

import com.yanzhenjie.album.AlbumLoader


class MediaLoader : AlbumLoader {
    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String?) {
        Glide.with(imageView.getContext())
            .load(url)
            .error(R.drawable.upload)
            .placeholder(R.drawable.upload)
            .into(imageView)
    }
}