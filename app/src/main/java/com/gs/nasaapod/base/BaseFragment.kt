package com.gs.nasaapod.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gs.nasaapod.R
import com.gs.nasaapod.data.ApiStatusCodes


abstract class BaseFragment<MyDataBinding : ViewDataBinding, MyViewModel : BaseViewModel> :
    Fragment() {

    lateinit var binding: MyDataBinding
    var viewModel: MyViewModel? = null

    private lateinit var rootView: View


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

        viewModel = initViewModel()

        initVariables()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        rootView = binding.root
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.setErrorObserver(viewLifecycleOwner, errorObserver)

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


    protected fun isFragmentAdded(): Boolean {
        return (!requireActivity().isFinishing && !requireActivity().isDestroyed && isAdded)
    }


    protected fun showToast(msg: String?) {
        if (requireActivity() is BaseActivity<*, *>) {
            (requireActivity() as BaseActivity<*, *>).showToast(msg)
        }
    }


    protected fun isNetworkAvailable(): Boolean {
        if (requireActivity() is BaseActivity<*, *>) {
            return (requireActivity() as BaseActivity<*, *>).isNetworkAvailable()
        }
        return false
    }

}