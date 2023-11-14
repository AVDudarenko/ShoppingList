package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem

class ProductItemActivity : AppCompatActivity() {

//	private lateinit var viewModel: ProductItemViewModel

//	private lateinit var tilName: TextInputLayout;
//	private lateinit var etName: EditText;
//	private lateinit var tilCount: TextInputLayout;
//	private lateinit var etCount: EditText;
//	private lateinit var btnSave: Button;

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
//		viewModel = ViewModelProvider(this)[ProductItemViewModel::class.java]
//		initViews()
//		initTextChangeListeners()
		launchScreenMode()
//		observeViewModel()
	}


//	private fun initViews() {
//		tilName = findViewById(R.id.tilName)
//		tilCount = findViewById(R.id.tilCount)
//		etName = findViewById(R.id.etName)
//		etCount = findViewById(R.id.etCount)
//		btnSave = findViewById(R.id.btnSave)
//	}

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

//	private fun launchEditMode() {
//		viewModel.getProductItem(productItemId)
//		viewModel.productItem.observe(this) {
//			etName.setText(it.name)
//			etCount.setText(it.count.toString())
//		}
//		btnSave.setOnClickListener {
//			viewModel.editProductItem(etName.text?.toString(), etCount.text?.toString())
//		}
//
//	}

//	private fun launchAddMode() {
//		btnSave.setOnClickListener {
//			viewModel.addProductItem(etName.text?.toString(), etCount.text?.toString())
//		}
//	}

//	private fun initTextChangeListeners() {
//		etName.addTextChangedListener(object : TextWatcher {
//			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//			}
//
//			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//				viewModel.resetErrorInputName()
//			}
//
//			override fun afterTextChanged(s: Editable?) {
//			}
//		})
//		etCount.addTextChangedListener(object : TextWatcher {
//			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//			}
//
//			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//				viewModel.resetErrorInputCount()
//			}
//
//			override fun afterTextChanged(s: Editable?) {
//			}
//		})
//	}

	private fun launchScreenMode() {
		val fragment = when (screenMode) {
			MODE_EDIT -> ProductItemFragment.newInstanceEditItem(productItemId)
			MODE_ADD -> ProductItemFragment.newInstanceAddItem()
			else -> throw RuntimeException("Unknown screen mode $screenMode")
		}
		supportFragmentManager.beginTransaction()
			.add(R.id.product_item_container, fragment)
			.commit()
	}

//	private fun observeViewModel() {
//		viewModel.isErrorInputCount.observe(this) {
//			val message = if (it) {
//				getString(R.string.error_input_count)
//			} else {
//				null
//			}
//			tilCount.error = message
//		}
//
//		viewModel.isErrorInputName.observe(this) {
//			val message = if (it) {
//				getString(R.string.error_input_name)
//			} else {
//				null
//			}
//			tilName.error = message
//		}
//		viewModel.isReadyToCloseScreen.observe(this) {
//			finish()
//		}
//	}
}