package com.example.shoppinglist.domain

import javax.inject.Inject

class AddProductItemUseCase @Inject constructor(
	private val productListRepository: ProductListRepository
) {

	suspend fun addProductItem(productItem: ProductItem) {
		productListRepository.addProductItem(productItem)
	}
}