package com.example.trackerv2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StatystykiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statystyki)

        val btnPowrot: Button = findViewById(R.id.btnPowrót)
        btnPowrot.setOnClickListener {
            finish()
        }
    }
}