package com.mvk.imageloader.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvk.imageloader.data.dispatchers.IoDispatcher
import com.mvk.imageloader.data.either.Either
import com.mvk.imageloader.data.error.ErrorCode
import com.mvk.imageloader.data.model.ImageResponse
import com.mvk.imageloader.data.repository.remote.definition.ImageRepository
import com.mvk.imageloader.ui.images.Image
import com.mvk.imageloader.ui.loader.LoadingState
import com.mvk.imageloader.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: ImageRepository,
  private val networkUtil: NetworkUtil,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _allImages: MutableState<List<Image>?> = mutableStateOf(null)
  internal val allImages: State<List<Image>?> = _allImages

  private val _error: MutableState<ErrorCode?> = mutableStateOf(null)
  internal val error: State<ErrorCode?> = _error

  val isLoading: LoadingState by lazy { LoadingState() }

  init {
    logcat { "Init viewmodel" }
//    getAllImages()
  }

  fun getAllImages() {

    logcat { "getAllImages" }
    if (!networkUtil.isNetworkAvailable()) {
      _error.value = ErrorCode.NoInternetError
      return
    }

    viewModelScope.launch(ioDispatcher) {

      isLoading.doWhileLoading {
        mapAllImages()
      }

    }
  }

  private suspend fun mapAllImages() {
    logcat { "mapAllImages" }

    when (val data = repository.getAllImages()) {
      is Either.Left -> {
        _error.value = data.value
      }

      is Either.Right -> {
        val images = data.value
        if (images == null) {
          _error.value = ErrorCode.NoDataError
          return
        }

        _allImages.value = images.mapToImage()
        logcat { _allImages.value?.size.toString() }
      }
    }
  }

  private fun List<ImageResponse>.mapToImage(): List<Image>? {
    logcat { "mapToImage" }
    return this@mapToImage?.mapNotNull { image ->
      val id = image.id
      val domain = image.thumbnail?.domain
      val basePath = image.thumbnail?.basePath
      val key = image.thumbnail?.key
      val url = "$domain/$basePath/0/$key"

      if (id == null || domain == null || basePath == null || key == null) null
      else Image(id = id, url = url)
    }
  }

}