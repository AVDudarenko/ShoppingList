package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.AddProductItemUseCase
import com.example.shoppinglist.domain.EditProductItemUseCase
import com.example.shoppinglist.domain.GetProductItemUseCase
import com.example.shoppinglist.domain.ProductItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductItemViewModel @Inject constructor(
	private val getProductItemUseCase: GetProductItemUseCase,
	private val addProductItemUseCase: AddProductItemUseCase,
	private val editProductItemUseCase: EditProductItemUseCase
) : ViewModel() {

	private val _isErrorInputName = MutableLiveData<Boolean>()
	val isErrorInputName: LiveData<Boolean>
		get() = _isErrorInputName

	private val _isErrorInputCount = MutableLiveData<Boolean>()
	val isErrorInputCount: LiveData<Boolean>
		get() = _isErrorInputCount

	private val _productItem = MutableLiveData<ProductItem>()
	val productItem: LiveData<ProductItem>
		get() = _productItem

	private val _isReadyToCloseScreen = MutableLiveData<Unit>()
	val isReadyToCloseScreen: LiveData<Unit>
		get() = _isReadyToCloseScreen

	fun getProductItem(productItemId: Int) {
		viewModelScope.launch {
			val item = getProductItemUseCase.getProductItem(productItemId)
			_productItem.value = item
		}
	}

	fun addProductItem(inputName: String?, inputCount: String?) {
		val name = parseName(inputName)
		val count = parseCount(inputCount)
		val fieldsValid = validateInput(name, count)
		if (fieldsValid) {
			viewModelScope.launch {
				val productItem = ProductItem(name, count, true)
				addProductItemUseCase.addProductItem(productItem)
				closeScreen()
			}
		}
	}

	fun editProductItem(inputName: String?, inputCount: String?) {
		val name = parseName(inputName)
		val count = parseCount(inputCount)
		val fieldsValid = validateInput(name, count)
		if (fieldsValid) {
			_productItem.value?.let {
				viewModelScope.launch {
					val item = it.copy(name = name, count = count)
					editProductItemUseCase.editProduct(item)
					closeScreen()
				}
			}
		}
	}

	private fun parseName(inputName: String?): String {
		return inputName?.trim() ?: ""
	}

	private fun parseCount(inputCount: String?): Double {
		return try {
			inputCount?.trim()?.toDouble() ?: 0.0
		} catch (e: Exception) {
			0.0
		}
	}

	private fun validateInput(name: String, count: Double): Boolean {
		var result = true
		if (name.isBlank()) {
			_isErrorInputName.value = true
			result = false
		}
		if (count <= 0.0) {
			_isErrorInputCount.value = true
			result = false
		}
		return result
	}

	fun resetErrorInputName() {
		_isErrorInputName.value = false
	}

	fun resetErrorInputCount() {
		_isErrorInputCount.value = false
	}

	private fun closeScreen() {
		_isReadyToCloseScreen.value = Unit
	}
}