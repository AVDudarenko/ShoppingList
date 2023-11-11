package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	val tvProductType = view.findViewById<TextView>(R.id.tvProductType)!!
	val tvProductCount = view.findViewById<TextView>(R.id.tvProductCount)!!
}