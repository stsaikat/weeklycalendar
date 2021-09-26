package com.example.calendar.screen.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.datamodel.DateEvents
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(val listener: OnItemClick,private var list: ArrayList<DateEvents>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnItemClick{
        fun onCreateClick(date: Int)
    }

    fun updateData(l: ArrayList<DateEvents>){
        Log.d("ac", "updateData: ${l.size}")
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val addButton: ImageButton = item.findViewById(R.id.create)
        val dateTextView: TextView = item.findViewById(R.id.date)
        val rv: RecyclerView = item.findViewById(R.id.rv_single_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_day,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.addButton.setOnClickListener { listener.onCreateClick(list[position].date) }
        "${list[position].date%100}".also { holder.dateTextView.text = it }
        holder.rv.adapter = SingleDateAdapter(list[position].events)
    }

    override fun getItemCount() = list.size
}