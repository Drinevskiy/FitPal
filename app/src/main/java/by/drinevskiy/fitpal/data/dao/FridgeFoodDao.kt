package by.drinevskiy.fitpal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FridgeFoodDao {
    @Query("SELECT * FROM fridge_foods")
    fun getAllFridgeFood(): Flow<List<FridgeFoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFridgeFood(fridgeFoodEntity: FridgeFoodEntity)

    @Delete
    suspend fun deleteFridgeFood(fridgeFoodEntity: FridgeFoodEntity)

    @Query("SELECT * FROM fridge_foods WHERE name = :name LIMIT 1")
    suspend fun getFridgeFoodByName(name: String): FridgeFoodEntity?

    @Query("""
        UPDATE fridge_foods 
        SET weight = weight + :weight,
            cost = ((cost * weight) + (:cost * :weight)) / (weight + :weight)
        WHERE name = :name
    """)
    suspend fun updateFridgeFood(name: String, weight: Double, cost: Double)

//    @Query("DELETE FROM foods")
//    suspend fun deleteAllFood()
}