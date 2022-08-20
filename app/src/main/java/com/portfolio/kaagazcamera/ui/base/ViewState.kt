package com.portfolio.kaagazcamera.ui.base

sealed class ViewState<out T : Any>
class Success<out T : Any>(val data: T) : ViewState<T>()
class Error<out T : Any>(val error: String) : ViewState<T>()
class Loading<out T : Any> : ViewState<T>()