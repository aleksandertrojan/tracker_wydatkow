package com.example.trackerv2

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

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
        val wszystkieWydatki = dataBase.listaWydatków()
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
        val poleKategoria: EditText = subView.findViewById(R.id.podajkategorie)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dodaj nowy wydatek")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton("Dodaj wydatek") { _, _ ->
            val kwota = poleKwota.text.toString()
            val kategoria = poleKategoria.text.toString()
            if (TextUtils.isEmpty(kwota)) {
                Toast.makeText(
                    this@MainActivity,
                    "Coś poszło nie tak, sprawdź poprawność wprowadzonych danych.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                val nowy = Wydatek(kwota.toDouble(), kategoria)
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