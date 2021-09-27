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

    fun editEvent(event: Event){
        database.document("${event.date}")
            .get()
            .addOnSuccessListener {
                val dateEvents = it.toObject(DateEvents::class.java)
                dateEvents?.let { dateE ->
                    dateE.events.forEach { eve ->
                        if(eve.id == event.id){
                            eve.title = event.title
                            eve.note = event.note
                            eve.logs = event.logs
                            database.document("${event.date}").set(dateE)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("xyz", "edit: failed")
            }
    }

    fun deleteEvent(event: Event){
        database.document("${event.date}")
            .get()
            .addOnSuccessListener {
                val dateEvents = it.toObject(DateEvents::class.java)
                dateEvents?.let { dateE ->
                    dateE.events.remove(event)
                }
            }
            .addOnFailureListener {
                Log.d("xyz", "edit: failed")
            }
    }

    fun getEvents(date: Int){
        Log.d("TAG", "getEvents: fb called $date")
        database.document("$date")
            .get()
            .addOnCompleteListener {
                var data = DateEvents(date)
                if(it.isSuccessful){
                    val dateEvents = it.result.toObject(DateEvents::class.java)
                    if (dateEvents != null) {
                        data = dateEvents
                    }
                }
                listener.events(data)
            }
/*            .addOnSuccessListener {
                val dateEvents = it.toObject(DateEvents::class.java)
                if (dateEvents != null) {
                    listener.events(dateEvents)
                }
            }
            .addOnFailureListener {
                // TODO: 26/09/2021
                listener.events(DateEvents(date))
            }*/
    }
}