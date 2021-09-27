package com.example.calendar.datamodel

data class Event(
    var title: String? = "",
    val date: Int = -1,
    val id: String = "",
    var note: String? = "",
    var logs: ArrayList<String> = ArrayList()
)