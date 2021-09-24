package com.example.calendar.screen.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.calendar.common.getTotalDayInMonth
import com.example.calendar.datamodel.DateEvents
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.example.calendar.repository.EventRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(val app: Application) : AndroidViewModel(app), EventRepo.Update {

    val eventRepo = EventRepo(
        User(
            Firebase.auth.uid.toString()
        ), this
    )

    val firstList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val secondList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val thirdList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fourthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fifthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val sixthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val seventhList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())

    val toastMessage: MutableLiveData<String?> = MutableLiveData(null)


    @SuppressLint("SimpleDateFormat")
    var startDate = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toInt()

    fun addEvent(event: Event,date: Int){

        eventRepo.addEvent(event)

        val currentMonthDay = getTotalDayInMonth((date/100)%100)
        val startDateMonthDay = getTotalDayInMonth((startDate/100)%100)
        val serial = if(currentMonthDay == startDateMonthDay) date - startDate
                     else{
                         startDateMonthDay - ((startDate%100)%startDateMonthDay) +
                         (date%100)%currentMonthDay
                     }

        Log.d("xyz", "addEvent: $serial")
        when(serial){
            0 -> {
                firstList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            1 -> {
                secondList.value?.let {
                    it.add(event)
                    secondList.postValue(it)
                }
            }
            2 -> {
                thirdList.value?.let {
                    it.add(event)
                    thirdList.postValue(it)
                }
            }
            2+1 -> {
                fourthList.value?.let {
                    it.add(event)
                    fourthList.postValue(it)
                }
            }
            4 -> {
                fifthList.value?.let {
                    it.add(event)
                    fifthList.postValue(it)
                }
            }
            5 -> {
                sixthList.value?.let {
                    it.add(event)
                    sixthList.postValue(it)
                }
            }
            6 -> {
                seventhList.value?.let {
                    it.add(event)
                    seventhList.postValue(it)
                }
            }
        }
    }

    fun loadData(){
        firstList.postValue(ArrayList())
        secondList.postValue(ArrayList())
        thirdList.postValue(ArrayList())
        fourthList.postValue(ArrayList())
        fifthList.postValue(ArrayList())
        sixthList.postValue(ArrayList())
        seventhList.postValue(ArrayList())

        val daysInMonth = getTotalDayInMonth((startDate/100)%100)
        val day = (startDate%100)
        for (i in 0..7){
            val newDay = day + i
            val newDate = if(newDay > daysInMonth){
                ((startDate/100) + 1)*100 + newDay%(daysInMonth+1) + 1
            }
            else {
                eventRepo.getEventList(startDate+i)
            }
        }
    }

    override fun failureMessage(message: String) {
        toastMessage.postValue(message)
    }

    override fun events(dateEvents: DateEvents) {
        val currentMonthDay = getTotalDayInMonth((dateEvents.date/100)%100)
        val startDateMonthDay = getTotalDayInMonth((startDate/100)%100)
        val serial = if(currentMonthDay == startDateMonthDay) dateEvents.date - startDate
        else{
            startDateMonthDay - ((startDate%100)%startDateMonthDay)
            (dateEvents.date%100)%currentMonthDay
        }

        when(serial){
            0 -> firstList.postValue(dateEvents.events)
            1 -> secondList.postValue(dateEvents.events)
            2 -> thirdList.postValue(dateEvents.events)
            2+1 -> fourthList.postValue(dateEvents.events)
            4 -> fifthList.postValue(dateEvents.events)
            5 -> sixthList.postValue(dateEvents.events)
            6 -> seventhList.postValue(dateEvents.events)
        }
    }
}