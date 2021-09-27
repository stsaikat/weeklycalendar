package com.example.calendar.screen.main.eventadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.datamodel.Event

class SingleDateAdapter(
    var list: ArrayList<Event>,
    val listener: OnItemClick
)
    : RecyclerView.Adapter<SingleDateAdapter.ViewHolder>() {

    interface OnItemClick{
        fun itemClicked(event: Event)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val title: TextView = item.findViewById(R.id.tv_title)
        val note: TextView = item.findViewById(R.id.tv_note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.note.text = list[position].note
        holder.itemView.setOnClickListener {
            listener.itemClicked(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}