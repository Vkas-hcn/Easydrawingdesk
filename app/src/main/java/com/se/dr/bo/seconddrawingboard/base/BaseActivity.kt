package com.se.dr.bo.seconddrawingboard.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<DB : ViewDataBinding, VM : ViewModel>(
    private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    protected lateinit var binding: DB
        private set
    protected val viewModel: VM by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        setup()
    }

    abstract fun setup()

     fun <T : AppCompatActivity> navigateTo(
        destination: Class<T>,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, destination)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    fun <T : AppCompatActivity> navigateForResult(
        destination: Class<T>,
        bundle: Bundle? = null,
        requestCode: Int
    ) {
        val intent = Intent(this, destination)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent,requestCode)
    }
}
