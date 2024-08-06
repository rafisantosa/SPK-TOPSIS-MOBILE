package com.example.spktopsis

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "barang.db"
        private const val TABLE_BARANG = "barang"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_HARGA = "harga"
        private const val COLUMN_JUMLAH_STOK = "jumlah_stok"


        private const val TABLE_USER = "user"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_BARANG ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_NAMA TEXT,"
                + "$COLUMN_HARGA REAL,"
                + "$COLUMN_JUMLAH_STOK INTEGER)")


        val createUserTable = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USERNAME TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_PASSWORD TEXT)")

        db.execSQL(createTableQuery)
        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BARANG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun registerUser(username: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USER, null, contentValues)
    }
    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER, arrayOf(COLUMN_USER_ID),
            "$COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?", arrayOf(email, password),
            null, null, null, null
        )
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }

    fun getUsernameByEmail(email: String): String? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER, arrayOf(COLUMN_USERNAME),
            "$COLUMN_EMAIL=?", arrayOf(email),
            null, null, null, null
        )
        return if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            cursor.close()
            username
        } else {
            cursor.close()
            null
        }
    }

    fun tambahBarang(nama: String, harga: Double, jumlahStok: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, nama)
            put(COLUMN_HARGA, harga)
            put(COLUMN_JUMLAH_STOK, jumlahStok)
        }
        val id = db.insert(TABLE_BARANG, null, values)
        db.close()
        return id
    }

    fun ambilSemuaBarang(): ArrayList<Barang> {
        val barangList = ArrayList<Barang>()
        val selectQuery = "SELECT * FROM $TABLE_BARANG"
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                    val nama = it.getString(it.getColumnIndexOrThrow(COLUMN_NAMA))
                    val harga = it.getDouble(it.getColumnIndexOrThrow(COLUMN_HARGA))
                    val jumlahStok = it.getInt(it.getColumnIndexOrThrow(COLUMN_JUMLAH_STOK))
                    val barang = Barang(id, nama, harga, jumlahStok)
                    barangList.add(barang)
                } while (it.moveToNext())
            }
            it.close()
        }
        db.close()
        return barangList
    }

    fun hapusBarang(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_BARANG, "$COLUMN_ID=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun ambilBarang(id: Int): Barang? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_BARANG, arrayOf(COLUMN_ID, COLUMN_NAMA, COLUMN_HARGA, COLUMN_JUMLAH_STOK),
            "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null, null
        )
        cursor?.let {
            if (it.moveToFirst()) {
                val barang = Barang(
                    id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                    nama = it.getString(it.getColumnIndexOrThrow(COLUMN_NAMA)),
                    harga = it.getDouble(it.getColumnIndexOrThrow(COLUMN_HARGA)),
                    jumlahStok = it.getInt(it.getColumnIndexOrThrow(COLUMN_JUMLAH_STOK))
                )
                it.close()
                db.close()
                return barang
            }
            it.close()
        }
        db.close()
        return null
    }

    fun updateBarang(barang: Barang): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAMA, barang.nama)
            put(COLUMN_HARGA, barang.harga)
            put(COLUMN_JUMLAH_STOK, barang.jumlahStok)
        }
        return db.update(TABLE_BARANG, contentValues, "$COLUMN_ID=?", arrayOf(barang.id.toString()))
    }
}
