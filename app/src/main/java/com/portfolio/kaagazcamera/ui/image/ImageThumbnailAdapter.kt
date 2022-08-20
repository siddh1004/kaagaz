package com.portfolio.kaagazcamera.ui.image

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.kaagazcamera.databinding.ListItemImageThumbnailBinding
import com.portfolio.kaagazcamera.domain.model.Image

class ImageThumbnailAdapter(private val onClick: (Image) -> Unit) :
    ListAdapter<Image, ImageThumbnailAdapter.ImageViewHolder>(ImageDiffCallback) {

    class ImageViewHolder(
        private val binding: ListItemImageThumbnailBinding,
        private val onClick: (Image) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.apply {
                try {
                    thumbnailImageView.setImageURI(Uri.parse(item.uri))
                } catch (ex: Exception) {
                    Log.e("App", "Photo load failed: ${ex.message}", ex)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ListItemImageThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }
}

object ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.fileName == newItem.fileName
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}