package com.portfolio.kaagazcamera.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.kaagazcamera.databinding.ListItemAlbumBinding
import com.portfolio.kaagazcamera.domain.model.Album

class AlbumAdapter(private val onClick: (Album) -> Unit) :
    ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback) {

    class AlbumViewHolder(
        private val binding: ListItemAlbumBinding,
        private val onClick: (Album) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Album) {
            binding.apply {
                albumContainer.setOnClickListener { onClick.invoke(item) }
                nameTextView.text = item.name
                countTextView.text = "${item.totalImages} Images"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            ListItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }
}

object AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}