package com.gs.nasaapod.ui.main

import androidx.lifecycle.LiveData
import com.gs.nasaapod.base.BaseRepository
import com.gs.nasaapod.data.ApiRequestCodes
import com.gs.nasaapod.data.DataManager
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.data.restapi.ResultWrapper


class MainRepository : BaseRepository() {

    init {
        System.loadLibrary("native-lib")
    }


    /**
     * method to get api key
     */
    private external fun getAPIKey(): String


    /**
     * method to get picture for today from api
     */
    suspend fun getPicturesForToday(): ResultWrapper<FavouritePicturesEntity?> {
        return safeApiCall(ApiRequestCodes.RC_PICTURE_FOR_TODAY) {
            val params = HashMap<String, String>()
            params["api_key"] = getAPIKey()

            DataManager.getPictures(params)
        }
    }


    /**
     * method to get picture for single selected day from api
     */
    suspend fun getPictureByDate(date: String): ResultWrapper<FavouritePicturesEntity?> {
        return safeApiCall(ApiRequestCodes.RC_PICTURE_BY_DAY) {
            val params = HashMap<String, String>()
            params["api_key"] = getAPIKey()
            params["date"] = date

            DataManager.getPictures(params)
        }
    }



    /**
     * method to save favourites in db
     */
    suspend fun addToFavourites(entity: FavouritePicturesEntity) {
        DataManager.addToFavourites(entity)
    }


    /**
     * method to remove favourites from db
     */
    suspend fun removeFromFavourites(compositeId: String) {
        DataManager.removeFromFavourites(compositeId)
    }

    /**
     * method to check if entity exists in our table
     */
    suspend fun checkIfExists(compositeId: String): Int {
        return DataManager.checkIfExists(compositeId)
    }

    /**
     * method to get saved favourites list from db
     */
     fun getFavouritesList(): LiveData<List<FavouritePicturesEntity>> {
        return DataManager.getFavourites()
    }

}