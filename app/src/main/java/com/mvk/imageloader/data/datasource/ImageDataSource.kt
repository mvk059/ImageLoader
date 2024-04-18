package com.mvk.imageloader.data.datasource

import com.mvk.imageloader.data.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ImageDataSource {

  @GET("api/v2/content/misc/media-coverages")
  suspend fun getAllImages(
    @Query("limit") appId: Int = LIMIT,
  ): Response<List<ImageResponse>>

  @GET
  suspend fun getImage(
    @Url url: String,
  ): Response<String>

  private companion object {
    const val LIMIT = 100
  }
}
