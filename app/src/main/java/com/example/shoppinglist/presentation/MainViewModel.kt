package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ProductListRepositoryImpl
import com.example.shoppinglist.domain.DeleteProductItemUseCase
import com.example.shoppinglist.domain.EditProductItemUseCase
import com.example.shoppinglist.domain.GetProductListUseCase
import com.example.shoppinglist.domain.ProductItem

class MainViewModel : ViewModel() {

	private val repository = ProductListRepositoryImpl

	private val getProductListUseCase = GetProductListUseCase(repository)
	private val deleteProductItemUseCase = DeleteProductItemUseCase(repository)
	private val editProductItemUseCase = EditProductItemUseCase(repository)

	val productList = getProductListUseCase.getProductList()

	fun deleteProductItem(productItem: ProductItem) {
		deleteProductItemUseCase.deleteProductItem(productItem)
	}

	fun changeEnableState(productItem: ProductItem) {
		val newItem = productItem.copy(enable = !productItem.enable)
		editProductItemUseCase.editProduct(newItem)
	}

}