package com.mvk.imageloader.data.repository.remote.definition

import com.mvk.imageloader.data.either.Either
import com.mvk.imageloader.data.error.ErrorCode
import com.mvk.imageloader.data.model.ImageResponse
import retrofit2.http.Url

interface ImageRepository {

  suspend fun getAllImages(): Either<ErrorCode, List<ImageResponse>?>

  suspend fun getImage(@Url url: String): Either<ErrorCode, String?>
}
