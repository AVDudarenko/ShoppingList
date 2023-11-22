package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ProductListRepository {

	suspend fun addProductItem(productItem: ProductItem) {

	}

	suspend fun deleteProductItem(productItem: ProductItem) {

	}

	suspend fun editProduct(productItem: ProductItem) {

	}

	suspend fun getProductItem(productItemId: Int): ProductItem {
		TODO()
	}

	fun getProductList(): LiveData<List<ProductItem>> {
		TODO()
	}
}