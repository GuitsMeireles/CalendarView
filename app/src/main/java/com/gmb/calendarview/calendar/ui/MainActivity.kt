package com.gmb.calendarview.calendar.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.gmb.calendarview.EventsAdapter
import com.gmb.calendarview.R
import com.gmb.calendarview.calendar.AppDatabase
import com.gmb.calendarview.calendar.Events
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var database: AppDatabase
    private lateinit var eventosRecyclerView: RecyclerView
    private lateinit var eventosAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        val btnAddEvent: Button = findViewById(R.id.btnAddEvent)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build()

        // Configurar RecyclerView
        eventosRecyclerView = findViewById(R.id.eventosRecyclerView)
        eventosRecyclerView.layoutManager = LinearLayoutManager(this)
        eventosAdapter = EventsAdapter(emptyList()) // Inicialize o adapter com uma lista vazia
        eventosRecyclerView.adapter = eventosAdapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = calendarToTimestamp(year, month, dayOfMonth)
            carregarEventos(selectedDate)
        }

        btnAddEvent.setOnClickListener {
            showAddEventDialog()
        }
    }

    private fun calendarToTimestamp(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.timeInMillis
    }

    private fun showAddEventDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Adicionar Evento")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val eventName = input.text.toString()
            if (eventName.isNotEmpty()) {
                adicionarEvento(eventName)
            }
        }

        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun adicionarEvento(nome: String) {
        lifecycleScope.launch {
            database.eventoDao().insertEvents(Events(nome = nome, data = calendarView.date))
            carregarEventos(calendarView.date)
        }
    }

    private fun carregarEventos(data: Long) {
        lifecycleScope.launch {
            val eventos = database.eventoDao().obterEventsPorData(data)
            eventos.collect { eventosList ->
                eventosAdapter.atualizarEventos(eventosList)
            }
        }
    }
}
