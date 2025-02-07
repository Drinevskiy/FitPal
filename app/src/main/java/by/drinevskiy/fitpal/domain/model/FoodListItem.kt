package by.drinevskiy.fitpal.domain.model

data class FoodListItem(
    val id: Int? = null,
    val name: String,
    val weight: Double,
    val ccal: Int,
    val protein: Double,
    val fat: Double,
    val carbons: Double,
    val cost: Double,
    val isLiked: Boolean
)
