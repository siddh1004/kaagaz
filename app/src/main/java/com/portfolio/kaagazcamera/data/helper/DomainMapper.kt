package com.portfolio.kaagazcamera.data.helper

interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}