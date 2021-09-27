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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(val app: Application) : AndroidViewModel(app), EventRepo.Update {

    private val eventRepo = EventRepo(
        User(
            Firebase.auth.uid.toString()
        ), this
    )

    private var dataloaderArray:ArrayList<DateEvents> = ArrayList()
    val data: MutableLiveData<ArrayList<DateEvents>?> = MutableLiveData(null)

/*    val firstList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val secondList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val thirdList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fourthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fifthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val sixthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val seventhList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())*/

    val toastMessage: MutableLiveData<String?> = MutableLiveData(null)


    @SuppressLint("SimpleDateFormat")
    var startDate = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toInt()

    fun newStartDate(date: Int){
        startDate = date
        loadData()
    }

    fun addEvent(event: Event,date: Int){

        eventRepo.addEvent(event)

        data.value?.let {
            dataloaderArray = it
            dataloaderArray.forEach { dateEvents ->
                if (dateEvents.date == date){
                    dateEvents.events.add(event)
                    return@forEach
                }
            }
            data.postValue(dataloaderArray)
        }
    }

    fun editEvent(event: Event){
        eventRepo.editEvent(event)

        data.value?.let {
            dataloaderArray = it
            dataloaderArray.forEach { dateEvents ->
                if(dateEvents.date == event.date){
                    dateEvents.events.forEach { eve ->
                        if(eve.id == event.id){
                            eve.title = event.title
                            eve.note = event.note
                            eve.logs = event.logs
                            return@let
                        }
                    }
                }
            }
        }
        data.postValue(dataloaderArray)
    }
    fun deleteEvent(event: Event){
        eventRepo.deleteEvent(event)

        data.value?.let {
            dataloaderArray = it
            dataloaderArray.forEach { dateEvents ->
                if(dateEvents.date == event.date){
                    dateEvents.events.remove(event)
                    return@let
                }
            }
        }
        data.postValue(dataloaderArray)
    }

    private fun loadData(){
        dataloaderArray.clear()

        val daysInMonth = getTotalDayInMonth((startDate/100)%100)
        Log.d(TAG, "loadData: days = $daysInMonth || $startDate")
        val day = (startDate%100)
        for (i in 0..6){
            val newDay = day + i
            val newDate = if(newDay > daysInMonth){
                ((startDate/100) + 1)*100 + newDay - daysInMonth
            }
            else {
                startDate + i
            }
            Log.d("abc", "$i , loadData: $newDate")
            eventRepo.getEventList(newDate)
        }
    }

    override fun failureMessage(message: String) {
        toastMessage.postValue(message)
    }

    override fun events(dateEvents: DateEvents) {

        Log.d("abc", "eventsv: $dateEvents")

        dataloaderArray.add(dateEvents)
        if(dataloaderArray.size == 7){
            dataloaderArray.sortBy {
                it.date
            }
            data.postValue(dataloaderArray)
        }
    }

    companion object {
        const val TAG = "xyz"
    }
}