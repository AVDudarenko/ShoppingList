package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ProductListRepository {

	fun addProductItem(productItem: ProductItem) {

	}

	fun deleteProductItem(productItem: ProductItem) {

	}

	fun editProduct(productItem: ProductItem) {

	}

	fun getProductItem(productItemId: Int): ProductItem {
		TODO()
	}

	fun getProductList(): LiveData<List<ProductItem>> {
		TODO()
	}
}