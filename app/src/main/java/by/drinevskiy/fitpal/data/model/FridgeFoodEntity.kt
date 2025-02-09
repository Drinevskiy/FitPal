package by.drinevskiy.fitpal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fridge_foods")
data class FridgeFoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String,
    val weight: Double,
    val kcal: Int,
    val protein: Double,
    val fat: Double,
    val carbons: Double,
    val cost: Double,
)
