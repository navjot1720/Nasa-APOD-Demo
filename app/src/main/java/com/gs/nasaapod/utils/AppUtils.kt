package com.gs.nasaapod.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * This class contains the helper methods to be used in the application
 */
object AppUtils {

    const val DF_dd_MMMM_yyyy = "dd MMMM, yyyy"
    const val DF_yyyy_MM_dd = "yyyy-MM-dd"


    fun parseDateTimeFormat(
        input: String?,
        inputFormat: String = DF_yyyy_MM_dd,
        outputFormat: String = DF_dd_MMMM_yyyy
    ): String {
        if (input != null) {
            try {
                val inputDateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
                val outputDateFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
                val date = inputDateFormat.parse(input)
                return if (date != null)
                    outputDateFormat.format(date)
                else
                    ""
            } catch (e: Exception) {
                return input
            }
        } else {
            return ""
        }
    }


}
