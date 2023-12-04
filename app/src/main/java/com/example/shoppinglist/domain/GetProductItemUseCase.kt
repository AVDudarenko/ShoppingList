package com.example.shoppinglist.domain

import javax.inject.Inject

class GetProductItemUseCase @Inject constructor(
	private val productListRepository: ProductListRepository
) {
	suspend fun getProductItem(productItemId: Int): ProductItem {
		return productListRepository.getProductItem(productItemId)
	}
}