package com.example.shoppinglist.domain

class DeleteProductItemUseCase(private val productListRepository: ProductListRepository) {

	suspend fun deleteProductItem(productItem: ProductItem) {
		productListRepository.deleteProductItem(productItem)
	}
}
