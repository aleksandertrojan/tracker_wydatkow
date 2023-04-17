package com.example.trwydatkow

import java.util.*

data class Wydatek(
    var id: Int = autoId(),
    var kwota: Double = 0.00,
    var kategoria: String = "",
    var data: String = ""

){
    companion object
    {
        fun autoId():Int
        {
            val random = Random()
            return random.nextInt(100)
        }
    }

}