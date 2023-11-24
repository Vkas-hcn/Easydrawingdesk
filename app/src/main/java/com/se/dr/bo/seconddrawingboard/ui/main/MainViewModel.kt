package com.se.dr.bo.seconddrawingboard.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.se.dr.bo.seconddrawingboard.ui.board.BoardActivity
import com.se.dr.bo.seconddrawingboard.utils.BoardData

class MainViewModel:ViewModel() {
     fun navigateToBoard(activity: MainActivity,name:String?=null) {
        val bundle = Bundle()
        bundle.putString("imageName", name)
        activity.navigateForResult(BoardActivity::class.java,bundle,0x888)
    }
    fun deleteLocalBoard(name:String,finishFun:()->Unit){
        BoardData.deleteLocalData(name)
        finishFun()
    }
}