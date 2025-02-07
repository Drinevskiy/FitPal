package by.drinevskiy.fitpal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val foods: List<FoodEntity>,
)