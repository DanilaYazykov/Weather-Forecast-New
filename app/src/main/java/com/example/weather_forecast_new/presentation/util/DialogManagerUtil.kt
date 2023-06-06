package com.example.weather_forecast_new.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import com.example.weather_forecast_new.R

class DialogManagerUtil {

    fun locationSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.enable_location))
        dialog.setMessage(context.getString(R.string.location_disabled))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _, _ ->
            listener.onClick("null")
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.city))

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _, _ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun internetSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.NoInternetConnection))
        dialog.setMessage(context.getString(R.string.Connect_to_internet))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _, _ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.getString(R.string.cancel)
        ) { _, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }


    interface Listener {
        fun onClick(name: String?)
    }
}