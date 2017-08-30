package com.natthawut.tamboon.utils

import android.databinding.BindingAdapter
import android.widget.ImageView

@BindingAdapter("imageUrl")
fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
    GlideApp.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
}
