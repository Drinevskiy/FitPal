package by.drinevskiy.fitpal.domain.model

data class PurchaseDetailListItem(
    val id: Int? = null,
    val name: String,
    val weight: Double,
    val kcal100g: Int,
    val protein100g: Double,
    val fat100g: Double,
    val carbon100g: Double,
    val kcal: Int,
    val protein: Double,
    val fat: Double,
    val carbon: Double,
    val cost: Double,
)
