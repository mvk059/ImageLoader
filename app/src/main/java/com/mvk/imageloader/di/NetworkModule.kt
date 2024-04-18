package com.mvk.imageloader.di

import android.content.Context
import com.google.gson.Gson
import com.mvk.imageloader.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideRetrofitClient(): Retrofit {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
      this.level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient().newBuilder()
      .readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(60, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .build()

    return Retrofit.Builder()
      .baseUrl("https://acharyaprashant.org/")
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .client(okHttpClient)
      .build()
  }

  @Singleton
  @Provides
  fun provideNetworkUtil(@ApplicationContext context: Context)
      : NetworkUtil = NetworkUtil(context = context)
}