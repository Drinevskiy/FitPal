package by.drinevskiy.fitpal.presentation.kitchen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import by.drinevskiy.fitpal.domain.repository.UIResources
import by.drinevskiy.fitpal.domain.usecase.purchase.AddPurchaseUseCase
import by.drinevskiy.fitpal.domain.usecase.purchase.GetAllPurchaseUseCase
import by.drinevskiy.fitpal.presentation.add.AddUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class KitchenViewModel @Inject constructor(
    private val getAllPurchaseUseCase: GetAllPurchaseUseCase,
    private val addPurchaseUseCase: AddPurchaseUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(KitchenUiState())
    val uiState = _uiState.asStateFlow()
    init {
        loadPuchases()
    }

    fun loadPuchases(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllPurchaseUseCase().collectLatest { resource ->
                when(resource){
                    is UIResources.Success -> withContext(Dispatchers.Main){
                        _uiState.update { it.copy(isLoading = false, purchaseList = resource.data)}
                    }
                    is UIResources.Error -> withContext(Dispatchers.Main){
                        _uiState.update { it.copy(errorName = resource.msg)}
                    }
                    is UIResources.Loading -> withContext(Dispatchers.Main){
                        _uiState.update { it.copy(isLoading = true)}
                    }
                }
            }
        }
    }

    fun addPurchase(purchaseListItem: PurchaseListItem){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            addPurchaseUseCase(purchaseListItem)
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }
}