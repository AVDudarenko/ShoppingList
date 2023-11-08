package com.example.shoppinglist.domain

class EditProductItemUseCase(private val productListRepository: ProductListRepository) {

	fun editProduct(productItem: ProductItem) {
		productListRepository.editProduct(productItem)
	}

}