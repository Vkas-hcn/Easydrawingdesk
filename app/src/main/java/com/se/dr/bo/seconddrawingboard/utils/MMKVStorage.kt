package com.se.dr.bo.seconddrawingboard.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tencent.mmkv.MMKV
import java.io.ByteArrayOutputStream

object MMKVStorage {

    private val mmkv: MMKV = MMKV.defaultMMKV()

    fun saveString(key: String, value: String) {
        mmkv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return mmkv.decodeString(key, defaultValue) ?: defaultValue
    }

    fun saveInt(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return mmkv.decodeInt(key, defaultValue)
    }
    fun saveBitmap(key: String, bitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        mmkv.encode(key, byteArray)
    }

    fun getBitmap(key: String): Bitmap? {
        val byteArray = mmkv.decodeBytes(key)
        return if (byteArray != null) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else {
            null
        }
    }

    //删除某条mmkv数据
    fun remove(key: String) {
        mmkv.remove(key)
    }

}
