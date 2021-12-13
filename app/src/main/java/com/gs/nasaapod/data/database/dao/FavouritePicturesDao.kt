package com.gs.nasaapod.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity


@Dao
interface FavouritePicturesDao {

    @Query("SELECT * FROM favourites")
     fun getCartData(): LiveData<List<FavouritePicturesEntity>>


    @Query("SELECT * FROM favourites WHERE compositeId =:id")
    suspend fun getPictureByCompositeId(id: String): FavouritePicturesEntity?


    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE compositeId =:id)")
    suspend fun checkIfExists(id: String): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(entity: FavouritePicturesEntity)


    @Query("DELETE FROM favourites WHERE compositeId =:id")
    suspend fun removeFromFavourites(id: String)

}