package com.example.shoppinglist.domain

data class ProductItem(
	val name: String,
	val count: Double,
	val enable: Boolean,
	var id: Int = UNDEFINED_ID
) {
	companion object {
		const val UNDEFINED_ID = 0
	}
}
