package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Hasil_keputusan : AppCompatActivity() {

    private lateinit var hasil:TextView

    private lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_keputusan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val sortedResults = intent.getParcelableArrayListExtra<PairParcelable>("sortedResults")


        hasil=findViewById(R.id.hasilKeputusan)
        back=findViewById(R.id.btnBack)

//        if (sortedResults != null) {
//            val hasilText = sortedResults.joinToString(separator = "\n") { result ->
//                val jumlahStokTambahan = (result.skor * 10).toInt() // Misalnya, tambahkan 10% dari nilai preferensi
//                "${result.barang.nama}: ${result.skor}, Stok yang harus ditambahkan: $jumlahStokTambahan"
//            }
//            hasil.text = hasilText
//        }

        back.setOnClickListener {
            val intent=Intent(this,SpkStok::class.java)
            startActivity(intent)
            finish()
        }
    }
}