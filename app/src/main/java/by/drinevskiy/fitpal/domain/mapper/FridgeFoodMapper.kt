package by.drinevskiy.fitpal.domain.mapper

import by.drinevskiy.fitpal.data.model.FridgeFoodEntity
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.FridgeFoodItem
import javax.inject.Inject

class FridgeFoodMapper @Inject constructor() {
    fun mapFridgeFoodEntityToFridgeFoodItem(fridgeFoodEntity: FridgeFoodEntity?): FridgeFoodItem?{
        fridgeFoodEntity?.let {
            return FridgeFoodItem(
                id = it.id,
                name = it.name,
                weight = it.weight,
                kcal = it.kcal,
                protein = it.protein,
                fat = it.fat,
                carbons = it.carbons,
                cost = it.cost
            )
        }
        return null
    }

    fun mapFridgeFoodItemToFridgeFoodEntity(fridgeFoodItem: FridgeFoodItem?): FridgeFoodEntity? {
        fridgeFoodItem?.let{
            return FridgeFoodEntity(
                id = it.id,
                name = it.name,

                weight = it.weight,
                kcal = it.kcal,
                protein = it.protein,
                fat = it.fat,
                carbons = it.carbons,
                cost = it.cost
            )
        }
        return null
    }

    fun mapFoodListItemToFridgeFoodEntity(foodListItem: FoodListItem): FridgeFoodEntity{
        return FridgeFoodEntity(
            id = foodListItem.id,
            name = foodListItem.name,
            weight = foodListItem.weight,
            kcal = foodListItem.kcal,
            protein = foodListItem.protein,
            fat = foodListItem.fat,
            carbons = foodListItem.carbons,
            cost = foodListItem.cost
        )
    }

    fun mapFridgeFoodEntityListToFridgeFoodItemList(list: List<FridgeFoodEntity>): List<FridgeFoodItem?>{
        return list.map { mapFridgeFoodEntityToFridgeFoodItem(it) }
    }

    fun mapFridgeFoodItemListToFridgeFoodEntityList(list: List<FridgeFoodItem>): List<FridgeFoodEntity?>{
        return list.map { mapFridgeFoodItemToFridgeFoodEntity(it) }
    }
}