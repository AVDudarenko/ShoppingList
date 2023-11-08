package com.example.shoppinglist.domain

class AddProductItemUseCase(private val productListRepository: ProductListRepository) {

	fun addProductItem(productItem: ProductItem) {
		productListRepository.addProductItem(productItem)
	}
}