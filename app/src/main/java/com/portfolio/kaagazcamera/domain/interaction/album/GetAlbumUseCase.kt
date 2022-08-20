package com.portfolio.kaagazcamera.domain.interaction.album

import com.portfolio.kaagazcamera.domain.model.Album

interface GetAlbumUseCase {
    suspend operator fun invoke(): List<Album>
}