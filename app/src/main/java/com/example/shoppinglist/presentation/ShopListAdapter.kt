package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ProductItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

	interface OnProductItemLongClickListener {
		fun onProductItemLongClick(productItem: ProductItem)
	}

	companion object {
		const val VIEW_TYPE_ENABLE = 0
		const val VIEW_TYPE_DISABLE = 1
		const val MAX_POOL_SIZE = 15
	}

	var productsList = listOf<ProductItem>()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	var onProductItemLongClickListener: OnProductItemLongClickListener? = null

	class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val tvProductType = view.findViewById<TextView>(R.id.tvProductType)!!
		val tvProductCount = view.findViewById<TextView>(R.id.tvProductCount)!!
	}

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

	override fun getItemCount(): Int {
		return productsList.size
	}

	override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
		val productItem = productsList[position]
		holder.tvProductType.text = productItem.name
		holder.tvProductCount.text = productItem.count.toString()
		holder.itemView.setOnLongClickListener {
			onProductItemLongClickListener?.onProductItemLongClick(productItem)
			true
		}
	}

	override fun getItemViewType(position: Int): Int {
		val item = productsList[position]
		return if (item.enable) {
			VIEW_TYPE_ENABLE
		} else {
			VIEW_TYPE_DISABLE
		}
	}
}