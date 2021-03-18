package pt.ie.commov_helpapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import pt.ie.commov_helpapp.Data.Entitys.Notas
import pt.ie.commov_helpapp.R
import pt.ie.commov_helpapp.editarnotas



class NotasAdapter internal constructor(context: Context) : RecyclerView.Adapter<NotasAdapter.NotasViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notas = emptyList<Notas>()

    class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TituloItemView: TextView = itemView.findViewById(R.id.outputTitulo)
        val DescItemView: TextView = itemView.findViewById(R.id.OutputDescricao)
        val HoraItemView: TextView = itemView.findViewById(R.id.Hora)

        /*init{
            itemView.setOnClickListener{
                val Position: Int = adapterPosition
                Toast.makeText(itemView.context, "Clicastes no Item # ${position +1}", Toast.LENGTH_SHORT).show()
                val IdItem: Int =
            }
        }*/

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


        holder.itemView.setOnClickListener{
            //val Position: Int = adapterPosition
            //Toast.makeText(itemView.context, "Clicastes no Item # ${IdItem}", Toast.LENGTH_SHORT).show()
            val IdItem: Int = current.id
            Toast.makeText(holder.itemView.context, "Clicastes no Item # ${IdItem}", Toast.LENGTH_SHORT).show()

            val intent = Intent(holder.itemView.context, editarnotas::class.java)
            intent.putExtra("idDoItem",IdItem)
            holder.itemView.context.startActivity(intent)

         }

    }

    internal fun setNotas(notas: List<Notas>) {
        this.notas = notas
        notifyDataSetChanged()
    }

    override fun getItemCount() = notas.size
}