package com.portfolio.kaagazcamera.data.repository.image

import com.portfolio.kaagazcamera.data.database.dao.ImageDao
import com.portfolio.kaagazcamera.data.database.model.ImageEntity
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.domain.repository.image.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao
) : ImageRepository {
    override suspend fun getImages() = imageDao.getImages().map { it.mapToDomainModel() }

    override suspend fun getImagesBasedOnAlbum(album: String) =
        imageDao.getImagesBasedOnAlbum(album).map { it.mapToDomainModel() }

    override suspend fun save(image: Image) {
        imageDao.saveImage(
            ImageEntity(
                fileName = image.fileName,
                album = image.album
            )
        )
    }
}