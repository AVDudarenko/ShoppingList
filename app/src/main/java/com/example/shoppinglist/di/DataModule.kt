package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ProductListDao
import com.example.shoppinglist.data.ProductListRepositoryImpl
import com.example.shoppinglist.domain.ProductListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

	@ApplicationScope
	@Binds
	fun bindProductListRepository(impl: ProductListRepositoryImpl): ProductListRepository

	companion object {

		@ApplicationScope
		@Provides
		fun provideShopListDao(
			application: Application
		): ProductListDao {
			return AppDatabase.getInstance(application).productListDao()
		}
	}
}