package com.example.notasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R

class NoteAdapter(
    private val notes: List<Note>,
    private val onEdit: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteContent: TextView = view.findViewById(R.id.lblNoteContent)
        val noteContainer: CardView = view.findViewById(R.id.cardViewNote)
        val editButton: Button = view.findViewById(R.id.btnEditNote)
        val deleteButton: Button = view.findViewById(R.id.btnDeleteNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteContent.text = note.content
        holder.noteContainer.setCardBackgroundColor(note.color)

        holder.editButton.setOnClickListener { onEdit(note) }
        holder.deleteButton.setOnClickListener { onDelete(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}