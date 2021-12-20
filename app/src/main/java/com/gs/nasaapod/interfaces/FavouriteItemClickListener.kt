package com.gs.nasaapod.interfaces

import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity


interface FavouriteItemClickListener {
    fun onFavouriteItemClicked(date : String?)
    fun onRemoveFavouriteClicked(model : FavouritePicturesEntity?)
}