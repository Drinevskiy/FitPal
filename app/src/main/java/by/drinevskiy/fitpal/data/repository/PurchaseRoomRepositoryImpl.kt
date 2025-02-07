package by.drinevskiy.fitpal.data.repository

import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.data.dao.PurchaseDao
import by.drinevskiy.fitpal.data.model.PurchaseEntity
import by.drinevskiy.fitpal.domain.repository.IPurchaseRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PurchaseRoomRepositoryImpl @Inject constructor(private val purchaseDao: PurchaseDao): IPurchaseRepository {
    override fun getAllPurchase(): Flow<UIResources<List<PurchaseEntity>>> = flow{
        emit(UIResources.Loading)
        purchaseDao.getAllPurchases().collect{
            emit(UIResources.Success(it))
        }
    }.catch {
        emit(UIResources.Error((it.localizedMessage ?: R.string.load_food_error_message).toString()))
    }

    override suspend fun addPurchase(purchaseEntity: PurchaseEntity) {
        try {
           purchaseDao.insertPurchase(purchaseEntity)
        } catch (e: Exception){
            throw e
        }
    }
}