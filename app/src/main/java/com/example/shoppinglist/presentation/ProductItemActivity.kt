package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem

class ProductItemActivity : AppCompatActivity(), ProductItemFragment.OnEditingFinishListener {

	private var screenMode = MODE_UNKNOWN
	private var productItemId = ProductItem.UNDEFINED_ID

	companion object {
		private const val EXTRA_SCREEN_MODE = "extra_mode"
		private const val EXTRA_PRODUCT_ITEM_ID = "extra_product_item_id"
		private const val MODE_EDIT = "mode_edit"
		private const val MODE_ADD = "mode_add"
		private const val MODE_UNKNOWN = ""

		fun newIntentAddItem(context: Context): Intent {
			val intent = Intent(context, ProductItemActivity::class.java)
			intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
			return intent
		}

		fun newIntentEditItem(context: Context, productItemId: Int): Intent {
			val intent = Intent(context, ProductItemActivity::class.java)
			intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
			intent.putExtra(EXTRA_PRODUCT_ITEM_ID, productItemId)
			return intent
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product_item)
		parseIntent()
		if (savedInstanceState == null) {
			launchScreenMode()
		}
	}

	private fun parseIntent() {
		if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
			throw RuntimeException("Param screen mode is empty")
		}
		val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
		if (mode != MODE_EDIT && mode != MODE_ADD) {
			throw RuntimeException("Unknown screen mode $mode")
		}
		screenMode = mode
		if (screenMode == MODE_EDIT) {
			if (!intent.hasExtra(EXTRA_PRODUCT_ITEM_ID)) {
				throw RuntimeException("Param shop item id is empty")
			}
			productItemId = intent.getIntExtra(EXTRA_PRODUCT_ITEM_ID, ProductItem.UNDEFINED_ID)
		}
	}

	private fun launchScreenMode() {
		val fragment = when (screenMode) {
			MODE_EDIT -> ProductItemFragment.newInstanceEditItem(productItemId)
			MODE_ADD -> ProductItemFragment.newInstanceAddItem()
			else -> throw RuntimeException("Unknown screen mode $screenMode")
		}
		supportFragmentManager.beginTransaction()
			.replace(R.id.product_item_container, fragment)
			.commit()
	}

	override fun onEditingFinish() {
		Toast.makeText(this@ProductItemActivity, "Success", Toast.LENGTH_SHORT).show()
		finish()
	}
}