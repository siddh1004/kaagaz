package com.portfolio.kaagazcamera.domain.repository.image

import com.portfolio.kaagazcamera.domain.model.Image

interface ImageRepository {
    suspend fun getImages(): List<Image>
    suspend fun getImagesBasedOnAlbum(album: String): List<Image>
    suspend fun save(image: Image)
}