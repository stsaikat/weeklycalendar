package com.example.calendar.screen.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.calendar.datamodel.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {


    val firstList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val secondList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val thirdList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fourthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val fifthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val sixthList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())
    val seventhList: MutableLiveData<ArrayList<Event>> = MutableLiveData(ArrayList())

    @SuppressLint("SimpleDateFormat")
    var startDate = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time).toInt()

    fun addEvent(event: Event,date: Int){
        when(date - startDate){
            0 -> {
                firstList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            1 -> {
                secondList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            2 -> {
                thirdList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            2+1 -> {
                fourthList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            4 -> {
                fifthList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            5 -> {
                sixthList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
            6 -> {
                seventhList.value?.let {
                    it.add(event)
                    firstList.postValue(it)
                }
            }
        }
    }

    fun loadData(){
        // TODO: 24/09/2021  
    }
}