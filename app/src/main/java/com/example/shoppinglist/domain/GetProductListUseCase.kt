package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetProductListUseCase(private val productListRepository: ProductListRepository) {

	fun getProductList(): LiveData<List<ProductItem>> {
		return productListRepository.getProductList()
	}
}