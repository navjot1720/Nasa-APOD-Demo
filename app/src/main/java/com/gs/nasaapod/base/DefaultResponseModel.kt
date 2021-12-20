package com.gs.nasaapod.base

/**
 * default model for api response to handle different states
 */
data class DefaultResponseModel<T>(
    var apiRequestCode: Int = 0,
    var msg: String? = null,
    var code: Int = 0
)
