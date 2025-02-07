package by.drinevskiy.fitpal.presentation.add

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val removeAllFoodUseCase: RemoveAllFoodUseCase
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
            withContext(Dispatchers.Main){
                _uiState.update {
                    it.copy(
                        isLoading = false,
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

    fun likeFood(it: FoodListItem) {

    }
//    private var foodList: List<FoodListItem> = listOf()
//    init {
//        foodList = generateFakeFoodList(10) // Генерируем 10 фейковых продуктов
//    }
//    private var _id = 1;
//    private fun generateFakeFoodList(size: Int): List<FoodListItem> {
//        val names = listOf("Яблоко", "Банан", "Груша", "Куриное филе", "Рис", "Макароны", "Морковь", "Творог", "Колбаса", "Шоколад")
//        return List(size) {
//            FoodListItem(
//                id = _id++,
//                name = names[Random.nextInt(names.size)],
//                weight = Random.nextDouble(50.0, 500.0), // Вес от 50 до 500 грамм
//                ccal = Random.nextInt(50, 500), // Калории от 50 до 500
//                protein = Random.nextDouble(0.0, 30.0), // Белки от 0 до 30 грамм
//                fat = Random.nextDouble(0.0, 20.0), // Жиры от 0 до 20 грамм
//                carbons = Random.nextDouble(0.0, 100.0), // Углеводы от 0 до 100 грамм
//                cost = Random.nextDouble(1.0, 20.0), // Стоимость от 1 до 20
//                isLiked = Random.nextBoolean() // Случайное значение для isLiked
//            )
//        }
//    }
//
//
//    fun likeFood(foodListItem: FoodListItem){
//        val index = foodList.indexOfFirst { it.id == foodListItem.id}
//        if(index == -1) return
//
//        foodList = ArrayList(foodList)
//        (foodList as ArrayList<FoodListItem>)[index] = foodList[index].copy(isLiked = !foodList[index].isLiked)
//    }
//
//    fun deleteFood(foodListItem: FoodListItem){
//        val index = foodList.indexOfFirst { it.id == foodListItem.id } // Находим индекс человека в списке
//        if (index == -1) return // Останавливаемся, если не находим такого человека
//
//        foodList = ArrayList<FoodListItem>(foodList) // Создаем новый список
//        (foodList as ArrayList<FoodListItem>).removeAt(index) // Удаляем человека
//    }
//    fun addFood(foodListItem: FoodListItem){
//        foodList = foodList.plus(foodListItem)
//    }
//    fun getFoodList(): List<FoodListItem> {
//        return foodList
//    }
}