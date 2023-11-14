package com.example.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem
import com.google.android.material.textfield.TextInputLayout

class ProductItemFragment : Fragment() {

	private lateinit var viewModel: ProductItemViewModel

	private lateinit var tilName: TextInputLayout;
	private lateinit var etName: EditText;
	private lateinit var tilCount: TextInputLayout;
	private lateinit var etCount: EditText;
	private lateinit var btnSave: Button;

	private var screenMode = MODE_UNKNOWN
	private var productItemId = ProductItem.UNDEFINED_ID

	companion object {
		private const val SCREEN_MODE = "extra_mode"
		private const val PRODUCT_ITEM_ID = "extra_product_item_id"
		private const val MODE_EDIT = "mode_edit"
		private const val MODE_ADD = "mode_add"
		private const val MODE_UNKNOWN = ""

		fun newInstanceAddItem(): ProductItemFragment {
			return ProductItemFragment().apply {
				arguments = Bundle().apply {
					putString(SCREEN_MODE, MODE_ADD)
				}
			}
		}

		fun newInstanceEditItem(productItemId: Int): ProductItemFragment {
			return ProductItemFragment().apply {
				arguments = Bundle().apply {
					putString(SCREEN_MODE, MODE_EDIT)
					putInt(PRODUCT_ITEM_ID, productItemId)
				}

			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseParams()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		arguments
		return inflater.inflate(R.layout.fragment_shop_item, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel = ViewModelProvider(this)[ProductItemViewModel::class.java]
		initViews(view)
		initTextChangeListeners()
		launchScreenMode()
		observeViewModel()
	}

	private fun initViews(view: View) {
		tilName = view.findViewById(R.id.til_name)
		tilCount = view.findViewById(R.id.til_count)
		etName = view.findViewById(R.id.et_name)
		etCount = view.findViewById(R.id.et_count)
		btnSave = view.findViewById(R.id.btn_save)
	}

	private fun parseParams() {
		val args = requireArguments()
		if (!args.containsKey(SCREEN_MODE)) {
			throw RuntimeException("Param screen mode is empty")
		}
		val mode = args.getString(SCREEN_MODE)
		if (mode != MODE_EDIT && mode != MODE_ADD) {
			throw RuntimeException("Unknown screen mode $mode")
		}
		screenMode = mode
		if (screenMode == MODE_EDIT) {
			if (!args.containsKey(PRODUCT_ITEM_ID)) {
				throw RuntimeException("Param shop item id is empty")
			}
			productItemId = args.getInt(PRODUCT_ITEM_ID, ProductItem.UNDEFINED_ID)
		}
	}

	private fun launchEditMode() {
		viewModel.getProductItem(productItemId)
		viewModel.productItem.observe(viewLifecycleOwner) {
			etName.setText(it.name)
			etCount.setText(it.count.toString())
		}
		btnSave.setOnClickListener {
			viewModel.editProductItem(etName.text?.toString(), etCount.text?.toString())
		}

	}

	private fun launchAddMode() {
		btnSave.setOnClickListener {
			viewModel.addProductItem(etName.text?.toString(), etCount.text?.toString())
		}
	}

	private fun initTextChangeListeners() {
		etName.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				viewModel.resetErrorInputName()
			}

			override fun afterTextChanged(s: Editable?) {
			}
		})
		etCount.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				viewModel.resetErrorInputCount()
			}

			override fun afterTextChanged(s: Editable?) {
			}
		})
	}

	private fun launchScreenMode() {
		when (screenMode) {
			MODE_EDIT -> launchEditMode()
			MODE_ADD -> launchAddMode()
		}
	}

	private fun observeViewModel() {
		viewModel.isErrorInputCount.observe(viewLifecycleOwner) {
			val message = if (it) {
				getString(R.string.error_input_count)
			} else {
				null
			}
			tilCount.error = message
		}

		viewModel.isErrorInputName.observe(viewLifecycleOwner) {
			val message = if (it) {
				getString(R.string.error_input_name)
			} else {
				null
			}
			tilName.error = message
		}
		viewModel.isReadyToCloseScreen.observe(viewLifecycleOwner) {
			activity?.onBackPressedDispatcher
		}
	}
}