package by.drinevskiy.fitpal.domain.usecase.purchase

import androidx.room.Insert
import by.drinevskiy.fitpal.domain.mapper.FoodMapper
import by.drinevskiy.fitpal.domain.mapper.PurchaseMapper
import by.drinevskiy.fitpal.domain.model.FoodListItem
import by.drinevskiy.fitpal.domain.model.PurchaseListItem
import by.drinevskiy.fitpal.domain.repository.IFoodRepository
import by.drinevskiy.fitpal.domain.repository.IPurchaseRepository
import by.drinevskiy.fitpal.domain.repository.UIResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPurchaseUseCase @Inject constructor(private val purchaseRepository: IPurchaseRepository, private val purchaseMapper: PurchaseMapper){
    operator fun invoke(): Flow<UIResources<List<PurchaseListItem>>> {
        return purchaseRepository.getAllPurchase().map { uiResource ->
            when (uiResource) {
                is UIResources.Success -> {
                    UIResources.Success(purchaseMapper.mapToDomainList(uiResource.data))
                }
                is UIResources.Error -> {
                    UIResources.Error(uiResource.msg)
                }
                is UIResources.Loading -> UIResources.Loading
            }
        }
    }
}

class AddPurchaseUseCase @Inject constructor(private val purchaseRepository: IPurchaseRepository, private val purchaseMapper: PurchaseMapper){
    suspend operator fun invoke(purchaseListItem: PurchaseListItem) {
        purchaseRepository.addPurchase(purchaseMapper.mapToData(purchaseListItem))
    }
}

class GetPurchaseByIdUseCase @Inject constructor(private val purchaseRepository: IPurchaseRepository, private val purchaseMapper: PurchaseMapper){
    suspend operator fun invoke(id: Int): PurchaseListItem{
        return purchaseMapper.mapToDomain(purchaseRepository.getPurchaseById(id))
    }
}