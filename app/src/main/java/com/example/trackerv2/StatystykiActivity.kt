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
import android.icu.text.DecimalFormat
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


        val decimalFormat = DecimalFormat("#0.00")

        //obszar spinner
        val textKat_0: TextView = findViewById(R.id.pod_txt0)
        val textKat_1: TextView = findViewById(R.id.pod_txt1)
        val textKat_2: TextView = findViewById(R.id.pod_txt2)
        val textKat_3: TextView = findViewById(R.id.pod_txt3)
        val textKat_4: TextView = findViewById(R.id.pod_txt4)
        val textKat_5: TextView = findViewById(R.id.pod_txt5)
        val textKat_6: TextView = findViewById(R.id.pod_txt6)
        val textKat_7: TextView = findViewById(R.id.pod_txt7)
        val textKat_8: TextView = findViewById(R.id.pod_txt8)
        val textKat_9: TextView = findViewById(R.id.pod_txt9)
        val textKat_10: TextView = findViewById(R.id.pod_txt10)

        val progBar_0 = findViewById<ProgressBar>(R.id.pod_prb0)
        val progBar_1 = findViewById<ProgressBar>(R.id.pod_prb1)
        val progBar_2 = findViewById<ProgressBar>(R.id.pod_prb2)
        val progBar_3 = findViewById<ProgressBar>(R.id.pod_prb3)
        val progBar_4 = findViewById<ProgressBar>(R.id.pod_prb4)
        val progBar_5 = findViewById<ProgressBar>(R.id.pod_prb5)
        val progBar_6 = findViewById<ProgressBar>(R.id.pod_prb6)
        val progBar_7 = findViewById<ProgressBar>(R.id.pod_prb7)
        val progBar_8 = findViewById<ProgressBar>(R.id.pod_prb8)
        val progBar_9 = findViewById<ProgressBar>(R.id.pod_prb9)
        val progBar_10 = findViewById<ProgressBar>(R.id.pod_prb10)

        //textView.text = "Wpisz tutaj"

        val db = SqliteDatabase(this)
        val pierwsza = "SELECT * FROM ${SqliteDatabase.TABELA} ORDER BY ${SqliteDatabase.COLUMN_DATA} ASC LIMIT 1"
        val ostatnia = "SELECT * FROM ${SqliteDatabase.TABELA} ORDER BY ${SqliteDatabase.COLUMN_DATA} DESC LIMIT 1"
        val suma = "SELECT SUM(${SqliteDatabase.COLUMN_KWOTA}) FROM ${SqliteDatabase.TABELA}"



        val cursorP = db.readableDatabase.rawQuery(pierwsza, null)
        val cursorO = db.readableDatabase.rawQuery(ostatnia, null)
        val cursorS = db.readableDatabase.rawQuery(suma, null)
        var pBazy: String? = null
        var kBazy: String? = null
        var sKwot: Double? = null

        if (cursorP.moveToFirst()) {
            pBazy = cursorP.getString(cursorP.getColumnIndexOrThrow(SqliteDatabase.COLUMN_DATA))
        }

        if (cursorO.moveToFirst()) {
            kBazy = cursorO.getString(cursorO.getColumnIndexOrThrow(SqliteDatabase.COLUMN_DATA))
        }

        if (cursorS.moveToFirst()) {
            sKwot = cursorS.getDouble(0)
        }


        cursorP.close()
        cursorO.close()
        cursorS.close()
        db.close()

        //ten fragment jest tylko do wyświetlenia sum
        //żeby się upewnić, że działa
        //możesz to później usunąć w statystyki.xml
        val sumaTextView = findViewById<TextView>(R.id.SumaTextView)
        if (sKwot != null) {
            sumaTextView.text = "Suma kwot: ${decimalFormat.format(sKwot)}"
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
                val miesiac = mies(wMonth!!)//.toString()
                val sumaMiesiac = "SELECT SUM(${SqliteDatabase.COLUMN_KWOTA}) FROM ${SqliteDatabase.TABELA} WHERE strftime('%m-%Y', ${SqliteDatabase.COLUMN_DATA}) = \"$miesiac\""
                val cursorSMsc = db.readableDatabase.rawQuery(sumaMiesiac, null)
                var sMsc: Double? = null
                if (cursorSMsc.moveToFirst()) {
                    sMsc = cursorSMsc.getDouble(0)
                }
                cursorSMsc.close()
                val razemTextView = findViewById<TextView>(R.id.RazemTextView)
                if (sMsc != null) {
                    razemTextView.text = "Razem za miesiąc: ${decimalFormat.format(sMsc)}"
                }
                val kat0 = sumawgkategorii(mies(wMonth!!)).getOrDefault("dom i rachunki",0.00)
                val kat1 = sumawgkategorii(mies(wMonth!!)).getOrDefault("wydatki podstawowe",0.00)
                val kat2 = sumawgkategorii(mies(wMonth!!)).getOrDefault("zdrowie",0.00)
                val kat3 = sumawgkategorii(mies(wMonth!!)).getOrDefault("kosmetyki",0.00)
                val kat4 = sumawgkategorii(mies(wMonth!!)).getOrDefault("transport",0.00)
                val kat5 = sumawgkategorii(mies(wMonth!!)).getOrDefault("edukacja",0.00)
                val kat6 = sumawgkategorii(mies(wMonth!!)).getOrDefault("odzież i obuwie",0.00)
                val kat7 = sumawgkategorii(mies(wMonth!!)).getOrDefault("jedzenie",0.00)
                val kat8 = sumawgkategorii(mies(wMonth!!)).getOrDefault("elektronika",0.00)
                val kat9 = sumawgkategorii(mies(wMonth!!)).getOrDefault("zwierzęta domowe",0.00)
                val kat10 = sumawgkategorii(mies(wMonth!!)).getOrDefault("inne wydatki",0.00)
                textKat_0.text ="${decimalFormat.format(kat0)}"
                textKat_1.text ="${decimalFormat.format(kat1)}"
                textKat_2.text ="${decimalFormat.format(kat2)}"
                textKat_3.text ="${decimalFormat.format(kat3)}"
                textKat_4.text ="${decimalFormat.format(kat4)}"
                textKat_5.text ="${decimalFormat.format(kat5)}"
                textKat_6.text ="${decimalFormat.format(kat6)}"
                textKat_7.text ="${decimalFormat.format(kat7)}"
                textKat_8.text ="${decimalFormat.format(kat8)}"
                textKat_9.text ="${decimalFormat.format(kat9)}"
                textKat_10.text ="${decimalFormat.format(kat10)}"
                progBar_0.progress = progB(kat0,sMsc!!)
                progBar_1.progress = progB(kat1,sMsc!!)
                progBar_2.progress = progB(kat2,sMsc!!)
                progBar_3.progress = progB(kat3,sMsc!!)
                progBar_4.progress = progB(kat4,sMsc!!)
                progBar_5.progress = progB(kat5,sMsc!!)
                progBar_6.progress = progB(kat6,sMsc!!)
                progBar_7.progress = progB(kat7,sMsc!!)
                progBar_8.progress = progB(kat8,sMsc!!)
                progBar_9.progress = progB(kat9,sMsc!!)
                progBar_10.progress = progB(kat10,sMsc!!)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
    fun progB(k: Double,s: Double): Int{
        val m =(k/s)*100
        val iM=m.toInt()
        return iM
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

    fun spinner1(s: String, s1: String): List<String> {
        val mp = s.substring(3, 5).toInt()
        var rp = s.substring(6, 10).toInt()
        val mk = s1.substring(3, 5).toInt()
        val rk = s1.substring(6, 10).toInt()
        val lista = listOf("styczeń", "luty", "marzec", "kwiecień", "maj",
            "czewiec", "lipiec", "sierpień", "wrzesień", "październik",
            "listopad", "grudzień")
        val lis = mutableListOf<String>()
        val k = rk - rp - 1
        if (rk - rp == 0) {
            lis.addAll(lista.subList(mp - 1, mk))
            for (i in 0 until lis.size) {
                lis[i] += " $rk"
            }
        } else if (k > 0) {
            lis.addAll(lista.subList(mp - 1, lista.size))
            for (i in 0 until k) {
                lis.addAll(lista)
            }
            lis.addAll(lista.subList(0, mk))
        } else {
            lis.addAll(lista.subList(mp - 1, mk))
        }
        for (i in 0 until lis.size) {
            if (lis[i] == "styczeń" && lis[0] != "styczeń") {
                rp++
            }
            lis[i] += " $rp"
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




