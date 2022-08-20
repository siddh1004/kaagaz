package com.portfolio.kaagazcamera.domain.interaction.album

import com.portfolio.kaagazcamera.domain.model.Album
import com.portfolio.kaagazcamera.domain.repository.image.ImageRepository
import javax.inject.Inject

class GetAlbumUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : GetAlbumUseCase {
    override suspend fun invoke(): List<Album> {
        val albumList = mutableListOf<Album>()
        for (groupItem in imageRepository.getImages().groupBy { it.album }) {
            albumList.add(
                Album(
                    name = groupItem.key,
                    totalImages = groupItem.value.count()
                )
            )
        }
        return albumList
    }
}