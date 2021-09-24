package com.example.calendar.common

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.example.calendar.R

fun getCreateEventDialog(context: Context) : Dialog {

    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.create_event_dialog_layout)
    val lp = WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    lp.copyFrom(dialog.window?.attributes)
    dialog.window?.attributes = lp
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog

}

fun getTotalDayInMonth(month: Int) : Int{
    return when(month){
        1,3,5,7,8,10,12 -> 31
        2 -> 28
        else -> 30
    }
}