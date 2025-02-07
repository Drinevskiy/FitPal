package by.drinevskiy.fitpal.data.repository

import by.drinevskiy.fitpal.R
import by.drinevskiy.fitpal.data.dao.FoodDao
import by.drinevskiy.fitpal.data.db.FitPalDatabase
import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.domain.repository.IFoodRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FoodRoomRepositoryImpl @Inject constructor(private val foodDao: FoodDao): IFoodRepository {
    override fun getAllFoods(): Flow<UIResources<List<FoodEntity>>> = flow {
        emit(UIResources.Loading)
        foodDao.getAllFoods().collect{
            emit(UIResources.Success(it))
        }
    }.catch {
        emit(UIResources.Error((it.localizedMessage ?: R.string.load_food_error_message).toString()))
    }

    override suspend fun addFood(foodEntity: FoodEntity) {
        try {
            foodDao.insertFood(foodEntity)
        } catch(e: Exception) {
            throw e
        }
    }

    override suspend fun removeFood(foodEntity: FoodEntity) {
        try {
            foodDao.deleteFood(foodEntity)
        } catch(e: Exception) {
            throw e
        }
    }

    override suspend fun removeAllFood() {
        try {
            foodDao.deleteAllFood()
        } catch(e: Exception) {
            throw e
        }
    }
}