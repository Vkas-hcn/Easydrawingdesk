package com.se.dr.bo.seconddrawingboard.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.se.dr.bo.seconddrawingboard.ui.start.StartActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object CloakFun {
    const val url_board = "https://payroll.drawingapp.net/bicep/dialup/chastise"

    /**
     * cloak
     */
    fun cloakJson(context: Context): Map<String, String> {
        return mapOf(
            "coffee" to BoardData.uuid_data, // distinct_id
            "pansy" to System.currentTimeMillis().toString(), // client_ts
            "campion" to getDeviceModel().toString(),//device_model
            "plague" to "com.drawingapp.easy",// bundle_id
            "text" to getOSVersion().toString(), // os_version
            "brim" to "", // gaid
            "towel" to (getAndroidID(context) ?: "").toString(), // android_id
            "quirk" to "cushing", // os
            "sled" to getAppVersionCode(context).toString(), // app_version
            "loon" to BoardData.uuid_data, // key
        )
    }

    fun getDeviceModel(): String {
        return Build.MODEL
    }

    fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }

    fun getAndroidID(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getAppVersionCode(context: Context): Int {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toInt()
        } else {
            packageInfo.versionCode
        }
    }

    @Throws(IOException::class)
    fun getParams2(urlString: String, params: Map<String, String>): String? {
        Log.e("TAG", "参数: ${params["towel"]}")
        val url = URL(urlString + "?" + encodeParams(params))
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            urlConnection.requestMethod = "GET"
            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader(InputStreamReader(urlConnection.inputStream)).use { reader ->
                    return reader.lineSequence().joinToString(separator = "\n")
                }
            } else {
                return null
                throw IOException("Server returned non-OK status: ${urlConnection.responseCode}")
            }
        } catch (e: Exception) {
            Log.e("TAG", "error: ${e}")
            return null
        } finally {
            urlConnection.disconnect()
        }
    }

    fun encodeParams(params: Map<String, String>): String {
        return params.map { (key, value) ->
            URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8")
        }.joinToString("&")
    }

    //发送请求
    fun sendRequest(activity: StartActivity) {
        if (BoardData.black_data.isNotEmpty()) {
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            val request = getParams2(CloakFun.url_board, cloakJson(activity))
            if (request.isNullOrEmpty()) {
                delay(10000)
                sendRequest(activity)
            } else {
                BoardData.black_data = request
                Log.e("TAG", "blacklist results: ${BoardData.black_data}")
            }
        }
    }
}