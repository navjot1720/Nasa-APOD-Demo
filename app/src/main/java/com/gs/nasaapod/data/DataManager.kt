package com.gs.nasaapod.data

import androidx.lifecycle.LiveData
import com.gs.nasaapod.BaseApplication
import com.gs.nasaapod.data.database.AppDatabase
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.data.restapi.ApiInterface
import com.gs.nasaapod.data.restapi.ApiManager


/**
 * Handle all data related task here.
 */

object DataManager {

    private var apiService: ApiInterface
    private var appDatabase: AppDatabase

    init {
        apiService = ApiManager.retrofitService
        appDatabase = AppDatabase.getInstance(BaseApplication.instance)
    }


    /**
     * ========================
     *  Database Query Methods
     * ========================
     */

     fun getFavourites(): LiveData<List<FavouritePicturesEntity>> {
        return appDatabase.favouritePicturesDao().getCartData()
    }

    suspend fun addToFavourites(entity: FavouritePicturesEntity) {
        appDatabase.favouritePicturesDao().addToFavourites(entity)
    }

    suspend fun removeFromFavourites(compositeId: String) {
        appDatabase.favouritePicturesDao().removeFromFavourites(compositeId)
    }

    suspend fun checkIfExists(compositeId: String) : Int{
        return appDatabase.favouritePicturesDao().checkIfExists(compositeId)
    }


    /**
     * ==================
     *  API Call Methods
     * ==================
     */

    suspend fun getPictures(params: HashMap<String, String>) = apiService.getPictures(params)

    suspend fun getPicturesByStartEndDates(params: HashMap<String, String>) = apiService.getPicturesByStartEndDates(params)


}
