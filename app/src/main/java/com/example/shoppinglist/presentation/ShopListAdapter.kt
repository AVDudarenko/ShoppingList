package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem

class ShopListAdapter :
	ListAdapter<ProductItem, ShopItemViewHolder>(ProductItemDiffCallback()) {

	companion object {
		const val VIEW_TYPE_ENABLE = 0
		const val VIEW_TYPE_DISABLE = 1
		const val MAX_POOL_SIZE = 15
	}

	var onProductItemLongClickListener: ((ProductItem) -> Unit)? = null
	var onProductClickListener: ((ProductItem) -> Unit)? = null


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
		val layout = when (viewType) {
			VIEW_TYPE_ENABLE -> R.layout.item_product_enable
			VIEW_TYPE_DISABLE -> R.layout.item_product_disable
			else -> throw RuntimeException("Unknown view type: $viewType")
		}
		val view =
			LayoutInflater.from(parent.context).inflate(
				layout,
				parent,
				false
			)
		return ShopItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
		val productItem = getItem(position)
		holder.tvProductType.text = productItem.name
		holder.tvProductCount.text = productItem.count.toString()
		holder.itemView.setOnLongClickListener {
			onProductItemLongClickListener?.invoke(productItem)
			true
		}
		holder.itemView.setOnClickListener {
			onProductClickListener?.invoke(productItem)
		}
	}

	override fun getItemViewType(position: Int): Int {
		val item = getItem(position)
		return if (item.enable) {
			VIEW_TYPE_ENABLE
		} else {
			VIEW_TYPE_DISABLE
		}
	}
}