package com.portfolio.kaagazcamera.ui.album

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.portfolio.kaagazcamera.R
import com.portfolio.kaagazcamera.databinding.FragmentAlbumListBinding
import com.portfolio.kaagazcamera.domain.model.Album
import com.portfolio.kaagazcamera.ui.base.FragmentBase
import com.portfolio.kaagazcamera.ui.base.Loading
import com.portfolio.kaagazcamera.ui.base.Success
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlbumListFragment : FragmentBase(R.layout.fragment_album_list) {

    @Inject
    lateinit var albumViewModel: AlbumViewModel

    private lateinit var albumAdapter: AlbumAdapter

    private var _binding: FragmentAlbumListBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setBindings(view)
        setAdapter()
        setObservers()
        setEventHandlers()
    }

    private fun loadData() {
        albumViewModel.getAlbumList()
    }

    private fun setBindings(view: View) {
        _binding = FragmentAlbumListBinding.bind(view)
    }

    private fun setAdapter() {
        albumAdapter = AlbumAdapter(::onAlbumClick)
        binding.albumRecyclerView.adapter = albumAdapter
        binding.albumRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setObservers() {
        albumViewModel.viewState.observe { viewState ->
            binding.progress.isVisible = viewState is Loading

            when (viewState) {
                is Success -> {
                    albumAdapter.submitList(viewState.data)
                    binding.emptyStateTextView.isVisible = viewState.data.isEmpty()
                }
                else -> {
                }
            }
        }
    }

    private fun setEventHandlers() {
        binding.fab.setOnClickListener {
            navigateTo(AlbumListFragmentDirections.moveToCameraFragment())
        }
    }

    private fun onAlbumClick(album: Album) {
        navigateTo(AlbumListFragmentDirections.moveToImageListFragment(album.name))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}