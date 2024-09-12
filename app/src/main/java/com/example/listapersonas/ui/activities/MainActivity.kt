package com.example.listapersonas.ui.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.notasapp.Note
import com.example.notasapp.NoteAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var noteAdapter: NoteAdapter
    private val noteList = mutableListOf<Note>()

    // Lista de 10 colores predefinidos
    private val predefinedColors = listOf(
        Color.parseColor("#FFEBEE"), // Color 1
        Color.parseColor("#FFCDD2"), // Color 2
        Color.parseColor("#F8BBD0"), // Color 3
        Color.parseColor("#D1C4E9"), // Color 4
        Color.parseColor("#BBDEFB"), // Color 5
        Color.parseColor("#C8E6C9"), // Color 6
        Color.parseColor("#FFECB3"), // Color 7
        Color.parseColor("#FFE0B2"), // Color 8
        Color.parseColor("#FFCCBC"), // Color 9
        Color.parseColor("#D7CCC8")  // Color 10
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el RecyclerView y el adaptador
        val rvNoteList: RecyclerView = findViewById(R.id.rvNoteList)
        rvNoteList.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(noteList, ::editNote, ::deleteNote)
        rvNoteList.adapter = noteAdapter

        // Agregar nueva nota
        val btnAddNote: Button = findViewById(R.id.btnAddNote)
        val txtNote: EditText = findViewById(R.id.txtNote)

        btnAddNote.setOnClickListener {
            val content = txtNote.text.toString()
            if (content.isNotEmpty()) {
                addNote(content)
                txtNote.text.clear() // Limpia el campo después de agregar la nota
            } else {
                Toast.makeText(this, "La nota no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para agregar una nueva nota con color blanco por defecto
    private fun addNote(content: String) {
        val newNote = Note(
            id = noteList.size + 1,
            content = content,
            color = Color.WHITE // Siempre se asigna el color blanco por defecto
        )
        noteList.add(newNote)
        noteAdapter.notifyDataSetChanged() // Actualiza la lista
    }

    // Método para editar una nota (texto y color)
    private fun editNote(note: Note) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Nota")

        // Inflar el layout del formulario de la nota
        val viewInflated = layoutInflater.inflate(R.layout.form_layout, null, false)

        // Referenciar los campos de texto y botones del formulario
        val txtNoteContent = viewInflated.findViewById<EditText>(R.id.txtNoteContent)

        // Mostrar el contenido actual de la nota en el campo de texto
        txtNoteContent.setText(note.content)

        builder.setView(viewInflated)

        // Asignar los botones de color
        val predefinedColorsWithButtons = listOf(
            R.id.colorButton1 to Color.parseColor("#FFEBEE"),
            R.id.colorButton2 to Color.parseColor("#FFCDD2"),
            R.id.colorButton3 to Color.parseColor("#F8BBD0"),
            R.id.colorButton4 to Color.parseColor("#D1C4E9"),
            R.id.colorButton5 to Color.parseColor("#BBDEFB"),
            R.id.colorButton6 to Color.parseColor("#C8E6C9"),
            R.id.colorButton7 to Color.parseColor("#FFECB3"),
            R.id.colorButton8 to Color.parseColor("#FFE0B2"),
            R.id.colorButton9 to Color.parseColor("#FFCCBC"),
            R.id.colorButton10 to Color.parseColor("#D7CCC8")
        )

        var selectedColor = note.color

        for ((buttonId, color) in predefinedColorsWithButtons) {
            val colorButton = viewInflated.findViewById<Button>(buttonId)
            colorButton.setOnClickListener {
                selectedColor = color // Actualizar el color seleccionado
            }
        }

        // Botón para guardar los cambios
        builder.setPositiveButton("Guardar") { dialog, _ ->
            // Actualizar el contenido y color de la nota
            note.content = txtNoteContent.text.toString()
            note.color = selectedColor

            // Refrescar la lista en el adaptador
            noteAdapter.notifyDataSetChanged()

            dialog.dismiss()
        }

        // Botón de cancelar
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // Método para eliminar una nota
    private fun deleteNote(note: Note) {
        noteList.remove(note)
        noteAdapter.notifyDataSetChanged() // Refresca la lista
    }
}

