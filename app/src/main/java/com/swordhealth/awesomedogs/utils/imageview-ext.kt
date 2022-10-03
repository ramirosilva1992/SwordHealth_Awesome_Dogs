package com.swordhealth.awesomedogs.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.swordhealth.awesomedogs.R

fun ImageView.loadImage(url: String?) {
    val options = RequestOptions()
        .placeholder(getProgressDrawable(context))
        .error(R.drawable.ic_warning)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

private fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}