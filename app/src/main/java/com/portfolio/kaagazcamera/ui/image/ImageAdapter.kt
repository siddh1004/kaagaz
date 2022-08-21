package com.portfolio.kaagazcamera.ui.image

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.kaagazcamera.databinding.ListItemImageBinding
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.ui.extensions.loadUri

class ImageAdapter(private val onClick: (Image) -> Unit) :
    ListAdapter<Image, ImageAdapter.ImageViewHolder>(ImageDiffCallback) {

    class ImageViewHolder(
        private val binding: ListItemImageBinding,
        private val onClick: (Image) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.apply {
                try {
                    image.loadUri(item.uri)
                } catch (ex: Exception) {
                    Log.e("App", "Photo load failed: ${ex.message}", ex)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ListItemImageBinding.inflate(
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