package com.example.trwydatkow


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    //Toast.makeText(this@MainActivity,
                    //    getString(R.string.selected_item) + " " +
                    //           "" + languages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.BottomNavigationView -> {
                // Obsługa kliknięcia na przycisk "Home"
                val intent = Intent(this, NowyEkranActivity::class.java)
                startActivity(intent)
            }
            /*R.id.navigation_notifications -> {
                // Obsługa kliknięcia na przycisk "Notifications"
                return true
            }
            R.id.navigation_settings -> {
                // Obsługa kliknięcia na przycisk "Settings"
                return true
            }*/
        }
        return false
    }

}

class NowyEkranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.podsumowanie)
    }
}