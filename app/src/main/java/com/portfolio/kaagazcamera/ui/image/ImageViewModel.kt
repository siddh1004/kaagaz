package com.portfolio.kaagazcamera.ui.image

import com.portfolio.kaagazcamera.domain.interaction.image.ImageUseCase
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.ui.base.Success
import com.portfolio.kaagazcamera.ui.base.ViewModelBase
import javax.inject.Inject

class ImageViewModel @Inject constructor(
    private val imageUseCase: ImageUseCase
) : ViewModelBase<List<Image>>() {
    fun getImages(album: String? = null) = executeUseCase {
        if (album.isNullOrEmpty()) {
            _viewState.value = Success(imageUseCase.getImages())
        } else {
            _viewState.value = Success(imageUseCase.getImagesBasedOnAlbum(album))
        }
    }

    fun saveImage(image: Image) = executeUseCase {
        imageUseCase.saveImage(image)
    }
}