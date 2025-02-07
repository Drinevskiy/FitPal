package by.drinevskiy.fitpal.data.converters

import androidx.room.TypeConverter
import by.drinevskiy.fitpal.data.model.FoodEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromFoodList(foods: List<FoodEntity>?): String? {
        return gson.toJson(foods)
    }

    @TypeConverter
    fun toFoodList(foodsString: String?): List<FoodEntity>? {
        val listType = object : TypeToken<List<FoodEntity>>() {}.type
        return gson.fromJson<List<FoodEntity>>(foodsString, listType)
    }
}