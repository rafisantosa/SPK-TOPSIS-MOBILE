package com.example.spktopsis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spktopsis.adapter.BarangAdapter
import com.example.spktopsis.adapter.HomeAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var menuStok:ImageView
    private lateinit var menuSpk:ImageView
    private lateinit var namaUser:TextView
    private lateinit var logout:ImageView

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var homeAdapter: HomeAdapter
    private var barangList = ArrayList<Barang>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerView=findViewById(R.id.rvBarangHome)

        logout=findViewById(R.id.logout)
        namaUser=findViewById(R.id.namaUser)
        menuStok=findViewById(R.id.menu_stok)
        menuSpk=findViewById(R.id.menu_spk)

        val username=intent.getStringExtra("USERNAME")
        if (username != null) {
            namaUser.text = "$username"
        }

        setupRecyclerView()

        menuStok.setOnClickListener {
            val intent=Intent(this,KelolaStok::class.java)
            startActivity(intent)
        }

        menuSpk.setOnClickListener {
            val intent=Intent(this,SpkStok::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("USERNAME")
            editor.apply()

            // Setelah itu, arahkan kembali ke layar login atau halaman awal aplikasi
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Menutup activity saat ini agar pengguna tidak dapat kembali ke MainActivity setelah logout
        }

    }

    override fun onResume() {
        super.onResume()
        loadBarang()
    }

    private fun loadBarang() {
        barangList.clear()
        barangList.addAll(databaseHelper.ambilSemuaBarang())
        homeAdapter.notifyDataSetChanged()
    }
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        homeAdapter = HomeAdapter(barangList)
        recyclerView.adapter = homeAdapter
    }

}