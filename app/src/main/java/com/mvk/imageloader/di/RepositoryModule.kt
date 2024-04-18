package com.mvk.imageloader.di

import com.mvk.imageloader.data.datasource.ImageDataSource
import com.mvk.imageloader.data.repository.remote.definition.ImageRepository
import com.mvk.imageloader.data.repository.remote.implementation.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  fun provideImageRepository(
    dataSource: ImageDataSource,
  ): ImageRepository = ImageRepositoryImpl(
    dataSource = dataSource
  )
}
