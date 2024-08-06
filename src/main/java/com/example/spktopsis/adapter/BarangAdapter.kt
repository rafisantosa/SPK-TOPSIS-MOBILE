package com.example.spktopsis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spktopsis.Barang
import com.example.spktopsis.R

class BarangAdapter(private val barangList: ArrayList<Barang>,
                    private val editClickListener: (Barang) -> Unit,
                    private val deleteClickListener: (Barang) -> Unit) :
    RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_kelola_stok, parent, false)
        return BarangViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        holder.namaTextView.text = barang.nama
        holder.hargaTextView.text = "Rp. ${barang.harga}"
        holder.jumlahStokTextView.text = "${barang.jumlahStok} pcs"
        holder.editButton.setOnClickListener { editClickListener(barang) }
        holder.deleteButton.setOnClickListener { deleteClickListener(barang) }
    }

    override fun getItemCount(): Int {
        return barangList.size
    }

    class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaTextView: TextView = itemView.findViewById(R.id.rvNBKelola)
        val hargaTextView: TextView = itemView.findViewById(R.id.rvHargaKelola)
        val jumlahStokTextView: TextView = itemView.findViewById(R.id.rvStokKelola)
        val editButton: ImageView = itemView.findViewById(R.id.edit)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete)
    }
}
