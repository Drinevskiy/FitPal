package by.drinevskiy.fitpal.domain.repository

import by.drinevskiy.fitpal.data.model.FoodEntity
import kotlinx.coroutines.flow.Flow

interface IFoodRepository {
    fun getAllFoods(): Flow<UIResources<List<FoodEntity>>>
    suspend fun addFood(foodEntity: FoodEntity)
    suspend fun removeFood(foodEntity: FoodEntity)
    suspend fun removeAllFood()
//    suspend fun
}