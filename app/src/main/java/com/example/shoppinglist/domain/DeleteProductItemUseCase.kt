package com.example.shoppinglist.domain

import javax.inject.Inject

class DeleteProductItemUseCase @Inject constructor(
	private val productListRepository: ProductListRepository
) {

	suspend fun deleteProductItem(productItem: ProductItem) {
		productListRepository.deleteProductItem(productItem)
	}
}
