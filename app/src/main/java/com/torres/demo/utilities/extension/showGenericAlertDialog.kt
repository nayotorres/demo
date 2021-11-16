package com.torres.demo.utilities.extension

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.torres.demo.R

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.aceptar)){ dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}

fun Context.showGenericAlertDialog(message: String,answer:ResponseAlertDialog){
    AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.aceptar)){ dialog, _ ->
            answer.clickOpcion(true)
            dialog.dismiss()
        }
    }.show()
}

fun Context.showGenericAlertDialogResult(message: String,answer:ResponseAlertDialog){
    AlertDialog.Builder(this,R.style.AlertDialogTheme).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.aceptar)){ dialog, _ ->
            answer.clickOpcion(true)
            dialog.dismiss()
        }
        setNegativeButton(getString(R.string.cancelar)){dialog,_->
            answer.clickOpcion(false)
            dialog.dismiss()
        }
    }.show()
}


interface ResponseAlertDialog{
    fun clickOpcion(answer:Boolean)
}