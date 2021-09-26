package com.example.calendar.screen.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar.common.AppConsts
import com.example.calendar.common.getTotalDayInMonth
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.databinding.CreateEventDialogLayoutBinding
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),MainAdapter.OnItemClick {

    private lateinit var mToast: Toast

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: CreateEventDialogLayoutBinding
    private lateinit var user: User
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = Gson().fromJson(
            intent.getStringExtra(AppConsts.USER),
            User::class.java
        )

        viewModel.newStartDate(
            SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toInt()
        )

        viewModel.toastMessage.observe(this,{
            it?.let {
                viewModel.toastMessage.postValue(null)
                showToast(it)
            }
        })

        binding.next.setOnClickListener {
            viewModel.newStartDate(
                getNextWeekStartDate(viewModel.startDate)
            )
        }

        binding.prev.setOnClickListener {
            viewModel.newStartDate(
                getPrevWeekStartDate(viewModel.startDate)
            )
            viewModel.startDate = getPrevWeekStartDate(viewModel.startDate)
        }


        val adapter = MainAdapter(this,ArrayList())
        binding.rvMain.adapter = adapter

        viewModel.data.observe(this,{
            it?.let {
                val date = viewModel.startDate
                "${getMonthName((date/100)%100)},${date/10000}".also {
                    binding.tvMonthYear.text = it
                }
                adapter.updateData(it)
            }
        })
    }

    private fun getNextWeekStartDate(date: Int) : Int{
        val daysInMonth = getTotalDayInMonth((date/100)%100)
        var day = (date%100) + 7

        return if(day > daysInMonth){
            (date/100 + 1) * 100 + day%daysInMonth
        }
        else date + 7
    }

    private fun getPrevWeekStartDate(date: Int) : Int{
        val daysInPrevMonth = getTotalDayInMonth((date/100)%100 - 1)
        var day = (date%100) - 7

        return if(day < 1){
            (date/100 - 1) * 100 + daysInPrevMonth+day
        }
        else date - 7
    }

    private fun showToast(message: String){
        if(this::mToast.isInitialized) mToast.cancel()
        mToast = Toast.makeText(this,message, Toast.LENGTH_LONG)
        mToast.show()
    }

    override fun onCreateClick(date: Int) {
        val dialogBinding = CreateEventDialogLayoutBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .show()

        dialogBinding.ok.setOnClickListener {
            dialog.dismiss()
            viewModel.addEvent(
                Event(
                    dialogBinding.etTitle.text.toString(),
                    date,
                    System.currentTimeMillis().toString(),
                    dialogBinding.etNote.text.toString()
                ),
                date
            )
        }

        dialogBinding.cancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}