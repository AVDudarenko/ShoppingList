package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ProductItem
import javax.inject.Inject

class ProductListMapper @Inject constructor() {

	fun mapEntityToDbModel(productItem: ProductItem): ProductItemDbModel {
		return ProductItemDbModel(
			id = productItem.id,
			name = productItem.name,
			count = productItem.count,
			enable = productItem.enable
		)
	}

	fun mapDbModelToEntity(productItemDBModel: ProductItemDbModel): ProductItem {
		return ProductItem(
			name = productItemDBModel.name,
			count = productItemDBModel.count,
			enable = productItemDBModel.enable,
			id = productItemDBModel.id
		)
	}

	fun mapListDbModelToListEntity(list: List<ProductItemDbModel>): List<ProductItem> {
		return list.map {
			mapDbModelToEntity(it)
		}
	}
}