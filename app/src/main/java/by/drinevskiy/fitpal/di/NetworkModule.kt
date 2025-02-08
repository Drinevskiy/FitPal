package by.drinevskiy.fitpal.di

import by.drinevskiy.fitpal.data.remote.IOpenFoodFactsApiService
import  by.drinevskiy.fitpal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOpenFoodFactsApiService(retrofit: Retrofit): IOpenFoodFactsApiService {
        return retrofit.create(IOpenFoodFactsApiService::class.java)
    }
}