package com.se.dr.bo.seconddrawingboard.ui.board

import android.graphics.Bitmap
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.databinding.ActivityBoardBinding
import com.se.dr.bo.seconddrawingboard.ui.finish.FinishActivity
import com.se.dr.bo.seconddrawingboard.utils.BoardData
import com.se.dr.bo.seconddrawingboard.utils.MMKVStorage

class BoardViewModel : ViewModel() {

    fun saveLocalBoard(name: String?, bitmap: Bitmap, finishFun: (boardName: String) -> Unit) {
        val boardname = name ?: "board_${System.currentTimeMillis()}"
        MMKVStorage.saveBitmap(
            boardname,
            bitmap
        )
        if (name.isNullOrEmpty()) {
            BoardData.saveLocalData(boardname)
        } else {
            BoardData.prioritizeElement(BoardData.getLocalData(), boardname)
        }
        finishFun(boardname)
    }

    //撤销状态
    fun onUndoStatus(binding: ActivityBoardBinding) {
        if (binding.boardView.isHasUndo) {
            binding.imgUndo.setImageResource(R.drawable.ic_undo_2)
        } else {
            binding.imgUndo.setImageResource(R.drawable.ic_undo_1)
        }
        if (binding.boardView.isHasRedo) {
            binding.imgRedo.setImageResource(R.drawable.ic_redo_2)
        } else {
            binding.imgRedo.setImageResource(R.drawable.ic_redo_1)
        }
    }

    //跳转完成页面
    fun navigateToFinish(name: String, activity: BoardActivity) {
        val bundle = Bundle()
        bundle.putString("imageName", name)
        activity.navigateTo(FinishActivity::class.java, bundle)
        activity.finish()
    }

}