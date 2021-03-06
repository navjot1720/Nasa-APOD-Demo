package com.gs.nasaapod.data


/**
 * All app related constants are defined in this file
 */
object AppConstants {
    const val KEY_DATE = "DATE"
    const val KEY_VIDEO_URL = "VIDEO_URL"

}

enum class TabNames(val value: String){
    ASTRONOMY("Astronomy"),
    FAVOURITES("Favourites")
}

enum class MediaType(val value: String){
    VIDEO("video"),
    IMAGE("image")
}

object ApiEndPoints {
    const val PLANETARY_APOD = "planetary/apod"
}


object ApiRequestCodes {
    const val RC_PICTURE_FOR_TODAY = 0
    const val RC_PICTURE_BY_DAY = 1

}

object ApiStatusCodes {
    const val SUCCESS = 200
    const val NOT_FOUND = 404
    const val TIMEOUT_ERROR = 504
    const val CONNECTION_ERROR = 502
}

