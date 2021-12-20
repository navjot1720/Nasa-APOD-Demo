package com.gs.nasaapod.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.gs.nasaapod.R
import com.gs.nasaapod.data.MediaType
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity


@BindingAdapter("setImage")
fun setImage(sdv: SimpleDraweeView, model: FavouritePicturesEntity?) {
    model?.let {
        if (it.media_type.equals(MediaType.VIDEO.value, true))
        {
            sdv.setImageURI(it.thumbnail_url)
        } else{
            sdv.setImageURI(it.url)
        }
    }
}

@BindingAdapter("setImage")
fun setImage(aiv: AppCompatImageView, isFavourite: Boolean?) {
    if (isFavourite == true) {
        aiv.setImageResource(R.drawable.ic_heart_filled)
    } else {
        aiv.setImageResource(R.drawable.ic_heart_hollow)
    }
}

