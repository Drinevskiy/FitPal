package by.drinevskiy.fitpal.domain.model


data class FridgeFoodItem(
    val id: Int? = 0,
    val name: String,
    val weight: Double,
    val kcal: Int,
    val protein: Double,
    val fat: Double,
    val carbons: Double,
    val cost: Double,
)