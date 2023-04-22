package com.example.trackerv2

class Wydatek {
    var id = 0
    var kwota: Double
    var kategoria: String
    internal constructor(kwota: Double, kategoria: String) {
        this.kwota = kwota
        this.kategoria = kategoria
    }
    internal constructor(id: Int, kwota: Double, kategoria: String) {
        this.id = id
        this.kwota = kwota
        this.kategoria = kategoria
    }
}

