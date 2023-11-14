package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ProductItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var productItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        productItemContainer = findViewById(R.id.productItemContainer)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.productList.observe(this) {
            shopListAdapter.submitList(it)
        }
        val btnAddProductItem = findViewById<FloatingActionButton>(R.id.btnAddProductItem)
        btnAddProductItem.setOnClickListener {
            if (isVerticalMode()) {
                val intent = ProductItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ProductItemFragment.newInstanceAddItem())
            }
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
            if (isVerticalMode()) {
                val intent = ProductItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ProductItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onProductItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun isVerticalMode(): Boolean {
        return productItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.productItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onEditingFinish() {
        finish()
    }
}