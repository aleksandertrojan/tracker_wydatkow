package com.example.trackerv2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class SqliteDatabase internal constructor(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        val utworzTabele = ("CREATE TABLE "
                + TABELA + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_KWOTA + " REAL,"
                + COLUMN_KATEGORIA + " TEXT" + ")")
        db.execSQL(utworzTabele)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABELA")
        onCreate(db)
    }

    fun listaWydatków(): ArrayList<Wydatek> {
        val sql = "select * from $TABELA"
        val db = this.readableDatabase
        val Lwydatkow = ArrayList<Wydatek>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val kwota = cursor.getDouble(1)
                val kategoria = cursor.getString(2)
                Lwydatkow.add(Wydatek(id, kwota, kategoria))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return Lwydatkow
    }

    fun add(wydatki: Wydatek) {
        val values = ContentValues()
        values.put(COLUMN_KWOTA, wydatki.kwota)
        values.put(COLUMN_KATEGORIA, wydatki.kategoria)
        val db = this.writableDatabase
        db.insert(TABELA, null, values)
    }

    fun update(wydatki: Wydatek) {
        val values = ContentValues()
        values.put(COLUMN_KWOTA, wydatki.kwota)
        values.put(COLUMN_KATEGORIA, wydatki.kategoria)
        val db = this.writableDatabase
        db.update(
            TABELA,
            values,
            "$COLUMN_ID = ?",
            arrayOf(wydatki.id.toString())
        )
    }

    fun delete(id: Int) {
        val db = this.writableDatabase
        db.delete(
            TABELA,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "DB"
        private const val TABELA = "Wydatki"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_KWOTA = "kwota"
        private const val COLUMN_KATEGORIA = "kategoria"
    }
}
