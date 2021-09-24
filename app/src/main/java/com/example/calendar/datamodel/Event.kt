package com.example.calendar.datamodel

data class Event(
    val title: String? = "",
    val date: Int = -1,
    val id: String = "",
    val note: String? = ""
)