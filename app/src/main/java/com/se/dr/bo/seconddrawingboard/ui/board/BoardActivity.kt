package com.se.dr.bo.seconddrawingboard.ui.board

import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.SeekBar
import android.widget.Toast
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.base.BaseActivity
import com.se.dr.bo.seconddrawingboard.databinding.ActivityBoardBinding
import com.se.dr.bo.seconddrawingboard.ui.finish.FinishActivity
import com.se.dr.bo.seconddrawingboard.ui.start.StartViewModel
import com.se.dr.bo.seconddrawingboard.utils.BoardData
import com.se.dr.bo.seconddrawingboard.utils.BoardUtils
import com.se.dr.bo.seconddrawingboard.utils.MMKVStorage
import com.se.dr.bo.seconddrawingboard.wight.CustomDialogFragment
import com.se.dr.bo.seconddrawingboard.wight.Draw
import com.se.dr.bo.seconddrawingboard.wight.DrawBoardView
import com.se.dr.bo.seconddrawingboard.wight.DrawBoardView.DrawMode.DRAW_CIRCLE
import com.se.dr.bo.seconddrawingboard.wight.DrawBoardView.DrawMode.DRAW_PATH
import com.se.dr.bo.seconddrawingboard.wight.DrawBoardView.DrawMode.ERASER

class BoardActivity : BaseActivity<ActivityBoardBinding, BoardViewModel>(
    R.layout.activity_board,
    BoardViewModel::class.java
), DrawBoardView.OnDrawListener {
    private var imageName: String? = null
    private var bitmap: Bitmap? = null
    private var isSave = false

    //画笔宽度
    private var strokeWidth = 30f

    //橡皮宽度
    private var eraserWidth = 30f
    override fun setup() {
        binding.boardView.setOnDrawListener(this)
        binding.boardView.setLineStrokeWidth(strokeWidth)
        binding.boardView.setEraserStrokeWidth(eraserWidth)
        clickEnv()
        getMainBitmap()
        seekBarFun()
    }

    private fun getMainBitmap() {
        imageName = intent.getStringExtra("imageName")
        imageName?.let {
            bitmap = MMKVStorage.getBitmap(it)
        }
        bitmap?.let {
            binding.boardView.imageBitmap = it
        }
    }

    private fun clickEnv() {
        dialogClick()
        binding.openLoad = false
        binding.imgBack.setOnClickListener {
            showSaveDialog()
        }
        binding.imgGone.setOnClickListener {
            if (isSave) {
                binding.openLoad = true
                viewModel.saveLocalBoard(imageName, binding.boardView.imageBitmap, finishFun = {
                    viewModel.navigateToFinish(it, this)
                    binding.openLoad = false
                })
            } else {
                Toast.makeText(this, "Please draw something", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgUndo.setOnClickListener {
            binding.boardView.undo()
        }
        binding.imgRedo.setOnClickListener {
            binding.boardView.redo()
        }
    }

    private fun dialogClick() {
        binding.selectedType = 1
        binding.tvBrush.setOnClickListener {
            binding.openBrush = true
            binding.openColor = false
        }
        binding.tvEraser.setOnClickListener {
            binding.openBrush = false
            binding.openColor = true
        }
        binding.imgX.setOnClickListener {
            binding.openColor = false
        }
        binding.imgXBrush.setOnClickListener {
            binding.openBrush = false
        }
        binding.clBrush.setOnClickListener {
            binding.openBrush = false
        }
        binding.clBrush1.setOnClickListener {
        }
        binding.llColor.setOnClickListener {
            binding.openColor = false
        }

        binding.imgColor.setColorListener { color ->
            binding.boardView.paintColor = color
        }

        binding.imgF.setOnClickListener {
            binding.selectedType = 1
            binding.boardView.drawMode = DRAW_PATH
            binding.boardView.setRound(Paint.Cap.SQUARE)
            binding.seekBar.progress = strokeWidth.toInt()
            binding.tvNum.text = binding.seekBar.progress.toString()

        }
        binding.imgY.setOnClickListener {
            binding.selectedType = 2
            binding.boardView.drawMode = DRAW_PATH
            binding.boardView.setRound(Paint.Cap.ROUND)
            binding.seekBar.progress = strokeWidth.toInt()
            binding.tvNum.text = binding.seekBar.progress.toString()

        }
        binding.imgG.setOnClickListener {
            binding.selectedType = 3
            binding.boardView.drawMode = DRAW_PATH
            binding.boardView.setBlurMask(true)
            binding.seekBar.progress = strokeWidth.toInt()
            binding.tvNum.text = binding.seekBar.progress.toString()

        }
        binding.imgR.setOnClickListener {
            binding.selectedType = 4
            binding.boardView.drawMode = ERASER
            binding.seekBar.progress = eraserWidth.toInt()
            binding.tvNum.text = binding.seekBar.progress.toString()
        }
        binding.imgC.setOnClickListener {
            binding.selectedType = 5
            binding.boardView.drawMode = DRAW_CIRCLE
            binding.seekBar.progress = strokeWidth.toInt()
            binding.tvNum.text = binding.seekBar.progress.toString()
        }
    }

    override fun onDraw(draw: Draw?) {
        isSave = true
    }

    override fun updateRevocationStatus() {
        viewModel.onUndoStatus(binding)
    }


    //保存画板提示框
    private fun showSaveDialog() {
        if (!isSave) {
            finish()
            return
        }
        val dialog = CustomDialogFragment.newInstance(
            "Unsaved data. Do you want to exit?",
            "Confirm",
            "Cancel"
        )
        dialog.positiveClickListener = {
            finish()
        }
        dialog.show(supportFragmentManager, "Tip")
    }

    private fun seekBarFun() {
        binding.seekBar.max = 100
        binding.seekBar.progress = 30
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                if (binding.selectedType != 4) {
                    strokeWidth = progress.toFloat()
                } else {
                    eraserWidth = progress.toFloat()
                }
                binding.tvNum.text = progress.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (binding.selectedType != 4) {
                    binding.boardView.setLineStrokeWidth(strokeWidth)
                } else {
                    binding.boardView.setEraserStrokeWidth(eraserWidth)
                }
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showSaveDialog()
        }
        return true
    }
}