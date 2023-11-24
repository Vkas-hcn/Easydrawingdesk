package com.se.dr.bo.seconddrawingboard.ui.finish

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.base.BaseActivity
import com.se.dr.bo.seconddrawingboard.databinding.ActivityFinishBinding
import com.se.dr.bo.seconddrawingboard.databinding.ActivityStartBinding
import com.se.dr.bo.seconddrawingboard.ui.start.StartViewModel
import com.se.dr.bo.seconddrawingboard.utils.BoardUtils
import com.se.dr.bo.seconddrawingboard.utils.MMKVStorage

class FinishActivity : BaseActivity<ActivityFinishBinding, FinishViewModel>(
    R.layout.activity_finish,
    FinishViewModel::class.java
) {
    private var imageName: String? = null
    private var bitmap: Bitmap? = null
    override fun setup() {
        imageName = intent.getStringExtra("imageName")
        imageName?.let {
            bitmap = MMKVStorage.getBitmap(it)
        }
        bitmap?.let {
            binding.imgFinish.setImageBitmap(it)
        }
        binding.imageView2.setOnClickListener {
            finish()
        }
        binding.tvDownload.setOnClickListener {
            haveStoragePermissions()
        }
        binding.tvShare.setOnClickListener {
            bitmap?.let { it1 -> imageName?.let { it2 -> BoardUtils.shareImage(this, it1, it2) } }
        }
    }

    //是否有存储权限
    fun haveStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                bitmap?.let { it1 -> BoardUtils.saveMediaToStorage(it1, this) }
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0x123
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0x123 -> {
                bitmap?.let { it1 -> BoardUtils.saveMediaToStorage(it1, this) }
            }
        }
    }

}