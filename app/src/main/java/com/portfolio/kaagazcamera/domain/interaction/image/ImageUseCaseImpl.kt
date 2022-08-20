package com.portfolio.kaagazcamera.domain.interaction.image

import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.domain.repository.image.ImageRepository
import javax.inject.Inject

class ImageUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : ImageUseCase {
    override suspend fun getImages() = imageRepository.getImages()

    override suspend fun getImagesBasedOnAlbum(album: String) =
        imageRepository.getImagesBasedOnAlbum(album)

    override suspend fun saveImage(image: Image) = imageRepository.save(image)
}