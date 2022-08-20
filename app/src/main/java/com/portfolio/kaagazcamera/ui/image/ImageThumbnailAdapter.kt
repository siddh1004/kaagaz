package com.portfolio.kaagazcamera.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.kaagazcamera.databinding.ListItemImageThumbnailBinding
import com.portfolio.kaagazcamera.domain.model.Image

class ImageThumbnailAdapter(private val onClick: (Image) -> Unit) :
    ListAdapter<Image, ImageThumbnailAdapter.TokenViewHolder>(TokenDiffCallback) {

    class TokenViewHolder(
        private val binding: ListItemImageThumbnailBinding,
        private val onClick: (Image) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.apply {
//                thumbnailImageView.setImageBitmap(item.bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TokenViewHolder {
        return TokenViewHolder(
            ListItemImageThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: TokenViewHolder, position: Int) {
        val token = getItem(position)
        holder.bind(token)
    }
}

object TokenDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.fileName == newItem.fileName
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}