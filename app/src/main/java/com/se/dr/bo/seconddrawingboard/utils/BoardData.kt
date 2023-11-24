package com.se.dr.bo.seconddrawingboard.utils

import android.util.Log
import com.tencent.mmkv.MMKV

object BoardData {
    val policy_url = "https://github.com/"
    private val mmkvBoard by lazy {
        MMKV.defaultMMKV()
    }

    var board_data: String = ""
        set(value) {
            mmkvBoard.encode("board_data", value)
            field = value
        }
        get() = mmkvBoard.decodeString("board_data", "") ?: ""
    var uuid_data: String = ""
        set(value) {
            mmkvBoard.encode("uuid_data", value)
            field = value
        }
        get() = mmkvBoard.decodeString("uuid_data", "") ?: ""

    var black_data: String = ""
        set(value) {
            mmkvBoard.encode("black_data", value)
            field = value
        }
        get() = mmkvBoard.decodeString("black_data", "") ?: ""
    //保存数据，用逗号隔开
    fun saveLocalData(data: String) {
        val data = "$data,$board_data"
        board_data = data
    }

    //获取本地数据
    fun getLocalData(): MutableList<String> {
        val list = mutableListOf<String>()
        board_data.split(",").forEach {
            if (it.isNotEmpty()) {
                list.add(it)
            }
        }
        return list
    }
    //数组排序
    fun prioritizeElement(array: MutableList<String>, element: String) {
        val list = array.toMutableList()
        list.remove(element)
        list.add(0, element)
        board_data = list.joinToString(",")
    }


    //删除本地数据
    fun deleteLocalData(name: String) {
        MMKVStorage.remove(name)
        val list = mutableListOf<String>()
        board_data.split(",").forEach {
            if (it.isNotEmpty() && it != name) {
                list.add(it)
            }
        }
        board_data = list.joinToString(",")
    }

}