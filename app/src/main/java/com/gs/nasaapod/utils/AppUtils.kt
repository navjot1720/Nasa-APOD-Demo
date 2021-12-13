package com.gs.nasaapod.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import com.gs.nasaapod.BuildConfig
import com.gs.nasaapod.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * created by Navjot Singh
 * on 02/02/2020
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


    /**
     * method to share application
     */
    fun openAppInGooglePlayStore(context: Context?) {
        if (context != null) {
            val appId: String = context.packageName
            val uri: Uri = Uri.parse("market://details?id=$appId")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            try {
                context.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$appId")
                    )
                )
            }
        }
    }


    /**
     * method to share application
     */
    fun shareApplication(
        context: Context?,
        subject: String,
        text: String
    ) {
        if (context != null) {
            val appName = context.getString(R.string.app_name)
            val appId = BuildConfig.APPLICATION_ID

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            //shareIntent.putExtra(Intent.EXTRA_TEXT, ("$text\n$appName:\nhttp://play.google.com/store/apps/details?id=$appId"))
            shareIntent.putExtra(
                android.content.Intent.EXTRA_TEXT,
                "$text\n$appName:\nhttp://play.google.com/store/apps/details?id=$appId"
            )
            context.startActivity(Intent.createChooser(shareIntent, "Share via:"))
        }
    }


    /**
     * method to share text with Intent chooser
     */
    fun shareTextWithIntentChooser(
        context: Context?,
        subject: String,
        text: String
    ) {
        if (context != null) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            val intentChooser = Intent.createChooser(shareIntent, "Share via:")
            context.startActivity(intentChooser)
        }
    }


    /**
     * method to create & show Native AlertDialog
     */
    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: String,
        negativeText: String,
        positiveClickListener: () -> Unit,
        negativeClickListener: () -> Unit
    ): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(positiveText) { dialog, position ->
            dialog.dismiss()
            positiveClickListener()
        }

        builder.setNegativeButton(negativeText) { dialog, position ->
            dialog.dismiss()
            negativeClickListener()
        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertDialog.window?.setBackgroundDrawable(null)
        alertDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )

        alertDialog.show()
        return alertDialog
    }


}
