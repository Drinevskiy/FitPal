package by.drinevskiy.fitpal.domain.mapper

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseDetailListItem
import javax.inject.Inject
import kotlin.math.roundToInt

class FoodMapper @Inject constructor() {
    fun mapToDomain(foodEntity: FoodEntity): FoodListItem {
        return FoodListItem(
            id = foodEntity.id,
            name = foodEntity.name,
            weight = foodEntity.weight, // Вес от 50 до 500 грамм
            kcal = foodEntity.kcal, // Калории от 50 до 500
            protein = foodEntity.protein, // Белки от 0 до 30 грамм
            fat = foodEntity.fat, // Жиры от 0 до 20 грамм
            carbons = foodEntity.carbons, // Углеводы от 0 до 100 грамм
            cost = foodEntity.cost, // Стоимость от 1 до 20
            isLiked = foodEntity.isLiked // Случайное значение для isLiked
        )
    }

    fun mapToPurchaseDetailDomain(foodEntity: FoodEntity): PurchaseDetailListItem {
        foodEntity.let {
            return PurchaseDetailListItem(
                id = it.id,
                name = it.name,
                weight = it.weight,
                kcal100g = it.kcal,
                protein100g = it.protein,
                fat100g = it.fat,
                carbon100g = it.carbons,
                kcal = (it.kcal * it.weight / 100).roundToInt(),
                protein = it.protein * it.weight / 100,
                fat = it.fat * it.weight / 100,
                carbon = it.carbons * it.weight / 100,
                cost = it.cost,
            )
        }
    }

    fun mapToData(foodListItem: FoodListItem): FoodEntity {
        return FoodEntity(
            id = foodListItem.id,
            name = foodListItem.name,
            weight = foodListItem.weight, // Вес от 50 до 500 грамм
            kcal = foodListItem.kcal, // Калории от 50 до 500
            protein = foodListItem.protein, // Белки от 0 до 30 грамм
            fat = foodListItem.fat, // Жиры от 0 до 20 грамм
            carbons = foodListItem.carbons, // Углеводы от 0 до 100 грамм
            cost = foodListItem.cost, // Стоимость от 1 до 20
            isLiked = foodListItem.isLiked // Случайное значение для isLiked
        )
    }

    fun mapFoodListItemToPurchaseDetailListItem(foodListItem: FoodListItem): PurchaseDetailListItem{
        foodListItem.let {
            return PurchaseDetailListItem(
                id = it.id,
                name = it.name,
                weight = it.weight,
                kcal100g = it.kcal,
                protein100g = it.protein,
                fat100g = it.fat,
                carbon100g = it.carbons,
                kcal = (it.kcal * it.weight / 100).roundToInt(),
                protein = it.protein * it.weight / 100,
                fat = it.fat * it.weight / 100,
                carbon = it.carbons * it.weight / 100,
                cost = it.cost,
            )
        }
    }
    fun mapToPurchaseDetailData(purchaseDetailListItem: PurchaseDetailListItem): FoodEntity {
        return FoodEntity(
            id = purchaseDetailListItem.id,
            name = purchaseDetailListItem.name,
            weight = purchaseDetailListItem.weight, // Вес от 50 до 500 грамм
            kcal = purchaseDetailListItem.kcal100g, // Калории от 50 до 500
            protein = purchaseDetailListItem.protein100g, // Белки от 0 до 30 грамм
            fat = purchaseDetailListItem.fat100g, // Жиры от 0 до 20 грамм
            carbons = purchaseDetailListItem.carbon100g, // Углеводы от 0 до 100 грамм
            cost = purchaseDetailListItem.cost, // Стоимость от 1 до 20
        )
    }

    fun mapPurchaseDetailItemListToDataList(purchasesList: List<PurchaseDetailListItem>): List<FoodEntity>{
        return purchasesList.map { mapToPurchaseDetailData(it) }
    }
    fun mapToDomainList(foodEntities: List<FoodEntity>): List<FoodListItem> {
        return foodEntities.map { mapToDomain(it) }
    }

    fun mapToPurchaseDetailDomainList(foodEntities: List<FoodEntity>): List<PurchaseDetailListItem> {
        return foodEntities.map { mapToPurchaseDetailDomain(it) }
    }

    fun mapPurchaseDetailListToFoodList(purchasesList: List<FoodListItem>): List<PurchaseDetailListItem>{
        return purchasesList.map { mapFoodListItemToPurchaseDetailListItem(it) }
    }
}