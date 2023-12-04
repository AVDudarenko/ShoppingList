package com.example.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.presentation.MainViewModel
import com.example.shoppinglist.presentation.ProductItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

	@IntoMap
	@ViewModelKey(MainViewModel::class)
	@Binds
	fun bindMainViewModel(imp: MainViewModel): ViewModel

	@IntoMap
	@ViewModelKey(ProductItemViewModel::class)
	@Binds
	fun bindProductItemViewModel(imp: ProductItemViewModel): ViewModel

}