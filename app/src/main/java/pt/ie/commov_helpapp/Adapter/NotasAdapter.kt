package pt.ie.commov_helpapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.R

class NotasAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Notas>()

    class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TituloItemView: TextView = itemView.findViewById(R.id.outputTitulo)
        val DescItemView: TextView = itemView.findViewById(R.id.OutputDescricao)
        val HoraItemView: TextView = itemView.findViewById(R.id.Hora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val itemView = inflater.inflate(R.layout.custom_row, parent, false)
        return NotasViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val current = notas[position]
        holder.TituloItemView.text = current.titulo
        holder.DescItemView.text = current.descricao
        holder.HoraItemView.text = current.DataHora
    }

    internal fun setNotas(notas: List<Notas>) {
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() = notas.size
}