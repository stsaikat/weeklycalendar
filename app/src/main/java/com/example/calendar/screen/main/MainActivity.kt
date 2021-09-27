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
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.example.calendar.screen.login.LogInActivity
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

        val gridLayoutManager = object : GridLayoutManager(this,7){
            override fun canScrollVertically() : Boolean {
                return false
            }
        }

        val adapter = MainAdapter(this,ArrayList())
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = gridLayoutManager

        viewModel.data.observe(this,{
            it?.let {
                val date = viewModel.startDate
                "${getMonthName((date/100)%100)},${date/10000}".also {
                    binding.tvMonthYear.text = it
                }
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