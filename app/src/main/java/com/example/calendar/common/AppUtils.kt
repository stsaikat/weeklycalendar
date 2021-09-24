package com.example.calendar.common

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.example.calendar.R

fun getTotalDayInMonth(month: Int) : Int{
    return when(month){
        1,3,5,7,8,10,12 -> 31
        2 -> 28
        else -> 30
    }
}