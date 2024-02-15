package com.gmb.calendarview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmb.calendarview.calendar.Events

class EventsAdapter(private var eventos: List<String>) : RecyclerView.Adapter<EventsAdapter.EventoViewHolder>() {

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeEvento: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.nomeEvento.text = evento
    }

    suspend fun atualizarEventos(novaListaEventos: List<Events>) {
        eventos = novaListaEventos.map { it.nome }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = eventos.size
}
