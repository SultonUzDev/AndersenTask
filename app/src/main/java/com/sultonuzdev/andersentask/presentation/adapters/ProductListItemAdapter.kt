package com.sultonuzdev.andersentask.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.core.utils.ImageLoader.loadImageFromInternet
import com.sultonuzdev.andersentask.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductListItemAdapter(
    private val lifecycleScope: CoroutineScope
) : ListAdapter<Product, ProductListItemAdapter.ProductViewHolder>(ProductDiffCallback()) {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.iv_product)
        val productTitle: TextView = view.findViewById(R.id.tv_product_title)
        val productSubTitle: TextView = view.findViewById(R.id.tv_product_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)

        holder.productTitle.text = product.title
        holder.productSubTitle.text = product.subtitle

        // Show placeholder
        holder.productImage.setImageResource(R.drawable.ic_place_holder)

        // Load image
        lifecycleScope.launch {
            val bitmap = loadImageFromInternet(product.image)
            if (bitmap != null) {

                holder.productImage.setImageBitmap(bitmap)
                holder.productImage.scaleType = ImageView.ScaleType.CENTER_CROP

            }
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}