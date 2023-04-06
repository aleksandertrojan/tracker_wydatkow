package com.example.trwydatkow

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Podsumowanie: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.podsumowanie)
    }

    fun home(view: View) {
        view as ImageButton
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}