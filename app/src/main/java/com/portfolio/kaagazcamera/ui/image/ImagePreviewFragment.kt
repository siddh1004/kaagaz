package com.portfolio.kaagazcamera.ui.image

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.portfolio.kaagazcamera.R
import com.portfolio.kaagazcamera.databinding.FragmentImagePreviewBinding
import com.portfolio.kaagazcamera.ui.base.FragmentBase
import com.portfolio.kaagazcamera.ui.base.Loading
import com.portfolio.kaagazcamera.ui.base.Success
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagePreviewFragment : FragmentBase(R.layout.fragment_image_preview) {

    @Inject
    lateinit var imageViewModel: ImageViewModel

    private lateinit var imagePreviewAdapter: ImagePreviewAdapter

    private var _binding: FragmentImagePreviewBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val navArgs: ImagePreviewFragmentArgs by navArgs()

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
        _binding = FragmentImagePreviewBinding.bind(view)
    }

    private fun setAdapter() {
        imagePreviewAdapter = ImagePreviewAdapter()
        binding.viewPager.adapter = imagePreviewAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { _, _ -> }.attach()
    }

    private fun setObservers() {
        imageViewModel.viewState.observe { viewState ->
            binding.progress.isVisible = viewState is Loading
            when (viewState) {
                is Success -> {
                    imagePreviewAdapter.submitList(viewState.data)
                    binding.viewPager.setCurrentItem(navArgs.position, false)
                }
                else -> {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}