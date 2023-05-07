package com.example.trackerv2

import android.annotation.SuppressLint
import android.app.DatePickerDialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.*
import com.example.trackerv2.R
import com.google.android.material.datepicker.MaterialDatePicker

import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var dataBase: SqliteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Peculium"
        val widokWydatkow: RecyclerView = findViewById(R.id.listaWydatkow)
        val linearLayoutManager = LinearLayoutManager(this)
        widokWydatkow.layoutManager = linearLayoutManager
        widokWydatkow.setHasFixedSize(true)
        dataBase = SqliteDatabase(this)
        val wszystkieWydatki = dataBase.listaWydatkow()
        val mAdapter = WydatekAdapter(this, wszystkieWydatki)
        widokWydatkow.adapter = mAdapter
        if (wszystkieWydatki.isEmpty()) {
            Toast.makeText(
                this,
                "Lista jest pusta, dodaj nowy wydatek.",
                Toast.LENGTH_LONG
            ).show()
        }

        val btnDodaj: Button = findViewById(R.id.btnDodaj)
        btnDodaj.setOnClickListener { addTaskDialog() }

        val btnStatystyki = findViewById<Button>(R.id.btnStatystyki)
        btnStatystyki.setOnClickListener {
            val intent = Intent(this, StatystykiActivity::class.java)
            startActivity(intent)
        }

    }


    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.okienko, null)
        val poleKwota: EditText = subView.findViewById(R.id.podajkwote)

        /// Obszar spinner
        val poleKategoria: Spinner = subView.findViewById(R.id.podajkategorie)
        val options = listOf("Wybierz kategorie", "dom i rachunki", "wydatki podstawowe",
                "zdrowie", "kosmetyki","transport","edukacja","odzież i obuwie","jedzenie",
                "elektronika","zwierzęta domowe","inne wydatki")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        poleKategoria.adapter = adapter
        /// Obszar data

        val poleData = subView.findViewById<EditText>(R.id.podajData)
        poleData.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                poleData.setText(SimpleDateFormat("yyyy-MM-dd").format(calendar.time))
            }
            DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }




        ///

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dodaj nowy wydatek")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton("Dodaj wydatek") { _, _ ->
            val kwota = poleKwota.text.toString()
            var kategoria: String? = null
            val data = poleData.text.toString()
            if (poleKategoria.selectedItemPosition != 0) {
                kategoria = poleKategoria.selectedItem.toString()
            }
            if (TextUtils.isEmpty(kwota) || TextUtils.isEmpty(kategoria) || TextUtils.isEmpty(data) ) {
                Toast.makeText(
                        this@MainActivity,
                        "Coś poszło nie tak, sprawdź poprawność wprowadzonych danych.",
                        Toast.LENGTH_LONG
                ).show()
            } else {
                val nowy = Wydatek(kwota.toDouble(), kategoria!!, data)
                dataBase.add(nowy)
                finish()
                startActivity(intent)
            }
        }
        builder.setNegativeButton("Anuluj") { _, _ -> Toast.makeText(this@MainActivity, "Dodawanie wydatku zostało anulowane.",
            Toast.LENGTH_LONG).show()}
        builder.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        dataBase.close()
    }
}