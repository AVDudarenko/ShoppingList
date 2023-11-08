package com.example.shoppinglist.domain

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

	fun getProductList(): List<ProductItem> {
		TODO()
	}
}