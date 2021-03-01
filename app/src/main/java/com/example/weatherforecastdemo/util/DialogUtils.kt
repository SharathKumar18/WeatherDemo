package com.example.weatherforecastdemo.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * Created by  on 28/02/21.
 */
object DialogUtils {

    fun showAlertDialog(context: Context, title:String, message:String, callback : ()->Unit){
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    callback.invoke()
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}