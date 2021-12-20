package com.gs.nasaapod.base

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.gs.nasaapod.R
import com.gs.nasaapod.data.ApiStatusCodes


abstract class BaseActivity<MyDataBinding : ViewDataBinding, MyViewModel : BaseViewModel> :
    AppCompatActivity() {

    lateinit var binding: MyDataBinding
    var viewModel: MyViewModel? = null

    private var mProgressDialog: Dialog? = null


    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViewModel(): MyViewModel?

    abstract fun initVariables()

    abstract fun setObservers()

    /**
     * observer to handle all apis errors in a place
     */
    private val errorObserver = Observer<DefaultResponseModel<*>> {
        handleApiErrorResponse(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())

        viewModel = initViewModel()

        initVariables()

        viewModel?.setErrorObserver(this, errorObserver)

        setObservers()

    }


    /**
     * method to handle Api Error Response
     * Override this method to provide class level implementation
     */
    @CallSuper
    protected open fun handleApiErrorResponse(responseModel: DefaultResponseModel<*>?) {
        when (responseModel?.code) {
            ApiStatusCodes.TIMEOUT_ERROR -> {
                // Error message handled in view
            }
            ApiStatusCodes.CONNECTION_ERROR -> {
                showToast(getString(R.string.txt_something_went_wrong))
            }
            else -> {
                val message = responseModel?.msg ?: getString(R.string.txt_something_went_wrong)
                showToast(message)
            }
        }
    }


     fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }


     fun isNetworkAvailable(): Boolean {
        val connectivity =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        return (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected)
    }


}