package by.drinevskiy.fitpal.data.remote

import by.drinevskiy.fitpal.data.dto.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IOpenFoodFactsApiService {
    @GET("{id}")
    suspend fun getProduct(@Path("id") id: String): ApiResponse?
}