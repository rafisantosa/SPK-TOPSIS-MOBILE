package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spktopsis.adapter.BarangAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelolaStok : AppCompatActivity() {
    private lateinit var inputStok: FloatingActionButton
    private lateinit var back:ImageView

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var barangAdapter: BarangAdapter
    private var barangList = ArrayList<Barang>()
    private lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_stok)

        databaseHelper = DatabaseHelper(this)
        recyclerView=findViewById(R.id.rvBarangKelola)
        setupRecyclerView()

        back=findViewById(R.id.btnBack)
        inputStok=findViewById(R.id.inputStok)

        back.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        inputStok.setOnClickListener{
            val intent= Intent(this,InputStok::class.java)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()
        loadBarang()
    }
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        barangAdapter = BarangAdapter(barangList,
            editClickListener = { barang -> editBarang(barang) },
            deleteClickListener = { barang -> hapusBarang(barang) })
        recyclerView.adapter = barangAdapter
    }

    private fun loadBarang() {
        barangList.clear()
        barangList.addAll(databaseHelper.ambilSemuaBarang())
        barangAdapter.notifyDataSetChanged()
    }

    private fun editBarang(barang: Barang) {
        // Implementasi logika untuk mengedit barang
        // Anda dapat memulai Intent ke EditBarangActivity dan meneruskan ID barang sebagai extra
        val intent = Intent(this, EditStokBarang::class.java)
        intent.putExtra("BARANG_ID", barang.id)
        startActivity(intent)
    }

    private fun hapusBarang(barang: Barang) {
        // Menghapus barang dari database menggunakan databaseHelper
        databaseHelper.hapusBarang(barang.id)
        // Memuat ulang daftar barang setelah penghapusan
        loadBarang()
    }
}