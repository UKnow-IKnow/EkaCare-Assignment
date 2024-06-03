package com.example.ekacareassignment.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import java.time.LocalDate
import java.util.Locale

class DatePicker {

    @RequiresApi(Build.VERSION_CODES.O)
    fun openDatePicker(
        context: Context,
        onDateSetListener: DatePickerDialog.OnDateSetListener,
        date: LocalDate
    ) {
        Locale.setDefault(Locale.ENGLISH)
        val datePickerDialog = DatePickerDialog(
            context,
            onDateSetListener,
            date.year,
            date.monthValue,
            date.dayOfMonth
        )
        datePickerDialog.show()
    }
}