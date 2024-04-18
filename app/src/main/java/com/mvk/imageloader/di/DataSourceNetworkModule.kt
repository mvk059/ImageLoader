package com.mvk.imageloader.di

import com.mvk.imageloader.data.datasource.ImageDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataSourceNetworkModule {

  @Provides
  fun provideImageDataSource(
    retrofit: Retrofit,
  ): ImageDataSource = retrofit.create(ImageDataSource::class.java)

}
