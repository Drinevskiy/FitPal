package by.drinevskiy.fitpal.domain.usecase.food

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.domain.mapper.FoodMapper
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.repository.IFoodRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(private val foodRepository: IFoodRepository, private val foodMapper: FoodMapper){
    operator fun invoke(): Flow<UIResources<List<FoodListItem>>> {
        return foodRepository.getAllFoods().map { uiResource ->
            when (uiResource) {
                is UIResources.Success -> {
                    UIResources.Success(foodMapper.mapToDomainList(uiResource.data))
                }
                is UIResources.Error -> {
                    UIResources.Error(uiResource.msg)
                }
                is UIResources.Loading -> UIResources.Loading
            }
        }
    }
}

class AddFoodUseCase @Inject constructor(private val foodRepository: IFoodRepository, private val foodMapper: FoodMapper){
    suspend operator fun invoke(foodListItem: FoodListItem) {
        foodRepository.addFood(foodMapper.mapToData(foodListItem))
    }
}

class RemoveFoodUseCase @Inject constructor(private val foodRepository: IFoodRepository, private val foodMapper: FoodMapper){
    suspend operator fun invoke(foodListItem: FoodListItem) {
        foodRepository.removeFood(foodMapper.mapToData(foodListItem))
    }
}

class RemoveAllFoodUseCase @Inject constructor(private val foodRepository: IFoodRepository){
    suspend operator fun invoke() {
        foodRepository.removeAllFood()
    }
}