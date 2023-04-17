package com.example.trwydatkow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RemoteViews.RemoteCollectionItems
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView

class WydatekAdapter : RecyclerView.Adapter<WydatekAdapter.WydatekWidok>() {

    private var lista: ArrayList<Wydatek> = ArrayList()
    //private var onClickItem: ((Wydatek) -> Unit)? = null

    fun dodajWydatek(items: ArrayList<Wydatek>){
        this.lista = items
        notifyDataSetChanged()
    }

    //fun setOnClickItem(callback: )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WydatekWidok (
        LayoutInflater.from(parent.context).inflate(R.layout.wpis_do_listy, parent, false))


    override fun onBindViewHolder(holder: WydatekWidok, position: Int) {
        val x = lista[position]
        holder.poleWydatek(x)
        holder.cardView.setOnClickListener {  }
    }

    override fun getItemCount(): Int {
        return lista.size
    }


    class WydatekWidok(var view: View) : RecyclerView.ViewHolder(view) {
        val cardView = view.findViewById<CardView>(R.id.cardView)
        private var kwota = view.findViewById<TextView>(R.id.edKwota)
        private var kategoria = view.findViewById<TextView>(R.id.spKategoria)
        private var data = view.findViewById<TextView>(R.id.edData)

        fun poleWydatek(T: Wydatek) {
            kwota.text = T.kwota.toString()
            kategoria.text = T.kategoria
            data.text = T.data
        }
    }
}