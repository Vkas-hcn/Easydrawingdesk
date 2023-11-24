package com.se.dr.bo.seconddrawingboard.ui.polocy

import android.webkit.WebView
import android.webkit.WebViewClient
import com.se.dr.bo.seconddrawingboard.R
import com.se.dr.bo.seconddrawingboard.base.BaseActivity
import com.se.dr.bo.seconddrawingboard.databinding.ActivityPolicyBinding
import com.se.dr.bo.seconddrawingboard.databinding.ActivityStartBinding
import com.se.dr.bo.seconddrawingboard.ui.start.StartViewModel
import com.se.dr.bo.seconddrawingboard.utils.BoardData

class PolicyActivity : BaseActivity<ActivityPolicyBinding, PolicyViewModel>(
    R.layout.activity_policy,
    PolicyViewModel::class.java
) {

    override fun setup() {
        binding.imageView2.setOnClickListener {
            finish()
        }
        binding.wvPolicy.loadUrl(BoardData.policy_url)
        binding.wvPolicy.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }
}