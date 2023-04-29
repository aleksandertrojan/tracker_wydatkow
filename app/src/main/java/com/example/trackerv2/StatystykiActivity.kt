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

        val db = SqliteDatabase(this)
        val pierwsza = "SELECT * FROM ${SqliteDatabase.TABELA} ORDER BY ${SqliteDatabase.COLUMN_DATA} ASC LIMIT 1"
        val ostatnia = "SELECT * FROM ${SqliteDatabase.TABELA} ORDER BY ${SqliteDatabase.COLUMN_DATA} DESC LIMIT 1"
        val suma = "SELECT SUM(${SqliteDatabase.COLUMN_KWOTA}) FROM ${SqliteDatabase.TABELA}"

        //val miesiac będzie mozna zamienic na wMonth jak będzie w poprawnym formacie
        //na razie trzeba ręcznie wpisywać datę
        val miesiac = mies(wMonth!!)
        val sumaMiesiac = "SELECT SUM(${SqliteDatabase.COLUMN_KWOTA}) FROM ${SqliteDatabase.TABELA} WHERE strftime('%m-%Y', ${SqliteDatabase.COLUMN_DATA}) = \"$miesiac\""

        val cursorP = db.readableDatabase.rawQuery(pierwsza, null)
        val cursorO = db.readableDatabase.rawQuery(ostatnia, null)
        val cursorS = db.readableDatabase.rawQuery(suma, null)
        val cursorSMsc = db.readableDatabase.rawQuery(sumaMiesiac, null)
        var pBazy: String? = null
        var kBazy: String? = null
        var sKwot: Double? = null
        var sMsc: Double? = null

        if (cursorP.moveToFirst()) {
            pBazy = cursorP.getString(cursorP.getColumnIndexOrThrow(SqliteDatabase.COLUMN_DATA))
        }

        if (cursorO.moveToFirst()) {
            kBazy = cursorO.getString(cursorO.getColumnIndexOrThrow(SqliteDatabase.COLUMN_DATA))
        }

        if (cursorS.moveToFirst()) {
            sKwot = cursorS.getDouble(0)
        }

        if (cursorSMsc.moveToFirst()) {
            sMsc = cursorSMsc.getDouble(0)
        }

        cursorP.close()
        cursorO.close()
        cursorS.close()
        cursorSMsc.close()
        db.close()

        //ten fragment jest tylko do wyświetlenia sum
        //żeby się upewnić, że działa
        //możesz to później usunąć w statystyki.xml
        val sumaTextView = findViewById<TextView>(R.id.SumaTextView)
        if (sKwot != null) {
            sumaTextView.text = "Suma kwot: $sKwot"
        }

        val razemTextView = findViewById<TextView>(R.id.RazemTextView)
        if (sMsc != null) {
            razemTextView.text = "Razem za miesiąc: $sMsc"
        }
        //###################################################################

        val poleMonth: Spinner = findViewById(R.id.pod_month)
        val options = spinner(pBazy!!, kBazy!!)
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
        /*poleMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                wMonth = parent.getItemAtPosition(position).toString()
                textView.text = mies(wMonth!!)
                if(wMonth != null) {
                    //linijka niżej nie działa, bo wMonth ma zły format daty, baza potrzebuje MM-yyyy
                    //val expensesByCategory = sumawgkategorii(wMonth!!)
                    val expensesByCategory = sumawgkategorii(miesiac)
                    val categories = listOf("dom i rachunki", "wydatki podstawowe", "zdrowie", "kosmetyki", "transport", "edukacja", "odzież i obuwie", "jedzenie", "elektronika", "zwierzęta domowe", "inne wydatki")

                    for ((index, category) in categories.withIndex()) {
                        val textView = when (index) {
                            0 -> findViewById<TextView>(R.id.pod_txt0)
                            1 -> findViewById<TextView>(R.id.pod_txt1)
                            2 -> findViewById<TextView>(R.id.pod_txt2)
                            3 -> findViewById<TextView>(R.id.pod_txt3)
                            4 -> findViewById<TextView>(R.id.pod_txt4)
                            5 -> findViewById<TextView>(R.id.pod_txt5)
                            6 -> findViewById<TextView>(R.id.pod_txt6)
                            7 -> findViewById<TextView>(R.id.pod_txt7)
                            8 -> findViewById<TextView>(R.id.pod_txt8)
                            9 -> findViewById<TextView>(R.id.pod_txt9)
                            10 -> findViewById<TextView>(R.id.pod_txt10)
                            else -> null
                        }
                        textView?.let {
                            it.text = expensesByCategory[category]?.toString() ?: "0.0"
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }*/
    }

    fun mies(s: String): String {
        val lista = listOf(
            "styczeń", "luty", "marzec", "kwiecień", "maj", "czewiec",
            "lipiec", "sierpień", "wrzesień","październik", "listopad", "grudzień"
        )
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
            wynik = "0$mies-$rok"
        } else {
            wynik = "$mies-$rok"
        }
        return wynik
    }

    fun spinner(s: String, s1: String): List<String> {
        val mp = s.substring(5, 7).toInt()
        var rp = s.substring(0, 4).toInt()
        val mk = s1.substring(5, 7).toInt()
        val rk = s1.substring(0, 4).toInt()
        val lista = listOf("styczeń", "luty", "marzec", "kwiecień", "maj", "czewiec",
            "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"
        )
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

    fun sumawgkategorii(month: String): Map<String, Double> {
        val db = SqliteDatabase(this)
        val cursor = db.readableDatabase.rawQuery(
            "SELECT ${SqliteDatabase.COLUMN_KATEGORIA}, SUM(${SqliteDatabase.COLUMN_KWOTA}) FROM ${SqliteDatabase.TABELA} WHERE strftime('%m-%Y', ${SqliteDatabase.COLUMN_DATA}) = ? GROUP BY ${SqliteDatabase.COLUMN_KATEGORIA}",
            arrayOf(month)
        )
        val wydatki = mutableMapOf<String, Double>()
        //println("Liczba wyników: ${cursor.count}")

        while (cursor.moveToNext()) {
            val kategoria = cursor.getString(0)
            val kwota = cursor.getDouble(1)
            wydatki[kategoria] = kwota

        }
        for ((kategoria, kwota) in wydatki) {
            println("kategoria: $kategoria, kwota: $kwota")
        }
        cursor.close()
        db.close()

        return wydatki
    }
}




