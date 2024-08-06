package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditStokBarang : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var namaEditText: EditText
    private lateinit var hargaEditText: EditText
    private lateinit var jumlahStokEditText: EditText
    private lateinit var simpanButton: Button
    private lateinit var back:ImageView
    private var barangId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_stok_barang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)

        namaEditText=findViewById(R.id.editNamaBarang)
        hargaEditText=findViewById(R.id.editHargaBarang)
        jumlahStokEditText=findViewById(R.id.editJumlahStok)
        back=findViewById(R.id.btnBack)

        simpanButton=findViewById(R.id.btnSimpanEdit)

        back.setOnClickListener {
            val intent= Intent(this,KelolaStok::class.java)
            startActivity(intent)
            finish()
        }

        barangId = intent.getIntExtra("BARANG_ID", -1)


        if (barangId != -1) {
            val barang = databaseHelper.ambilBarang(barangId)
            barang?.let {
                namaEditText.setText(it.nama)
                hargaEditText.setText(it.harga.toString())
                jumlahStokEditText.setText(it.jumlahStok.toString())
            }
        }

        simpanButton.setOnClickListener {
            val nama = namaEditText.text.toString()
            val harga = hargaEditText.text.toString().toDouble()
            val jumlahStok = jumlahStokEditText.text.toString().toInt()

            if (barangId != -1) {
                val barang = Barang(barangId, nama, harga, jumlahStok)
                databaseHelper.updateBarang(barang)
                Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal memperbarui barang", Toast.LENGTH_SHORT).show()
            }
        }

    }
}