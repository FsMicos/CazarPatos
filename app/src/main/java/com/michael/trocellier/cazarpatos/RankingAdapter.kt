package com.michael.trocellier.cazarpatos

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(private val dataSet: ArrayList<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        val textViewPosicion: TextView = view.findViewById(R.id.textViewPosicion)
        val textViewPatosCazados: TextView = view.findViewById(R.id.textViewPatosCazados)
        val textViewUsuario: TextView = view.findViewById(R.id.textViewUsuario)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewPosicion: TextView
        val textViewPatosCazados: TextView
        val textViewUsuario: TextView

        init {
            textViewPosicion = view.findViewById(R.id.textViewPosicion)
            textViewPatosCazados = view.findViewById(R.id.textViewPatosCazados)
            textViewUsuario = view.findViewById(R.id.textViewUsuario)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val header = LayoutInflater.from(parent.context).inflate(R.layout.ranking_list, parent, false)
            ViewHolderHeader(header)
        } else {
            val item = LayoutInflater.from(parent.context).inflate(R.layout.ranking_list, parent, false)
            ViewHolder(item)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderHeader -> {
                holder.textViewPosicion.text = "#"
                holder.textViewPatosCazados.text = "Patos Cazados"
                holder.textViewUsuario.text = "Usuario"

                // Styling para el header
                holder.textViewPosicion.paintFlags = holder.textViewPosicion.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                holder.textViewPosicion.setTextColor(holder.textViewPosicion.context.getColor(R.color.colorPrimaryDark))

                holder.textViewPatosCazados.paintFlags = holder.textViewPatosCazados.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                holder.textViewPatosCazados.setTextColor(holder.textViewPatosCazados.context.getColor(R.color.colorPrimaryDark))

                holder.textViewUsuario.paintFlags = holder.textViewUsuario.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                holder.textViewUsuario.setTextColor(holder.textViewUsuario.context.getColor(R.color.colorPrimaryDark))
            }
            is ViewHolder -> {
                val playerIndex = position - 1 // Compensar por el header
                holder.textViewPosicion.text = position.toString()
                holder.textViewPatosCazados.text = dataSet[playerIndex].huntedDucks.toString()
                holder.textViewUsuario.text = dataSet[playerIndex].username
            }
        }
    }

    override fun getItemCount() = dataSet.size + 1
}