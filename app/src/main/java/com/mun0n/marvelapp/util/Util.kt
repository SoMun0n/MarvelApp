package com.mun0n.marvelapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mun0n.marvelapp.R
import com.squareup.picasso.Picasso
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

@BindingAdapter("url", "size")
fun ImageView.bindImageUrl(url: String?, size: String) {
    if (url != null && url.isNotBlank()) {
        val finalUrl = url.replace("http", "https")
        Picasso.get()
            .load("$finalUrl/$size")
            .placeholder(R.mipmap.placeholder)
            .into(this)
    }
}

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}