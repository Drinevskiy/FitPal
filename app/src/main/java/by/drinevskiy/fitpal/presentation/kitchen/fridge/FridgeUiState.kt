package by.drinevskiy.fitpal.presentation.kitchen.fridge

import by.drinevskiy.fitpal.domain.model.FridgeFoodItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem

data class FridgeUiState(
    val fridgeFoodList: List<FridgeFoodItem?> = listOf(),
    val isLoading: Boolean = false,
    val errorName: String? = null,
)
