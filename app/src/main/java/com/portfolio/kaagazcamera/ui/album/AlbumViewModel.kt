package com.portfolio.kaagazcamera.ui.album

import com.portfolio.kaagazcamera.domain.interaction.album.GetAlbumUseCase
import com.portfolio.kaagazcamera.domain.model.Album
import com.portfolio.kaagazcamera.ui.base.Success
import com.portfolio.kaagazcamera.ui.base.ViewModelBase
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbumUseCase
) : ViewModelBase<List<Album>>() {
    fun getAlbumList() = executeUseCase {
        _viewState.value = Success(getAlbums.invoke())
    }
}