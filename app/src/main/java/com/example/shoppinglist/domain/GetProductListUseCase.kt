package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
	private val productListRepository: ProductListRepository
) {
	fun getProductList(): LiveData<List<ProductItem>> {
		return productListRepository.getProductList()
	}
}