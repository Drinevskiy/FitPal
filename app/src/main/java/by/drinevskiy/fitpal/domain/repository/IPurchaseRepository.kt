package by.drinevskiy.fitpal.domain.repository

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.data.model.PurchaseEntity
import kotlinx.coroutines.flow.Flow

interface IPurchaseRepository {
    fun getAllPurchase(): Flow<UIResources<List<PurchaseEntity>>>
    suspend fun getPurchaseById(id: Int): PurchaseEntity?
    suspend fun addPurchase(purchaseEntity: PurchaseEntity)
}