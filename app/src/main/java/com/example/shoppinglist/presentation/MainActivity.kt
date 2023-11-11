package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

	private lateinit var viewModel: MainViewModel
	private lateinit var shopListAdapter: ShopListAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupRecyclerView()
		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		viewModel.productList.observe(this) {
			shopListAdapter.submitList(it)
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
		setupClickListener()
		setupLongClickListener()
		setupSwipeOnElement(rvProductsList)
	}

	private fun setupSwipeOnElement(rvProductsList: RecyclerView) {
		val callback = object :
			ItemTouchHelper.SimpleCallback(
				0,
				ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
			) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return false
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val item = shopListAdapter.currentList[viewHolder.adapterPosition]
				viewModel.deleteProductItem(item)
			}
		}
		val itemTouchHelper = ItemTouchHelper(callback)
		itemTouchHelper.attachToRecyclerView(rvProductsList)
	}

	private fun setupClickListener() {
		shopListAdapter.onProductClickListener = {
			Log.d("TAG", it.name)
		}
	}

	private fun setupLongClickListener() {
		shopListAdapter.onProductItemLongClickListener = {
			viewModel.changeEnableState(it)
		}
	}
}