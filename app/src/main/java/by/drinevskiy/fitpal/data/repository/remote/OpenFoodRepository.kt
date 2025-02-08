package by.drinevskiy.fitpal.data.repository.remote

import android.util.Log
import by.drinevskiy.fitpal.data.dto.ProductDTO
import by.drinevskiy.fitpal.data.remote.IOpenFoodFactsApiService
import javax.inject.Inject

class OpenFoodRepository @Inject constructor(private val apiService: IOpenFoodFactsApiService) {
    suspend fun getProductById(id: String): ProductDTO?{
        try {
            return apiService.getProduct(id)?.product
        } catch (e: Exception){
            return null
        }
    }
}