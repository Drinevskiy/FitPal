package by.drinevskiy.fitpal.presentation.add

import by.drinevskiy.fitpal.domain.model.FoodListItem

data class AddUiState(
    val foodList: List<FoodListItem> = listOf(),
    val isLoading: Boolean = false,
    val errorName: String? = null,
)