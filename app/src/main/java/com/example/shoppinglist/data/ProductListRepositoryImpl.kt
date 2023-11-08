package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ProductItem
import com.example.shoppinglist.domain.ProductListRepository
import kotlin.RuntimeException

object ProductListRepositoryImpl : ProductListRepository {

	private val productList = mutableListOf<ProductItem>()
	private var autoIncrementId = 0

	override fun addProductItem(productItem: ProductItem) {
		if (productItem.id == ProductItem.UNDEFINED_ID) {
			productItem.id = autoIncrementId++
		}
		productList.add(productItem)
	}

	override fun deleteProductItem(productItem: ProductItem) {
		productList.remove(productItem)
	}

	override fun editProduct(productItem: ProductItem) {
		val oldProductItem = getProductItem(productItem.id)
		productList.remove(oldProductItem)
		addProductItem(productItem)
	}

	override fun getProductItem(productItemId: Int): ProductItem {
		return productList.find { it.id == productItemId }
			?: throw RuntimeException("Element with id $productItemId not found")
	}

	override fun getProductList(): List<ProductItem> {
		return productList.toList()
	}
}