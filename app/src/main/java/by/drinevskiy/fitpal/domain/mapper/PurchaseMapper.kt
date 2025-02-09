package by.drinevskiy.fitpal.domain.mapper

import by.drinevskiy.fitpal.data.model.FoodEntity
import by.drinevskiy.fitpal.data.model.PurchaseEntity
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseDetailListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import javax.inject.Inject

class PurchaseMapper @Inject constructor(private val foodMapper: FoodMapper) {
    fun mapToDomain(purchaseEntity: PurchaseEntity?): PurchaseListItem {
        purchaseEntity?.let { item ->
            val foods = foodMapper.mapToPurchaseDetailDomainList(item.foods)
            return PurchaseListItem(
                id = item.id,
                date = item.dateTime,
                foods = foods,
                weight = foods.sumOf { it.weight }, // Вес от 50 до 500 грамм
                cost = foods.sumOf { it.cost }, // Стоимость от 1 до 20
            )
        }
        return PurchaseListItem()
    }

//    fun mapToDetailDomain(purchaseEntity: PurchaseEntity?): PurchaseDetailListItem {
//        purchaseEntity?.let { item ->
//            val foods = foodMapper.mapToDomainList(item.foods)
//            return PurchaseDetailListItem(
//                id = item.id,
//                name = item,
//                weight = ,
//                kcal100g = ,
//                protein100g = ,
//                fat100g = ,
//                carbon100g = ,
//                kcal = ,
//                protein = ,
//                fat = ,
//                carbon = ,
//                cost = ,
////                id = item.id,
////                date = item.dateTime,
////                foods = foods,
////                weight = foods.sumOf { it.weight }, // Вес от 50 до 500 грамм
////                cost = foods.sumOf { it.cost }, // Стоимость от 1 до 20
//            )
//        }
//        return PurchaseDetaiListItem()
//    }

    fun mapToData(purchaseListItem: PurchaseListItem): PurchaseEntity {
        return PurchaseEntity(
            foods = purchaseListItem.foods.map { foodMapper.mapToPurchaseDetailData(it) }
        )
    }

    fun mapToDomainList(purchaseEntities: List<PurchaseEntity>): List<PurchaseListItem> {
        return purchaseEntities.map { mapToDomain(it) }
    }
}