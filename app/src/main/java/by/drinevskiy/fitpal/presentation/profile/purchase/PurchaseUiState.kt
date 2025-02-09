package by.drinevskiy.fitpal.presentation.profile.purchase

import by.drinevskiy.fitpal.domain.model.PurchaseListItem

data class PurchaseUiState(
    val purchaseList: List<PurchaseListItem> = listOf(),
    val isLoading: Boolean = false,
    val errorName: String? = null,
    val currentPurchase: PurchaseListItem? = null
)