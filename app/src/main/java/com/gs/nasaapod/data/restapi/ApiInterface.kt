package com.gs.nasaapod.data.restapi

import com.gs.nasaapod.data.ApiEndPoints
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiInterface {

    @GET(ApiEndPoints.PLANETARY_APOD)
    suspend fun getPictures(@QueryMap params: HashMap<String, String>): FavouritePicturesEntity

    @GET(ApiEndPoints.PLANETARY_APOD)
    suspend fun getPicturesByStartEndDates(@QueryMap params: HashMap<String, String>): List<FavouritePicturesEntity>


}
