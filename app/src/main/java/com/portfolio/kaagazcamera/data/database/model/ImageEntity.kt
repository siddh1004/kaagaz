package com.portfolio.kaagazcamera.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.portfolio.kaagazcamera.data.database.ImageTableName
import com.portfolio.kaagazcamera.data.helper.DomainMapper
import com.portfolio.kaagazcamera.domain.model.Image

@Entity(tableName = ImageTableName)
data class ImageEntity(
    @PrimaryKey
    val fileName: String,
    val album: String,
    val uri: String
) : DomainMapper<Image> {

    override fun mapToDomainModel() = Image(
        fileName = fileName,
        album = album,
        uri = uri
    )
}