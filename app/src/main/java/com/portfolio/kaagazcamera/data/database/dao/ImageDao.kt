package com.portfolio.kaagazcamera.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portfolio.kaagazcamera.data.database.ImageTableName
import com.portfolio.kaagazcamera.data.database.model.ImageEntity

@Dao
abstract class ImageDao {
    @Query("SELECT * FROM $ImageTableName")
    abstract suspend fun getImages(): List<ImageEntity>

    @Query("SELECT * FROM $ImageTableName where album = :album")
    abstract suspend fun getImagesBasedOnAlbum(album: String): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveImage(image: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveImages(images: List<ImageEntity>)

    @Delete
    abstract suspend fun deleteImage(image: ImageEntity)
}