package com.torres.demo.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import com.torres.demo.application.App
import java.io.File

object Utils {

    fun isConnect(context: Context): Boolean {
        var connected = false
        val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val redes = connec.allNetworkInfo
        for (i in redes.indices) {
            if (redes[i].state == NetworkInfo.State.CONNECTED) {
                connected = true
            }
        }
        return connected
    }

    fun createImageFile(remoteContentID: String): File? {
        val storageDir: File = App.appInstance!!.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES)!!
        if (storageDir != null && !storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                return null
            }
        }
        return File(storageDir.toString() + File.separator + remoteContentID)
    }

    fun s4(): String {
        val s4 = Math.floor(Math.random() * 0x10000).toInt()
        var pid = Integer.toHexString(s4)
        if (pid.length <= 3) {
            pid = s4()
        }
        return pid.toUpperCase()
    }

    fun GenerateUUID(): String? {
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4()
    }


}