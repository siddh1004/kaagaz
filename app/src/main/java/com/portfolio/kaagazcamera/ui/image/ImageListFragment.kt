package com.portfolio.kaagazcamera.ui.image

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.portfolio.kaagazcamera.R
import com.portfolio.kaagazcamera.databinding.FragmentImageListBinding
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.ui.base.FragmentBase
import com.portfolio.kaagazcamera.ui.base.Loading
import com.portfolio.kaagazcamera.ui.base.Success
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageListFragment : FragmentBase(R.layout.fragment_image_list) {

    @Inject
    lateinit var imageViewModel: ImageViewModel

    private lateinit var imageAdapter: ImageAdapter

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val navArgs: ImageListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setBindings(view)
        setAdapter()
        setObservers()
    }

    private fun loadData() {
        imageViewModel.getImages(navArgs.album)
    }

    private fun setBindings(view: View) {
        _binding = FragmentImageListBinding.bind(view)
    }

    private fun setAdapter() {
        imageAdapter = ImageAdapter(::onImageClick)
        binding.imageRecyclerView.adapter = imageAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setObservers() {
        imageViewModel.viewState.observe { viewState ->
            binding.progress.isVisible = viewState is Loading
            when (viewState) {
                is Success -> {
                    imageAdapter.submitList(viewState.data)
                }
                else -> {
                }
            }
        }
    }

    private fun onImageClick(image: Image) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}