package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_items")
data class ProductItemDbModel(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val name: String,
	val count: Double,
	val enable: Boolean,
) {
}