package com.mvk.imageloader.data.repository.remote.implementation

import com.mvk.imageloader.data.datasource.ImageDataSource
import com.mvk.imageloader.data.either.Either
import com.mvk.imageloader.data.error.ErrorCode
import com.mvk.imageloader.data.model.ImageResponse
import com.mvk.imageloader.data.repository.remote.definition.ImageRepository
import logcat.logcat
import retrofit2.http.Url
import java.net.HttpURLConnection.HTTP_BAD_REQUEST

class ImageRepositoryImpl(
  private val dataSource: ImageDataSource
) : ImageRepository {

  override suspend fun getAllImages(): Either<ErrorCode, List<ImageResponse>> {

    return try {
      val response = dataSource.getAllImages()
      if (response.isSuccessful) {
        val body = response.body()
        if (body == null)
          Either.Left(ErrorCode.NoDataError)
        else
          Either.Right(body)
      } else {
        if (response.code() == HTTP_BAD_REQUEST) { //400
          Either.Left(ErrorCode.BadRequestError)
        } else {
          Either.Left(ErrorCode.UnknownError)
        }
      }
    } catch (exception: Exception) {
//             Ideally log using a custom logger
//            Log.e(TemperatureRepositoryImpl::class.simpleName, "${exception.message}")
      logcat { "Exception: ${exception.localizedMessage}" }
      Either.Left(ErrorCode.UnknownError)
    }
  }

  override suspend fun getImage(@Url url: String): Either<ErrorCode, String?> {

    return try {
      val response = dataSource.getImage(url = url)
      if (response.isSuccessful) {
        val body = response.body()
        if (body == null)
          Either.Left(ErrorCode.NoDataError)
        else
          Either.Right(body)
      } else {
        if (response.code() == HTTP_BAD_REQUEST) { //400
          Either.Left(ErrorCode.BadRequestError)
        } else {
          Either.Left(ErrorCode.UnknownError)
        }
      }
    } catch (exception: Exception) {
//            Ideally log using a custom logger
//            Log.e(TemperatureRepositoryImpl::class.simpleName, "${exception.message}")
      logcat { "Exception: ${exception.localizedMessage}" }
      Either.Left(ErrorCode.UnknownError)
    }
  }
}