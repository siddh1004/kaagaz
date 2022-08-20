package com.portfolio.kaagazcamera.data.di

import android.content.Context
import com.portfolio.kaagazcamera.data.database.AppDatabase
import com.portfolio.kaagazcamera.data.database.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideImageDao(db: AppDatabase): ImageDao {
        return db.imageDao()
    }
}