package by.drinevskiy.fitpal.presentation.kitchen

import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem

data class KitchenUiState(
    val purchaseList: List<PurchaseListItem> = listOf(),
    val isLoading: Boolean = false,
    val errorName: String? = null,
    val currentPurchase: PurchaseListItem? = null
)