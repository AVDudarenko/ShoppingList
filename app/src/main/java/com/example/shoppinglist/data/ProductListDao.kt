package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductListDao {

	@Query("SELECT * FROM product_items")
	fun getProductList(): LiveData<List<ProductItemDbModel>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addProductItem(productItemDBModel: ProductItemDbModel)

	@Query("DELETE FROM product_items WHERE id=:productItemId")
	fun deleteProductItem(productItemId: Int)

	@Query("SELECT * FROM product_items WHERE id=:productItemId LIMIT 1")
	fun getProductItem(productItemId: Int): ProductItemDbModel

}