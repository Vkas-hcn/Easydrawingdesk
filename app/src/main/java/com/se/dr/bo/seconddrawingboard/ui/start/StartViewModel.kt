package com.se.dr.bo.seconddrawingboard.ui.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.se.dr.bo.seconddrawingboard.utils.BoardData
import com.se.dr.bo.seconddrawingboard.utils.CloakFun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class StartViewModel : ViewModel() {
    fun setUuid() {
        BoardData.uuid_data = UUID.randomUUID().toString()
    }


}