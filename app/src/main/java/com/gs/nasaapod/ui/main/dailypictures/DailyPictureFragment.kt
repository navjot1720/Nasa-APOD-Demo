package com.gs.nasaapod.ui.main.dailypictures

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseFragment
import com.gs.nasaapod.databinding.FragmentDailyPicturesBinding
import com.gs.nasaapod.ui.main.MainViewModel
import java.util.*


class DailyPictureFragment : BaseFragment<FragmentDailyPicturesBinding, MainViewModel>() {


    private var mDatePickerDialog: DatePickerDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel?.getPicturesForToday()

    }

    override fun getLayoutId() = R.layout.fragment_daily_pictures


    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun initVariables() {

    }


    override fun setObservers() {

        viewModel?.selectDateLiveData?.observe(viewLifecycleOwner, {
            selectDate()
        })

    }


    private fun selectDate() {
        if (mDatePickerDialog == null) {
            val calendar = Calendar.getInstance()
            mDatePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DatePickerTheme,
                { _, year, month, dayOfMonth ->

                    // getting pictures for selected date
                    val selectedDate = "$year-${month + 1}-$dayOfMonth"
                    viewModel?.getPictureByDate(selectedDate)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // setting maxDate to today
            mDatePickerDialog?.datePicker?.maxDate = calendar.timeInMillis

            // setting minDate as the nasaAPOD api gives data for dates after 1995/06/16
            calendar.set(1995, 6, 16)
            mDatePickerDialog?.datePicker?.minDate = calendar.timeInMillis
        }
        mDatePickerDialog?.show()
    }


}