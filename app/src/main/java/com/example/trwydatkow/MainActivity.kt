package com.example.trwydatkow


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /////////////////////////////
        //val myEditText = findViewById<EditText>(R.id.home_ukwota)
        val decimalEditText = findViewById<TextView>(R.id.home_ureszta)

        //val myDecimal = myEditText.text.toString().toBigDecimal()

        val decimalValue = BigDecimal("10.9")
        decimalEditText.setText(decimalValue.toString())

        ////////////////////
        val dateField = findViewById<EditText>(R.id.home_udata)

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
        }

        ///////////////////

        val kategoria = resources.getStringArray(R.array.kategoria)
        val spinner = findViewById<Spinner>(R.id.home_ukat)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, kategoria)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@MainActivity,
                        getString(R.string.selected_item) + " " +
                               "" + kategoria[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }


    }
    fun podsumowanie(view: View) {
        view as ImageButton
        val intent = Intent(this, Podsumowanie::class.java)
        startActivity(intent)
    }

}

