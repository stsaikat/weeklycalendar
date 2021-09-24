package com.example.calendar.datamodel

data class DateEvents(
    val date: Int = 0,
    val events: ArrayList<Event> = ArrayList()
)