package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem

class MainActivity : AppCompatActivity() {

	private lateinit var viewModel: MainViewModel
	private lateinit var shopListAdapter: ShopListAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupRecyclerView()
		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		viewModel.productList.observe(this) {
			shopListAdapter.productsList = it
		}
	}

	private fun setupRecyclerView() {
		val rvProductsList = findViewById<RecyclerView>(R.id.rvProductsList)
		with(rvProductsList) {
			shopListAdapter = ShopListAdapter()
			adapter = shopListAdapter
			rvProductsList.recycledViewPool.setMaxRecycledViews(
				ShopListAdapter.VIEW_TYPE_ENABLE,
				ShopListAdapter.MAX_POOL_SIZE
			)
			rvProductsList.recycledViewPool.setMaxRecycledViews(
				ShopListAdapter.VIEW_TYPE_DISABLE,
				ShopListAdapter.MAX_POOL_SIZE
			)
		}
		shopListAdapter.onProductItemLongClickListener =
			object : ShopListAdapter.OnProductItemLongClickListener {
				override fun onProductItemLongClick(productItem: ProductItem) {
					viewModel.changeEnableState(productItem)
				}
			}
	}
}