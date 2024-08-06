package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InputStok : AppCompatActivity() {

    private lateinit var btnSimpan:Button
    private lateinit var edNama:EditText
    private lateinit var edHarga:EditText
    private lateinit var edStok:EditText
    private lateinit var back:ImageView

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_stok)

        databaseHelper=DatabaseHelper(this)

        btnSimpan=findViewById(R.id.btnSimpan)
        edNama=findViewById(R.id.edNamaBarang)
        edHarga=findViewById(R.id.edHargaBarang)
        edStok=findViewById(R.id.edJumlahStok)
        back=findViewById(R.id.btnBack)

        back.setOnClickListener {
            val intent= Intent(this,KelolaStok::class.java)
            startActivity(intent)
            finish()
        }

        btnSimpan.setOnClickListener {
            val nama=edNama.text.toString()
            val harga=edHarga.text.toString().toDouble()
            val stok =edStok.text.toString().toInt()

            val id=databaseHelper.tambahBarang(nama,harga,stok)

            if (id !=-1L ){
                showToast("Berhasil Menyimpan Data")
                finish()
            }else{
                showToast("Gagal Menyimpan Data")
            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}