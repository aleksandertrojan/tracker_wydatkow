package com.example.trwydatkow


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var edKwota: EditText
    private lateinit var spKategoria: Spinner
    private lateinit var edData: EditText
    private lateinit var btnDodaj: Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: WydatekAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        updateScreen()
        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
        btnDodaj.setOnClickListener { dodajW() }

        /*val dateField = findViewById<EditText>(R.id.edData)

        dateField.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                dateField.setText(SimpleDateFormat("dd/MM/yyyy").format(calendar.time))
            }
            DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }*/

    }


    private fun wypiszWydatek(){
        val lista = sqliteHelper.wszystkieWpisy()
        Log.e("yes", "${lista.size}")
        adapter?.dodajWydatek(lista)
    }

    private fun dodajW(){
        val kwota = edKwota.text.toString()
        val kategoria = spKategoria.selectedItem.toString()
        val data = edData.text.toString()

        if(kwota.isEmpty() || data.isEmpty()){
            Toast.makeText(this, "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
        } else {
            val kwota = kwota.toDouble()
            val x = Wydatek(kwota = kwota, kategoria = kategoria, data = data)
            val status = sqliteHelper.wstaw(x)
            if (status > -1){
                Toast.makeText(this, "Dodano wpis.", Toast.LENGTH_SHORT).show()
                wyczyscPola()
                wypiszWydatek()
            } else{
                Toast.makeText(this, "Nie dodano wpisu.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun wyczyscPola(){
        edKwota.setText("")
        edData.setText("")
        //edKwota.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WydatekAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView(){
        edKwota = findViewById(R.id.edKwota)
        spKategoria = findViewById(R.id.spKategoria)
        edData = findViewById(R.id.edData)
        btnDodaj = findViewById(R.id.btnDodaj)
        //recyclerView = findViewById(R.id.recyclerView)

    }

    private var currentScreen: Int = R.id.menu_item_ekran_1
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_item_ekran_1 -> {
                currentScreen = R.id.menu_item_ekran_1
                updateScreen()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_item_ekran_2 -> {
                currentScreen = R.id.menu_item_ekran_2
                updateScreen()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_item_ekran_3 -> {
                currentScreen = R.id.menu_item_ekran_3
                updateScreen()
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }


    private fun updateScreen() {
        val inflater = LayoutInflater.from(this)
        val podsumowanie = findViewById<FrameLayout>(R.id.podsumowanie)
        podsumowanie.removeAllViews()

        when (currentScreen) {
            R.id.menu_item_ekran_1 -> {
                inflater.inflate(R.layout.start, podsumowanie, true)
                initView()

                btnDodaj.setOnClickListener { dodajW() }
                supportActionBar?.title = getString(R.string.ekran_1)

                /////////////////////////////////////////////////////////

                val kategoria = resources.getStringArray(R.array.kategoria)
                val spinner = findViewById<Spinner>(R.id.spKategoria)
                if (spinner != null) {
                    val adapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, kategoria)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {
                            Toast.makeText(this@MainActivity,
                                getString(R.string.selected_item) + " " +
                                        "" + kategoria[position], Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // write code to perform some action
                        }
                    }
                }


            }
            R.id.menu_item_ekran_2 -> {
                inflater.inflate(R.layout.statystyki, podsumowanie, true)
                supportActionBar?.title = getString(R.string.ekran_2)
            }
            R.id.menu_item_ekran_3 -> {
                inflater.inflate(R.layout.podsumowanie, podsumowanie, true)
                initRecyclerView()
                supportActionBar?.title = getString(R.string.ekran_3)
            }
        }
    }
}
