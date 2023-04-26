package com.example.trackerv2



class Wydatek {
    var id = 0
    var kwota: Double
    var kategoria: String
    var data: String

    internal constructor(kwota: Double, kategoria: String, data: String) {
        this.kwota = kwota
        this.kategoria = kategoria
        this.data = data
    }
    internal constructor(id: Int, kwota: Double, kategoria: String, data: String) {
        this.id = id
        this.kwota = kwota
        this.kategoria = kategoria
        this.data = data
    }
}

