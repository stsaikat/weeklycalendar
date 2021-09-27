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

    override fun onBindViewHolder(holder: ViewHolder, p: Int) {

        holder.title.text = if(list[p].title != null && list[p].title!!.length > 12) "${list[p].title!!.substring(0,9)}..."
                            else list[p].title
        holder.note.text = if(list[p].note != null && list[p].note!!.length > 12) "${list[p].note!!.substring(0,9)}..."
        else list[p].note
        holder.itemView.setOnClickListener {
            listener.itemClicked(list[p])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}