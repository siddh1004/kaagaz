package com.portfolio.kaagazcamera.domain.di

import com.portfolio.kaagazcamera.domain.interaction.album.GetAlbumUseCase
import com.portfolio.kaagazcamera.domain.interaction.album.GetAlbumUseCaseImpl
import com.portfolio.kaagazcamera.domain.interaction.image.ImageUseCase
import com.portfolio.kaagazcamera.domain.interaction.image.ImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractionModule {

    @Binds
    abstract fun bindImageUseCase(
        imageUseCaseImpl: ImageUseCaseImpl
    ): ImageUseCase

    @Binds
    abstract fun bindAlbumUseCase(
        albumUseCaseImpl: GetAlbumUseCaseImpl
    ): GetAlbumUseCase
}