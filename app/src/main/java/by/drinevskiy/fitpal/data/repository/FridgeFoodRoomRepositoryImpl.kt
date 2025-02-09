package by.drinevskiy.fitpal.data.repository

import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.data.dao.FridgeFoodDao
import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import by.drinevskiy.fitpal.domain.repository.IFridgeFoodRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FridgeFoodRoomRepositoryImpl @Inject constructor(private val fridgeFoodDao: FridgeFoodDao): IFridgeFoodRepository {
    override fun getAllFridgeFood(): Flow<UIResources<List<FridgeFoodEntity>>> = flow {
            emit(UIResources.Loading)
            fridgeFoodDao.getAllFridgeFood().collect{
                emit(UIResources.Success(it))
            }
        }.catch {
            emit(UIResources.Error((it.localizedMessage ?: R.string.load_food_error_message).toString()))
    }

    override suspend fun insertFridgeFood(fridgeFoodEntity: FridgeFoodEntity) {
        try {
            fridgeFoodDao.insertFridgeFood(fridgeFoodEntity)
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun deleteFridgeFood(fridgeFoodEntity: FridgeFoodEntity) {
        try {
            fridgeFoodDao.deleteFridgeFood(fridgeFoodEntity)
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getFridgeFoodByName(name: String): FridgeFoodEntity? {
        return try {
            fridgeFoodDao.getFridgeFoodByName(name)
        } catch (e: Exception){
            null
        }
    }

    override suspend fun updateFridgeFood(name: String, weight: Double, cost: Double) {
        try{
            fridgeFoodDao.updateFridgeFood(name, weight, cost)
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun insertOrUpdateFridgeFood(fridgeFoodEntity: FridgeFoodEntity) {
        val existingFood = fridgeFoodDao.getFridgeFoodByName(fridgeFoodEntity.name)

        if (existingFood != null) {
            // Обновляем существующий продукт, используя SQL-запрос
            fridgeFoodDao.updateFridgeFood(fridgeFoodEntity.name, fridgeFoodEntity.weight, fridgeFoodEntity.cost)
        } else {
            // Продукт не существует, вставляем новый
            fridgeFoodDao.insertFridgeFood(fridgeFoodEntity)
        }
    }
}