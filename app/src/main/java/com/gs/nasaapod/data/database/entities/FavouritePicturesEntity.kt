package com.gs.nasaapod.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gs.nasaapod.utils.AppUtils


@Entity(
    tableName = "favourites", indices = [Index(value = ["date"], unique = true)]
)
class FavouritePicturesEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var date: String? = null
    var title: String? = null
    var explanation: String? = null
    var url: String? = null
    var hdurl: String? = null
    var thumbnail_url: String? = null
    var media_type: String? = null
    var copyright: String? = null
    var service_version: String? = null
    var isFavourite: Boolean = false


    fun getParsedDate(): String {
        return AppUtils.parseDateTimeFormat(date)
    }

}