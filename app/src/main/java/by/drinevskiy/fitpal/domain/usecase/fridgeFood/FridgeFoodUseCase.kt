package by.drinevskiy.fitpal.domain.usecase.fridgeFood

import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import by.drinevskiy.fitpal.domain.mapper.FridgeFoodMapper
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.FridgeFoodItem
import by.drinevskiy.fitpal.domain.repository.IFridgeFoodRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFridgeFoodUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    operator fun invoke(): Flow<UIResources<List<FridgeFoodItem?>>> {
        return fridgeFoodRepository.getAllFridgeFood().map { uiResource ->
            when (uiResource) {
                is UIResources.Success -> {
                    UIResources.Success(fridgeFoodMapper.mapFridgeFoodEntityListToFridgeFoodItemList(uiResource.data))
                }
                is UIResources.Error -> {
                    UIResources.Error(uiResource.msg)
                }
                is UIResources.Loading -> UIResources.Loading
            }
        }
    }
}

class AddFridgeFoodUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    suspend operator fun invoke(fridgeFoodItem: FridgeFoodItem){
        fridgeFoodRepository.insertFridgeFood(fridgeFoodMapper.mapFridgeFoodItemToFridgeFoodEntity(fridgeFoodItem)!!)
    }
}

class DeleteFridgeFoodUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    suspend operator fun invoke(fridgeFoodItem: FridgeFoodItem){
        fridgeFoodRepository.deleteFridgeFood(fridgeFoodMapper.mapFridgeFoodItemToFridgeFoodEntity(fridgeFoodItem)!!)
    }
}

class GetFridgeFoodByNameUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    suspend operator fun invoke(name: String): FridgeFoodItem?{
        return fridgeFoodMapper.mapFridgeFoodEntityToFridgeFoodItem(fridgeFoodRepository.getFridgeFoodByName(name))
    }
}

class UpdateFridgeFoodUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    suspend operator fun invoke(name: String, weight: Double, cost: Double){
        return fridgeFoodRepository.updateFridgeFood(name, weight, cost)
    }
}

class InsertOrUpdateFridgeFoodUseCase @Inject constructor(private val fridgeFoodRepository: IFridgeFoodRepository, private val fridgeFoodMapper: FridgeFoodMapper){
    suspend operator fun invoke(foodListItem: FoodListItem){
        return fridgeFoodRepository.insertOrUpdateFridgeFood(fridgeFoodMapper.mapFoodListItemToFridgeFoodEntity(foodListItem))
    }
}