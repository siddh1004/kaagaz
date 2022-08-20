package com.portfolio.kaagazcamera.ui.image

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.portfolio.kaagazcamera.R
import com.portfolio.kaagazcamera.databinding.FragmentImageListBinding
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.ui.base.FragmentBase
import com.portfolio.kaagazcamera.ui.base.Success
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageListFragment : FragmentBase(R.layout.fragment_image_list) {

    @Inject
    lateinit var imageViewModel: ImageViewModel

    private lateinit var imageThumbnailAdapter: ImageThumbnailAdapter

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
        imageThumbnailAdapter = ImageThumbnailAdapter(::onImageClick)
        binding.imageRecyclerView.adapter = imageThumbnailAdapter
    }

    private fun setObservers() {
        imageViewModel.viewState.observe { viewState ->
            when (viewState) {
                is Success -> {
                    imageThumbnailAdapter.submitList(viewState.data)
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