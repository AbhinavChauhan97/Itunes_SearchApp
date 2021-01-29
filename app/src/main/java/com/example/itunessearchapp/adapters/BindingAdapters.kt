package com.example.itunessearchapp.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.itunessearchapp.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setSrc(imageView: ImageView, imageUrl: String?) {
        if (imageUrl == null || imageUrl.isEmpty() || imageUrl.isBlank()) {
            imageView.load(R.drawable.ic_baseline_image_24){
                placeholder(R.drawable.ic_baseline_image_24)
            }
            return
        }
        imageView.load(imageUrl)
    }
}