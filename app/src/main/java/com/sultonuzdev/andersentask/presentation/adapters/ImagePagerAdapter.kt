package com.sultonuzdev.andersentask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sultonuzdev.andersentask.R
import com.sultonuzdev.andersentask.core.utils.ImageLoader.loadImageFromInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ImagePagerAdapter(
    private val imageUrls: List<String>,
    private val lifecycleScope: CoroutineScope
) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_pager_item, parent, false)
        return ImageViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        val imageUrl = imageUrls[position]
        lifecycleScope.launch {
            val bitmap = loadImageFromInternet(imageUrl)
            if (bitmap != null) {
                holder.imageView.setImageBitmap(bitmap)
                holder.imageView.scaleType = ImageView.ScaleType.CENTER_CROP


            }
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_category)
    }

}