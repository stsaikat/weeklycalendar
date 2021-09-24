package com.example.calendar.source

import android.util.Log
import com.example.calendar.datamodel.DateEvents
import com.example.calendar.datamodel.Event
import com.example.calendar.datamodel.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseSource(private val user: User,val listener: Update) {

    interface Update{
        fun failureMessage(message: String)
        fun events(dateEvents: DateEvents)
    }

    private val database = Firebase.firestore.collection(user.id)

    fun addEvent(event: Event){
        database.document("${event.date}")
            .get()
            .addOnSuccessListener {
                var dateEvents = it.toObject(DateEvents::class.java)
                if(dateEvents == null){
                    dateEvents = DateEvents(event.date, arrayListOf(event))
                    database.document("${event.date}").set(dateEvents)
                }
                else {
                    dateEvents.events.add(event)
                    database.document("${event.date}").set(dateEvents)
                }
            }
            .addOnFailureListener {
                Log.d("xyz", "addEvent: failed")
            }
    }

    fun getEvents(date: Int){
        database.document("$date")
            .get()
            .addOnSuccessListener {
                val dateEvents = it.toObject(DateEvents::class.java)
                if (dateEvents != null) {
                    listener.events(dateEvents)
                }
            }
            .addOnFailureListener {

            }
    }
}