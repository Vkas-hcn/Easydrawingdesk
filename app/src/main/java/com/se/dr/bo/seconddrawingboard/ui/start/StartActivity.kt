package com.se.dr.bo.seconddrawingboard.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.base.BaseActivity
import com.se.dr.bo.seconddrawingboard.databinding.ActivityMainBinding
import com.se.dr.bo.seconddrawingboard.databinding.ActivityStartBinding
import com.se.dr.bo.seconddrawingboard.ui.board.BoardActivity
import com.se.dr.bo.seconddrawingboard.ui.main.MainActivity
import com.se.dr.bo.seconddrawingboard.utils.BoardData
import com.se.dr.bo.seconddrawingboard.utils.CloakFun
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(
    R.layout.activity_start,
    StartViewModel::class.java
) {

    override fun setup() {
        if(BoardData.uuid_data.isEmpty()){
            viewModel.setUuid()
        }
        CloakFun.sendRequest(this)
    }
    //倒计时三秒跳转首页
    override fun onResume() {
        super.onResume()
        Thread {
            for (i in 100 downTo 1) {
                runOnUiThread {
                    binding.progressBarStart.progress = (100 - i)
                }
                Thread.sleep(20)
            }
            navigateTo(MainActivity::class.java)
            finish()
        }.start()
    }
    //禁止返回键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK
    }

}