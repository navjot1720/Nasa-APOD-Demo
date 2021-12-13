package com.gs.nasaapod.base


import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.gs.nasaapod.R
import com.gs.nasaapod.data.NetworkStatusMessage


abstract class BaseActivity<MyDataBinding : ViewDataBinding, MyViewModel : BaseViewModel> :
    AppCompatActivity(), BaseNavigator {

    lateinit var binding: MyDataBinding
    var viewModel: MyViewModel? = null

    private var mProgressDialog: Dialog? = null


    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun initViewModel(): MyViewModel?

    abstract fun initVariables()

    abstract fun setObservers()


    private val errorObserver = Observer<DefaultResponseModel<*>> {
        hideProgressBar()
        handleApiErrorResponse(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
        viewModel = initViewModel()

        initVariables()

        viewModel?.navigator = this
        viewModel?.setErrorObserver(this, errorObserver)

        setObservers()

    }


    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())

        binding.executePendingBindings()
    }


    /**
     * method to handle Api Error Response
     * Override this method to provide class level implementation
     */
    @CallSuper
    protected open fun handleApiErrorResponse(responseModel: DefaultResponseModel<*>?) {
        val message = responseModel?.msg ?: NetworkStatusMessage.NETWORK_ERROR.message
        showToast(message)
    }


    override fun showProgressBar() {
        if (!isFinishing) {
            hideProgressBar()
            mProgressDialog = Dialog(this)
            mProgressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val view: View = LayoutInflater.from(this).inflate(R.layout.layout_progressbar, null)
            mProgressDialog?.setContentView(view)

            mProgressDialog?.setCancelable(false)

            mProgressDialog?.window?.let {
                it.setBackgroundDrawable(
                    ContextCompat.getDrawable(this, android.R.color.transparent)
                )
                it.setDimAmount(0f)
                it.setGravity(Gravity.CENTER)
            }
            mProgressDialog?.show()
        }
    }


    override fun hideProgressBar() {
        if (!isFinishing && !isDestroyed) {
            if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
                mProgressDialog?.dismiss()
            }
            mProgressDialog = null
        }
    }


    override fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }



    override fun goBack() {
        onBackPressed()
    }


    override fun isNetworkAvailable(): Boolean {
        val connectivity =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        return (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected)
    }


    override fun showNoNetworkError() {
        showToast(NetworkStatusMessage.NO_INTERNET.message)
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null && (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_MOVE)
            && view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)

            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]

            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.hideSoftInputFromWindow(
                    window.decorView.applicationWindowToken, 0
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    /**
     * method to adjust FontScale/Density of the screen when changed manually from device settings
     */
    fun adjustFontScale(configuration: Configuration) {
        val displayMetrics = resources.displayMetrics
        var updateConfig = false

        if (configuration.fontScale > 1.0F) {
            configuration.fontScale = 1.0F
            updateConfig = true
        }
        if (displayMetrics.density > 2.625F) {
            displayMetrics.density = 2.625F
            displayMetrics.densityDpi = 420
            configuration.densityDpi = 420
            updateConfig = true
        }

        if (updateConfig) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                applyOverrideConfiguration(configuration)
//
//            } else {
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density
            resources.updateConfiguration(configuration, displayMetrics)
//            }
        }
    }


    override fun onDestroy() {
        hideProgressBar()
        super.onDestroy()
    }

}