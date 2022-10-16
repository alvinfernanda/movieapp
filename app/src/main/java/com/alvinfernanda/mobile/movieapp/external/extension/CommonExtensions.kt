package com.alvinfernanda.mobile.movieapp.external.extension

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alvinfernanda.mobile.movieapp.BuildConfig
import com.alvinfernanda.mobile.movieapp.R
import com.alvinfernanda.mobile.movieapp.external.constant.AppConstant
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun debugMode(function: () -> Unit) {
    if(BuildConfig.DEBUG) {
        function()
    }
}

fun releaseMode(function: () -> Unit) {
    if(BuildConfig.BUILD_TYPE.equals("release", ignoreCase = true)) {
        function()
    }
}

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if(this != null) f(this)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun ImageView.loadImage(url: String?) {
    try {
        if (url != null && url.isNotEmpty()) {
            Glide.with(context)
                .asBitmap()
                .load(AppConstant.BASE_POSTER_PATH + url)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_default))
                .error(ContextCompat.getDrawable(context, R.drawable.ic_default))
                .into(this)
        }
    } catch (error: Exception) {
        error.printStackTrace()
    }
}

@SuppressLint("SimpleDateFormat")
fun convertToStringDate(format: String?): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = formatter.parse(format)
    val desiredFormat = SimpleDateFormat("dd MMM yyyy").format(date!!)
    return desiredFormat.toString()
}