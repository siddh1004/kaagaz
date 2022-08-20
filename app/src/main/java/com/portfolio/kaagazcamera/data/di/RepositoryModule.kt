package com.portfolio.kaagazcamera.data.di

import com.portfolio.kaagazcamera.data.repository.image.ImageRepositoryImpl
import com.portfolio.kaagazcamera.domain.repository.image.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}