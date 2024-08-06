package com.example.spktopsis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spktopsis.Barang
import com.example.spktopsis.R

class HomeAdapter (private val barangList: ArrayList<Barang>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_stok_home, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return barangList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val barang = barangList[position]
        holder.namaTextView.text = barang.nama
        holder.hargaTextView.text = "Rp. ${barang.harga}"
        holder.jumlahStokTextView.text = "${barang.jumlahStok} pcs"
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.namaBarangHome)
        val hargaTextView: TextView = itemView.findViewById(R.id.hargaBarangHome)
        val jumlahStokTextView: TextView = itemView.findViewById(R.id.stokBarangHome)
    }
}