package com.example.trackerv2

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.util.*

internal class WydatekAdapter(private val context: Context, listaW: ArrayList<Wydatek>) :
    RecyclerView.Adapter<WydatekWidok>(), Filterable {
    private var listaW: ArrayList<Wydatek>
    private val mArrayList: ArrayList<Wydatek>
    private val mDatabase: SqliteDatabase
    init {
        this.listaW = listaW
        this.mArrayList = listaW
        mDatabase = SqliteDatabase(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WydatekWidok {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.wydatek, parent, false)
        return WydatekWidok(view)
    }
    override fun onBindViewHolder(holder: WydatekWidok, position: Int) {
        val wydatki = listaW[position]
        holder.holKwota.text = wydatki.kwota.toString()
        holder.holKategoria.text = wydatki.kategoria
        holder.edytuj.setOnClickListener { editTaskDialog(wydatki) }
        holder.usun.setOnClickListener {
            mDatabase.delete(wydatki.id)
            (context as Activity).finish()
            context.startActivity(context.intent)
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                listaW = if (charString.isEmpty()) {
                    mArrayList
                }
                else {
                    val filteredList = ArrayList<Wydatek>()
                    for (wydatki in mArrayList) {
                        if (wydatki.kwota.toString().contains(charString)) {
                            filteredList.add(wydatki)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listaW
                return filterResults
            }
            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            )
            {
                listaW =
                    filterResults.values as ArrayList<Wydatek>
                notifyDataSetChanged()
            }
        }
    }
    override fun getItemCount(): Int {
        return listaW.size
    }
    private fun editTaskDialog(wydatki: Wydatek) {
        val inflater = LayoutInflater.from(context)
        val subView = inflater.inflate(R.layout.okienko, null)
        val poleKwota: EditText = subView.findViewById(R.id.podajkwote)
        val poleKategoria: EditText = subView.findViewById(R.id.podajkategorie)
        poleKwota.setText(wydatki.kwota.toString())
        poleKategoria.setText(wydatki.kategoria)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edytuj wydatek")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton(
            "EDYTUJ WYDATEK"
        ) { _, _ ->
            val kwotaStr = poleKwota.text.toString()
            val kategoria = poleKategoria.text.toString()
            if (TextUtils.isEmpty(kwotaStr)) {
                Toast.makeText(
                    context,
                    "Coś poszło nie tak, sprawdź poprawność wprowadzonych danych.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                mDatabase.update(
                    Wydatek(
                        Objects.requireNonNull<Any>(wydatki.id) as Int,
                        kwotaStr.toDouble(),
                        kategoria
                    )
                )
                (context as Activity).finish()
                context.startActivity(context.intent)
            }
        }
        builder.setNegativeButton(
            "ANULUJ"
        ) { _, _ -> Toast.makeText(context, "Anulowano dodawanie wydatku.", Toast.LENGTH_LONG).show() }
        builder.show()
    }

    fun updateData(newList: List<Wydatek>) {
        listaW.clear()
        listaW.addAll(newList)
        notifyDataSetChanged()
    }
}