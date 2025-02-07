package by.drinevskiy.fitpal.domain.mapper

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.data.model.PurchaseEntity
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import javax.inject.Inject

class PurchaseMapper @Inject constructor(private val foodMapper: FoodMapper) {
    fun mapToDomain(purchaseEntity: PurchaseEntity): PurchaseListItem {
        val foods = foodMapper.mapToDomainList(purchaseEntity.foods)
        return PurchaseListItem(
            date = purchaseEntity.dateTime,
            foods = foods,
            weight = foods.sumOf { it.weight }, // Вес от 50 до 500 грамм
            cost = foods.sumOf { it.cost }, // Стоимость от 1 до 20
        )
    }

    fun mapToData(purchaseListItem: PurchaseListItem): PurchaseEntity {
        return PurchaseEntity(
            foods = purchaseListItem.foods.map { foodMapper.mapToData(it) }
        )
    }

    fun mapToDomainList(purchaseEntities: List<PurchaseEntity>): List<PurchaseListItem> {
        return purchaseEntities.map { mapToDomain(it) }
    }
}