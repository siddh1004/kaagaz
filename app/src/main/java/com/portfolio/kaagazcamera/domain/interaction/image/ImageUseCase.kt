package com.portfolio.kaagazcamera.domain.interaction.image

import com.portfolio.kaagazcamera.domain.model.Image

interface ImageUseCase {
    suspend fun getImages(): List<Image>
    suspend fun getImagesBasedOnAlbum(album: String): List<Image>
    suspend fun saveImage(image: Image)
}