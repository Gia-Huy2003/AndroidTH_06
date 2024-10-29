package com.example.bai63

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes: List<Note>, private val onClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)

        init {
            itemView.setOnClickListener {
                onClick(notes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.textViewTitle.text = notes[position].title
        holder.textViewDate.text = notes[position].date
    }

    override fun getItemCount() = notes.size
}