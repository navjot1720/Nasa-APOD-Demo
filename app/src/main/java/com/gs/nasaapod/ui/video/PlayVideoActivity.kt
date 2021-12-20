package com.gs.nasaapod.ui.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseActivity
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.data.AppConstants
import com.gs.nasaapod.databinding.ActivityPlayVideoBinding


class PlayVideoActivity : BaseActivity<ActivityPlayVideoBinding, BaseViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setUpWebViewSettings()
        val videoUrl = intent.getStringExtra(AppConstants.KEY_VIDEO_URL) ?: ""
        binding.webView.loadUrl(videoUrl)

    }

    override fun getLayoutId() = R.layout.activity_play_video

    override fun initViewModel(): Nothing? = null

    override fun initVariables() {
    }

    override fun setObservers() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebViewSettings() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            allowFileAccess = true
            allowContentAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(false)
        }
    }

}