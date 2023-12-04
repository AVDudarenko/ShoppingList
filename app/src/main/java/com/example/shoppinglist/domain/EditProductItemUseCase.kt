package com.example.shoppinglist.domain

import javax.inject.Inject

class EditProductItemUseCase @Inject constructor(
	private val productListRepository: ProductListRepository
) {

	suspend fun editProduct(productItem: ProductItem) {
		productListRepository.editProduct(productItem)
	}

}