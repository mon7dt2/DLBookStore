package com.base.mvvmbasekotlin.di.module

import android.content.Context
import com.base.mvvmbasekotlin.BuildConfig
import com.base.mvvmbasekotlin.network.ApiInterface
import com.base.mvvmbasekotlin.network.NetworkCheckerInterceptor
import com.base.mvvmbasekotlin.utils.Define

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiInterface(client: OkHttpClient): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) //setting in build.gradle app
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

//        val tokenInterceptor = TokenInterceptor()
        val networkCheckerInterceptor = NetworkCheckerInterceptor(context)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(tokenInterceptor)
            .addInterceptor(networkCheckerInterceptor)
            .connectTimeout(Define.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Define.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
