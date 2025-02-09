package by.drinevskiy.fitpal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.drinevskiy.fitpal.data.converters.DateConverter
import by.drinevskiy.fitpal.data.converters.FoodConverter
import by.drinevskiy.fitpal.data.dao.FoodDao
import by.drinevskiy.fitpal.data.dao.FridgeFoodDao
import by.drinevskiy.fitpal.data.dao.PurchaseDao
import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import by.drinevskiy.fitpal.data.model.PurchaseEntity

@Database(entities = [FoodEntity::class, PurchaseEntity::class, FridgeFoodEntity::class], version = 2)
@TypeConverters(DateConverter::class, FoodConverter::class)
abstract class FitPalDatabase : RoomDatabase(){
    abstract fun foodDao(): FoodDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun fridgeFoodDao(): FridgeFoodDao
}