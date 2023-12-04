package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.DeleteProductItemUseCase
import com.example.shoppinglist.domain.EditProductItemUseCase
import com.example.shoppinglist.domain.GetProductListUseCase
import com.example.shoppinglist.domain.ProductItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
	private val getProductListUseCase: GetProductListUseCase,
	private val deleteProductItemUseCase: DeleteProductItemUseCase,
	private val editProductItemUseCase: EditProductItemUseCase
) : ViewModel() {

	val productList = getProductListUseCase.getProductList()

	fun deleteProductItem(productItem: ProductItem) {
		viewModelScope.launch {
			deleteProductItemUseCase.deleteProductItem(productItem)
		}
	}

	fun changeEnableState(productItem: ProductItem) {
		viewModelScope.launch {
			val newItem = productItem.copy(enable = !productItem.enable)
			editProductItemUseCase.editProduct(newItem)
		}
	}
}