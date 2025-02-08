package by.drinevskiy.fitpal.presentation.add

import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.OpenFoodItem

data class AddUiState(
    val foodList: List<FoodListItem> = listOf(),
    val isLoading: Boolean = false,
    val errorName: String? = null,
    val openFoodItem: OpenFoodItem? = null,
)