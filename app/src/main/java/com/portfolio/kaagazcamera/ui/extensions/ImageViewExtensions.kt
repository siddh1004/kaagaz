package com.portfolio.kaagazcamera.ui.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadUri(uri: String, placeholder: Drawable? = ColorDrawable(Color.GRAY)) {
    try {
        val options: RequestOptions = RequestOptions()
            .fitCenter()
            .placeholder(placeholder)
            .error(placeholder)

        Glide
            .with(context)
            .load(Uri.parse(uri))
            .apply(options)
            .into(this)
    } catch (ex: Exception) {
        setImageDrawable(placeholder)
    }
}