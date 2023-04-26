package com.example.trackerv2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class WydatekWidok(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var holKwota: TextView = itemView.findViewById(R.id.kwota)
    var holKategoria: TextView = itemView.findViewById(R.id.kategoria)
    var holData: TextView = itemView.findViewById(R.id.data)
    var usun: ImageView = itemView.findViewById(R.id.usun)
    var edytuj: ImageView = itemView.findViewById(R.id.edytuj)
}