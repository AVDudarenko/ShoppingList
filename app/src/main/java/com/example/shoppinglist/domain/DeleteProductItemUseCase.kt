package com.example.shoppinglist.domain

class DeleteProductItemUseCase(private val productListRepository: ProductListRepository) {

	fun deleteProductItem(productItem: ProductItem) {
		productListRepository.deleteProductItem(productItem)
	}
}
