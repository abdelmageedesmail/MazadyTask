package com.abdelmageed.mazadytask.extension

import android.view.View
import android.widget.ImageView
import coil.api.load

fun ImageView.loadImage(url: String) {
    this.load("https://image.tmdb.org/t/p/w500$url")
}

fun View.visibility(isVisible: Boolean) {
    if (isVisible)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}