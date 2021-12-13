package com.gs.nasaapod.data


/**
 * All app related constants are defined in this file
 */
object AppConstants {


}

object ApiEndPoints {
    const val PLANETARY_APOD = "planetary/apod"

}


object ApiRequestCodes {
    const val RC_PICTURE_FOR_TODAY = 0
    const val RC_PICTURE_BY_DAY = 1
    const val RC_PICTURES_FOR_DATES = 2

}


object ApiStatusCodes {
    const val SUCCESS = 200
    const val NOT_FOUND = 404
    const val ERROR = 504
}


enum class NetworkStatusMessage(val message: String) {
    NO_INTERNET("Please check your internet connection!"),
    NETWORK_ERROR("Something went wrong! Please try again."),
    CONNECTION_ERROR("Unable to connect to server. Please try again later.")
}

