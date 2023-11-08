package com.example.shoppinglist.domain

class GetProductListUseCase(private val productListRepository: ProductListRepository) {

	fun getProductList(): List<ProductItem> {
		return productListRepository.getProductList()
	}
}