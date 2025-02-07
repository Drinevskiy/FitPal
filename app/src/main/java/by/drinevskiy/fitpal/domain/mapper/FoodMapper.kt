package by.drinevskiy.fitpal.domain.mapper

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.domain.model.FoodListItem
import javax.inject.Inject

class FoodMapper @Inject constructor() {
    fun mapToDomain(foodEntity: FoodEntity): FoodListItem {
        return FoodListItem(
            id = foodEntity.id,
            name = foodEntity.name,
            weight = foodEntity.weight, // Вес от 50 до 500 грамм
            ccal = foodEntity.ccal, // Калории от 50 до 500
            protein = foodEntity.protein, // Белки от 0 до 30 грамм
            fat = foodEntity.fat, // Жиры от 0 до 20 грамм
            carbons = foodEntity.carbons, // Углеводы от 0 до 100 грамм
            cost = foodEntity.cost, // Стоимость от 1 до 20
            isLiked = foodEntity.isLiked // Случайное значение для isLiked
        )
    }

    fun mapToData(foodListItem: FoodListItem): FoodEntity {
        return FoodEntity(
            id = foodListItem.id,
            name = foodListItem.name,
            weight = foodListItem.weight, // Вес от 50 до 500 грамм
            ccal = foodListItem.ccal, // Калории от 50 до 500
            protein = foodListItem.protein, // Белки от 0 до 30 грамм
            fat = foodListItem.fat, // Жиры от 0 до 20 грамм
            carbons = foodListItem.carbons, // Углеводы от 0 до 100 грамм
            cost = foodListItem.cost, // Стоимость от 1 до 20
            isLiked = foodListItem.isLiked // Случайное значение для isLiked
        )
    }

    fun mapToDomainList(foodEntities: List<FoodEntity>): List<FoodListItem> {
        return foodEntities.map { mapToDomain(it) }
    }
}