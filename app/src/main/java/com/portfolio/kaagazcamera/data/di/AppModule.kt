package com.portfolio.kaagazcamera.data.di

import com.portfolio.kaagazcamera.data.helper.ContextProvider
import com.portfolio.kaagazcamera.data.helper.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCoroutineContext(): ContextProvider {
        return CoroutineContextProvider()
    }
}