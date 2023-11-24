package com.se.dr.bo.seconddrawingboard.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.recyclerview.widget.GridLayoutManager
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.base.BaseActivity
import com.se.dr.bo.seconddrawingboard.databinding.ActivityMainBinding
import com.se.dr.bo.seconddrawingboard.ui.board.BoardActivity
import com.se.dr.bo.seconddrawingboard.ui.finish.FinishActivity
import com.se.dr.bo.seconddrawingboard.ui.polocy.PolicyActivity
import com.se.dr.bo.seconddrawingboard.utils.BoardData
import com.se.dr.bo.seconddrawingboard.wight.CustomDialogFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    MainViewModel::class.java
) {
    private lateinit var boardAdapter: BoardAdapter
    private var boardList = mutableListOf<String>()
    override fun setup() {
        initBoardAdapter()
        clickEvent()
    }

    //初始化BoardAdapter
    private fun initBoardAdapter() {
        boardList = BoardData.getLocalData()
        binding.isEmpty = boardList.isEmpty()
        boardAdapter = BoardAdapter(boardList)
        binding.recyclerBoard.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerBoard.adapter = boardAdapter
        boardAdapter.setOnItemClickListener(object : BoardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //点击事件
                viewModel.navigateToBoard(this@MainActivity, boardList[position])
            }

            override fun onItemLongClick(position: Int) {
                //长按事件
                showSaveDialog(position)
            }
        })
    }

    private fun clickEvent() {
        binding.imgMenu.setOnClickListener {
            binding.isOpenMenu = true
        }
        binding.lfNav.setOnClickListener {
            binding.isOpenMenu = false
        }
        binding.clNav.setOnClickListener {
        }
        binding.tvPolicy.setOnClickListener {
            navigateTo(PolicyActivity::class.java)
        }
        binding.tvStart.setOnClickListener {
            viewModel.navigateToBoard(this@MainActivity)
        }
    }

    //保存画板提示框
    private fun showSaveDialog(position: Int) {
        val dialog = CustomDialogFragment.newInstance("Confirm Delete this Painting?", "Cancel", "Delete")
        dialog.negativeClickListener = {
            viewModel.deleteLocalBoard(boardList[position], finishFun = {
                refreshData()
            })
        }
        dialog.show(supportFragmentManager, "Tip")
    }

    //刷新数据
    private fun refreshData() {
        boardList = BoardData.getLocalData()
        boardAdapter.updateData(boardList)
        binding.isEmpty = boardList.isEmpty()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x888) {
            boardList = BoardData.getLocalData()
            boardAdapter.updateData(boardList)
            binding.isEmpty = boardList.isEmpty()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.isOpenMenu == true) {
                binding.isOpenMenu = false
            } else {
                finish()
            }
        }
        return true
    }
}