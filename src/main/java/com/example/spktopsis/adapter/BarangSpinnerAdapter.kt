package com.example.spktopsis.adapter

import com.example.spktopsis.Barang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatSpinner
import android.widget.ArrayAdapter

class BarangSpinnerAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val barangList: List<Barang>
) : ArrayAdapter<Barang>(context, layoutResource, barangList) {

    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        val barang = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = barang?.nama
        return view
    }
}
