package com.example.trwydatkow

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getIntOrNull
import java.util.function.DoubleBinaryOperator

class SQLiteHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "tracker.db"
        private const val TABELA = "tabela"
        private const val ID = "id"
        private const val KWOTA = "kwota"
        private const val KATEGORIA = "kategoria"
        private const val DATA = "data"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val baza = ("CREATE TABLE " + TABELA + "(" + ID + " INTEGER PRIMARY KEY," + KWOTA + " FLOAT," + KATEGORIA + " TEXT," + DATA + " VARCHAR2"+ ")")
        db?.execSQL("INSERT INTO TABELA(ID,KWOTA,KATEGORIA,DATA) VALUES(0,15.25,'kosmetyki','20.03.2023')")
        db?.execSQL(baza)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABELA")
        onCreate(db)
    }

    fun wstaw(std: Wydatek): Long
    {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(KWOTA, std.kwota)
        contentValues.put(KATEGORIA, std.kategoria)
        contentValues.put(DATA, std.data)

        val success = db.insert(TABELA, null, contentValues)
        db.close()
        return success
    }

    fun wszystkieWpisy(): ArrayList<Wydatek>
    {
        val lista: ArrayList<Wydatek> = ArrayList()
        val pokazwpisy = "SELECT * FROM TABELA"
        val db = this.readableDatabase

        val cursor: Cursor?

        try { cursor = db.rawQuery(pokazwpisy, null) }
        catch (e: Exception)
        {
            e.printStackTrace()
            db.execSQL(pokazwpisy)
            return ArrayList()
        }

        var id: Int
        var kwota: Double
        var kategoria:String
        var data:String

        if (cursor.moveToFirst()){
            do
            {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                kwota = cursor.getDouble(cursor.getColumnIndexOrThrow("kwota"))
                kategoria = cursor.getString(cursor.getColumnIndexOrThrow("kategoria"))
                data = cursor.getString(cursor.getColumnIndexOrThrow("data"))

                val x = Wydatek(id = id, kwota = kwota, kategoria = kategoria, data = data)
                lista.add(x)
            }
            while (cursor.moveToNext())
        }
        return lista
    }
}