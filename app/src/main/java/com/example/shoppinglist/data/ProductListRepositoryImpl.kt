package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ProductItem
import com.example.shoppinglist.domain.ProductListRepository
import kotlin.random.Random

object ProductListRepositoryImpl : ProductListRepository {

	private val productListLiveData = MutableLiveData<List<ProductItem>>()
	private val productList = sortedSetOf<ProductItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
	private var autoIncrementId = 0

	init {
		for (i in 0 until 20) {
			val item = ProductItem("Name $i", i.toDouble(), Random.nextBoolean())
			addProductItem(item)
		}
	}

	override fun addProductItem(productItem: ProductItem) {
		if (productItem.id == ProductItem.UNDEFINED_ID) {
			productItem.id = autoIncrementId++
		}
		productList.add(productItem)
		updateList()
	}

	override fun deleteProductItem(productItem: ProductItem) {
		productList.remove(productItem)
		updateList()
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

	override fun getProductList(): LiveData<List<ProductItem>> {
		return productListLiveData
	}

	private fun updateList() {
		productListLiveData.postValue(productList.toList())
	}
}