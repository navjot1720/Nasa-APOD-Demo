package com.gs.nasaapod.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseActivity
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.databinding.ActivitySplashBinding
import com.gs.nasaapod.ui.main.MainActivity
import com.gs.nasaapod.ui.main.MainViewModel
import com.gs.nasaapod.utils.getViewModel


class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.animationView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                // launching MainActivity once animation ends
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })

    }


    override fun getLayoutId() = R.layout.activity_splash


    override fun getBindingVariable() = 0


    override fun initViewModel() = null


    override fun initVariables() {

    }


    override fun setObservers() {

    }

}