package com.mvk.imageloader.data.error

sealed interface ErrorCode {
    data object NoDataError : ErrorCode
    data object BadRequestError : ErrorCode
    data object NoInternetError : ErrorCode
    data object UnknownError : ErrorCode

}