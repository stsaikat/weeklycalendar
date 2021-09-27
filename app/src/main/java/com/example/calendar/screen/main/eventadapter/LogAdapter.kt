package com.example.calendar.screen.main.eventadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R


class LogAdapter(private var list: ArrayList<String>)
    : RecyclerView.Adapter<LogAdapter.ViewHolder>()
{

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val logTextView = item.findViewById<TextView>(R.id.tv_log)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.log_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.logTextView.text = list[position]
    }

    override fun getItemCount() = list.size
}