package com.example.calendar.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.common.AppConsts
import com.example.calendar.common.getTotalDayInMonth
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.databinding.CreateEventDialogLayoutBinding
import com.example.calendar.databinding.EditEventDialogLayoutBinding
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.example.calendar.screen.login.LogInActivity
import com.example.calendar.screen.main.eventadapter.LogAdapter
import com.example.calendar.screen.main.eventadapter.MainAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClick {

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

        // initial starting date
        viewModel.newStartDate(
            SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toInt()
        )

        showLoadingView()

        viewModel.toastMessage.observe(this,{
            it?.let {
                viewModel.toastMessage.postValue(null)
                showToast(it)
            }
        })

        binding.next.setOnClickListener {
            showLoadingView()
            viewModel.newStartDate(
                getNextWeekStartDate(viewModel.startDate)
            )
        }

        binding.prev.setOnClickListener {
            showLoadingView()
            viewModel.newStartDate(
                getPrevWeekStartDate(viewModel.startDate)
            )
            viewModel.startDate = getPrevWeekStartDate(viewModel.startDate)
        }

        val gridLayoutManager = object : GridLayoutManager(
            this,7,HORIZONTAL,false
        ){
            override fun canScrollHorizontally() : Boolean {
                return false
            }
        }

        val adapter = MainAdapter(this,ArrayList())
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = gridLayoutManager

        // update ui based on data of 7 days
        // range date calculate and update from 7 days data
        viewModel.data.observe(this,{
            it?.let {
                var s = "${it[0].date%100} ${getMonthName((it[0].date/100)%100)}"
                s += " - "
                s += "${it[6].date%100} ${getMonthName((it[6].date/100)%100)}"
/*                "${getMonthName((date/100)%100)},${date/10000}".also {
                    binding.tvMonthYear.text = it
                }*/
                binding.tvMonthYear.text = s
                adapter.updateData(it)
                hideLoadingView()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.signout -> {
                Firebase.auth.signOut()
                startActivity(
                    Intent(this,LogInActivity::class.java)
                )
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoadingView(){
        binding.rvMain.visibility = View.INVISIBLE
        binding.prev.isEnabled = false
        binding.next.isEnabled = false
        binding.pbMainLoading.visibility = View.VISIBLE
    }

    private fun hideLoadingView(){
        binding.rvMain.visibility = View.VISIBLE
        binding.prev.isEnabled = true
        binding.next.isEnabled = true
        binding.pbMainLoading.visibility = View.INVISIBLE
    }


    // gives the date of next week start date of the date given
    private fun getNextWeekStartDate(date: Int) : Int{
        val daysInMonth = getTotalDayInMonth((date/100)%100)
        var day = (date%100) + 7

        return if(day > daysInMonth){
            (date/100 + 1) * 100 + day%daysInMonth
        }
        else date + 7
    }

    // gives the date of previous week start date of the date given
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

    // user clicked plus button to create new event
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
                    dialogBinding.etNote.text.toString(),
                    arrayListOf("Created at ${SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)}")
                ),
                date
            )
        }

        dialogBinding.cancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    // user clicked on an event
    override fun itemClicked(event: Event) {
        val dialogBinding = EditEventDialogLayoutBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .show()

        dialogBinding.etTitle.setText(event.title)
        dialogBinding.etNote.setText(event.note)
        dialogBinding.rvLogs.adapter = LogAdapter(event.logs)

        dialogBinding.delete.setOnClickListener {
            dialog.dismiss()
            viewModel.deleteEvent(event)
        }

        dialogBinding.ok.setOnClickListener {
            dialog.dismiss()
            event.title = dialogBinding.etTitle.text.toString()
            event.note = dialogBinding.etNote.text.toString()
            event.logs.add("Edited at ${SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().time)}")
            viewModel.editEvent(event)
        }

        dialogBinding.cancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}