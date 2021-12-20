package com.gs.nasaapod.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.gs.nasaapod.R



@BindingAdapter("setImage")
fun setImage(sdv: SimpleDraweeView, url: String?) {
    sdv.setImageURI(url)
}

@BindingAdapter("setImage")
fun setImage(aiv: AppCompatImageView, isFavourite: Boolean?) {
    if (isFavourite == true) {
        aiv.setImageResource(R.drawable.ic_heart_filled)
    } else {
        aiv.setImageResource(R.drawable.ic_heart_hollow)
    }
}

