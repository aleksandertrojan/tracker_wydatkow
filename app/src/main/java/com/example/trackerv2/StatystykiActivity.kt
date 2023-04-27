/*package com.example.trackerv2

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class StatystykiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statystyki)

        val btnPowrot: Button = findViewById(R.id.btnPowrót)
        btnPowrot.setOnClickListener {
            finish()
        }
        val poleMonth: Spinner = findViewById(R.id.pod_month)
        val options = listOf("Wybierz kategorie", "dom i rachunki", "wydatki podstawowe",
                "zdrowie", "kosmetyki","transport","edukacja","odzież i obuwie","jedzenie",
                "elektronika","zwierzęta domowe","inne wydatki")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        poleMonth.adapter = adapter
    }
}*/
package com.example.trackerv2

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StatystykiActivity : AppCompatActivity() {
    private var wMonth: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statystyki)

        val btnPowrot: Button = findViewById(R.id.btnPowrót)
        btnPowrot.setOnClickListener {
            finish()
        }

        //obszar spinner
        val textView: TextView = findViewById(R.id.pod_txt0)
        //textView.text = "Wpisz tutaj"

        var pBazy: String? = null //dla daty 1 zapisu
        var kBazy: String? = null //dla daty ostatniego zapisu
        val poleMonth: Spinner = findViewById(R.id.pod_month)
        var options = spinner("27/03/2023"/*pBazy*/,"10/07/2023"/*kBazy*/)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        poleMonth.adapter = adapter

        poleMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                wMonth = parent.getItemAtPosition(position).toString()
                textView.text = mies(wMonth!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        // mies(wMonth!!) // dla zapytania do bazy

    }
    fun mies(s: String): String {
        val lista = listOf("styczeń", "luty", "marzec", "kwiecień", "maj", "czewiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień")
        val rok = s.takeLast(4)
        val m = s.dropLast(5)
        var mies = -1
        var wynik = ""
        for ((index, value) in lista.withIndex()) {
            if (value == m) {
                mies = index + 1
                break
            }
        }
        if (mies < 10) {
            wynik = "0$mies/$rok"
        } else {
            wynik = "$mies/$rok"
        }
        return wynik
    }

    fun spinner(s: String, s1: String): List<String> {
        val mp = s.substring(3, 5).toInt()
        var rp = s.substring(6, 10).toInt()
        val mk = s1.substring(3, 5).toInt()
        val rk = s1.substring(6, 10).toInt()
        val lista = listOf("styczeń", "luty", "marzec", "kwiecień", "maj", "czewiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień")
        var lis = mutableListOf<String>()
        if (rk - rp == 0) {
            lis = lista.subList(mp - 1, mk).map { it + " " + rk.toString() }.toMutableList()
        } else {
            val k = rk - rp - 1
            if (k > 0) {
                lis = (lista.subList(mp - 1, lista.size) + lista.repeat(k) + lista.subList(0, mk)) as MutableList<String>
            } else {
                lis = lista.subList(mp - 1, mk).toMutableList()
            }
            for (i in 0 until lis.size) {
                if (lis[i] == "styczeń" && lis[0] != "styczeń") {
                    rp += 1
                }
                lis[i] = lis[i] + " " + rp.toString()
            }
        }
        return lis
    }

    fun <T> List<T>.repeat(k: Int): List<T> = mutableListOf<T>().apply {
        repeat(k) {
            addAll(this@repeat)
        }
    }
}
