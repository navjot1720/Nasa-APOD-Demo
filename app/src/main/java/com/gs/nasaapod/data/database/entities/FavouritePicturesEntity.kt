package com.gs.nasaapod.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "favourites", indices = [Index(value = ["compositeId"], unique = true)]
)
class FavouritePicturesEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var compositeId: String? = null
    var title: String? = null
    var url: String? = null
    var copyright: String? = null
    var date: String? = null
    var explanation: String? = null
    var hdurl: String? = null
    var media_type: String? = null
    var service_version: String? = null
    var isFavourite: Boolean = false


    fun createCompositeId() : String{
        return ("$date#$title").toString()
    }

}