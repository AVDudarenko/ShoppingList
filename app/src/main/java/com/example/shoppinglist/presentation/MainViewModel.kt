package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.data.ProductListRepositoryImpl
import com.example.shoppinglist.domain.DeleteProductItemUseCase
import com.example.shoppinglist.domain.EditProductItemUseCase
import com.example.shoppinglist.domain.GetProductListUseCase
import com.example.shoppinglist.domain.ProductItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

	private val repository = ProductListRepositoryImpl(application)

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