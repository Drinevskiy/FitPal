package by.drinevskiy.fitpal.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.repository.UIResources
import by.drinevskiy.fitpal.domain.usecase.food.AddFoodUseCase
import by.drinevskiy.fitpal.domain.usecase.food.GetAllFoodUseCase
import by.drinevskiy.fitpal.domain.usecase.food.RemoveAllFoodUseCase
import by.drinevskiy.fitpal.domain.usecase.food.RemoveFoodUseCase
import by.drinevskiy.fitpal.domain.usecase.openFood.GetProductByBarcode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddViewModel @Inject constructor(
    private val getAllFoodUseCase: GetAllFoodUseCase,
    private val addFoodUseCase: AddFoodUseCase,
    private val removeFoodUseCase: RemoveFoodUseCase,
    private val removeAllFoodUseCase: RemoveAllFoodUseCase,
    private val getProductByBarcode: GetProductByBarcode
): ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState = _uiState.asStateFlow()
    init {
        loadFood()
    }

    fun loadFood(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodUseCase().collectLatest { resource ->
                when(resource){
                    is UIResources.Success -> withContext(Dispatchers.Main){
                        _uiState.update { it.copy(isLoading = false, foodList = resource.data)}
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

    fun addFood(foodListItem: FoodListItem){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            addFoodUseCase(foodListItem)
//            var updatedFoodList = emptyList<FoodListItem>()
//            getAllFoodUseCase().collectLatest {
//                updatedFoodList = when(it){
//                    is UIResources.Success -> it.data
//                    is UIResources.Error -> emptyList<FoodListItem>()
//                    is UIResources.Loading -> emptyList<FoodListItem>()
//                }
//            } // Загружаем обновленный список
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        isLoading = false,
//                        foodList = updatedFoodList,
                        openFoodItem = null
                    )
                }
            }
        }
    }

    fun removeFood(foodListItem: FoodListItem){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            removeFoodUseCase(foodListItem)
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun removeAllFood(){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            removeAllFoodUseCase()
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun loadProductInfoByBarcode(id: String?){
        viewModelScope.launch(Dispatchers.IO) {
            if(id != null) {
                val productByBarcode = getProductByBarcode(id)
                Log.i("AddViewModel", productByBarcode.toString())
                withContext(Dispatchers.Main) {
                    _uiState.update {
                        it.copy(
                            openFoodItem = productByBarcode,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    fun likeFood(it: FoodListItem) {

    }
}