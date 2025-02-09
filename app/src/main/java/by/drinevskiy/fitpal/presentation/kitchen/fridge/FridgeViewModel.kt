package by.drinevskiy.fitpal.presentation.kitchen.fridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.drinevskiy.fitpal.domain.repository.UIResources
import by.drinevskiy.fitpal.domain.usecase.fridgeFood.GetAllFridgeFoodUseCase
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
class FridgeViewModel @Inject constructor(
    private val getAllFridgeFoodUseCase: GetAllFridgeFoodUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(FridgeUiState())
    val uiState = _uiState.asStateFlow()
    init {
        getAllFridgeFood()
    }

    fun getAllFridgeFood(){
        viewModelScope.launch(Dispatchers.IO){
            getAllFridgeFoodUseCase().collectLatest { resource ->
                when(resource){
                    is UIResources.Success -> withContext(Dispatchers.Main){
                        _uiState.update { it.copy(isLoading = false, fridgeFoodList = resource.data)}
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
}