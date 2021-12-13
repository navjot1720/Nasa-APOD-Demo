package com.gs.nasaapod.ui.main.dailypictures

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseFragment
import com.gs.nasaapod.base.DefaultResponseModel
import com.gs.nasaapod.data.ApiStatusCodes
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.databinding.FragmentDailyPicturesBinding
import com.gs.nasaapod.ui.main.MainViewModel
import com.gs.nasaapod.utils.AppUtils
import com.gs.nasaapod.utils.gone
import com.gs.nasaapod.utils.visible
import java.util.*


class DailyPictureFragment : BaseFragment<FragmentDailyPicturesBinding, MainViewModel>(), View.OnClickListener {


    private var currentPictureModel: FavouritePicturesEntity? = null

    private var mDatePickerDialog: DatePickerDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.getPicturesForToday()

    }

    override fun getLayoutId() = R.layout.fragment_daily_pictures

    override fun getBindingVariable() = 0


    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun initVariables() {

    }


    override fun setObservers() {

        viewModel?.picturesLiveData?.observe(this, { response ->
            response?.let { model ->
                binding.atvRetry.gone()
                binding.clMain.visible()

                currentPictureModel = model

                binding.sdvImage.setImageURI(model.url)

                binding.atvTitle.text = model.title
                binding.atvExplanation.text = model.explanation
                binding.atvDate.text = AppUtils.parseDateTimeFormat(model.date)

                viewModel?.checkIfExistsInFavourites(model.createCompositeId())
            }
        })


        viewModel?.ifExistsInFavouritesLiveData?.observe(this, { it ->
            currentPictureModel?.let { model ->
                setFavourite(it != 0)
            }
        })


        binding.atvSelectDate.setOnClickListener(this)
        binding.aivFavourite.setOnClickListener(this)
        binding.atvRetry.setOnClickListener(this)

    }


    /**
     * method to set favourite status
     */
    private fun setFavourite(isFavourite: Boolean) {
        currentPictureModel?.let {
            it.isFavourite = isFavourite
            if (isFavourite) {
                binding.aivFavourite.setImageResource(R.drawable.ic_heart_filled)
            } else {
                binding.aivFavourite.setImageResource(R.drawable.ic_heart_hollow)
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atvSelectDate ->{
                selectDate()
            }

            R.id.aivFavourite ->{
                currentPictureModel?.let {
                    if (it.isFavourite) {
                        viewModel?.removeFromFavourites(it.createCompositeId())
                        setFavourite(false)
                    } else {
                        viewModel?.addToFavourites(it)
                        setFavourite(true)
                    }
                }
            }

            R.id.atvRetry ->{
                viewModel?.getPicturesForToday()
            }
        }
    }


    private fun selectDate(){
        if (mDatePickerDialog == null){
            val calendar = Calendar.getInstance()
            mDatePickerDialog = DatePickerDialog(requireContext(), R.style.DatePickerTheme, { dialog, year, month, dayOfMonth ->

                // getting pictures for selected date
                val selectedDate = "$year-${month+1}-$dayOfMonth"
                viewModel?.getPictureByDate(selectedDate)

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            // setting maxDate to today
            mDatePickerDialog?.datePicker?.maxDate = calendar.timeInMillis

            // setting minDate as the nasaAPOD api gives data for dates after 1995/06/16
            calendar.set(1995, 6,16)
            mDatePickerDialog?.datePicker?.minDate = calendar.timeInMillis
        }
        mDatePickerDialog?.show()
    }


    override fun handleApiErrorResponse(responseModel: DefaultResponseModel<*>?) {
        if(responseModel?.code == ApiStatusCodes.ERROR){
            binding.atvRetry.visible()
        }else {
            binding.atvRetry.gone()
            super.handleApiErrorResponse(responseModel)
        }
    }


    override fun showProgressBar() {
        binding.progress.visible()
    }


    override fun hideProgressBar() {
        binding.progress.gone()
    }

}