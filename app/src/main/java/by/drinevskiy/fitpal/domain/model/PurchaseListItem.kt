package by.drinevskiy.fitpal.domain.model

import java.time.LocalDateTime

data class PurchaseListItem(
    val cost: Double = 0.0,
    val weight: Double = 0.0,
    val date: LocalDateTime = LocalDateTime.now(),
    val foods: List<FoodListItem> = listOf(),
)
