package by.drinevskiy.fitpal.domain.repository

import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import kotlinx.coroutines.flow.Flow

interface IFridgeFoodRepository {
    fun getAllFridgeFood(): Flow<UIResources<List<FridgeFoodEntity>>>
    suspend fun insertFridgeFood(fridgeFoodEntity: FridgeFoodEntity)
    suspend fun deleteFridgeFood(fridgeFoodEntity: FridgeFoodEntity)
    suspend fun getFridgeFoodByName(name: String): FridgeFoodEntity?
    suspend fun updateFridgeFood(name: String, weight: Double, cost: Double)
    suspend fun insertOrUpdateFridgeFood(fridgeFoodEntity: FridgeFoodEntity)
}