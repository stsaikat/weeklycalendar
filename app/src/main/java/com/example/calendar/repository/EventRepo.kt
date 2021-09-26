package com.example.calendar.repository

import android.util.Log
import com.example.calendar.datamodel.DateEvents
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.example.calendar.source.FirebaseSource

class EventRepo(private val user: User, val listener: Update) : FirebaseSource.Update {

    interface Update{
        fun failureMessage(message: String)
        fun events(dateEvents: DateEvents)
    }

    private val firebaseSource = FirebaseSource(user,this)

    fun getEventList(date: Int){
        firebaseSource.getEvents(date)
    }

    fun addEvent(event: Event){
        firebaseSource.addEvent(event)
    }

    override fun failureMessage(message: String) {
        listener.failureMessage(message)
    }

    override fun events(dateEvents: DateEvents) {
        Log.d("xyz", "events: $dateEvents")
        listener.events(dateEvents)
    }
}