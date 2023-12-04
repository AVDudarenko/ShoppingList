package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglist.domain.ProductItem
import com.example.shoppinglist.domain.ProductListRepository
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
	private val productListDao: ProductListDao,
	private val mapper: ProductListMapper
) : ProductListRepository {

	override suspend fun addProductItem(productItem: ProductItem) {
		productListDao.addProductItem(mapper.mapEntityToDbModel(productItem))
	}

	override suspend fun deleteProductItem(productItem: ProductItem) {
		productListDao.deleteProductItem(productItem.id)
	}

	override suspend fun editProduct(productItem: ProductItem) {
		productListDao.addProductItem(mapper.mapEntityToDbModel(productItem))
	}

	override suspend fun getProductItem(productItemId: Int): ProductItem {
		val dbModel = productListDao.getProductItem(productItemId)
		return mapper.mapDbModelToEntity(dbModel)
	}


	//MediatorLiveData get some event from another LD, and react to them.
	override fun getProductList(): LiveData<List<ProductItem>> =
		MediatorLiveData<List<ProductItem>>().apply {
			addSource(productListDao.getProductList()) {
				value = mapper.mapListDbModelToListEntity(it)
			}
		}
}