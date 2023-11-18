package com.example.projeto_final_mobile.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.R

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.itemtTextViewFragment)
}