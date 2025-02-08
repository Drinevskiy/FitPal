package by.drinevskiy.fitpal.domain.usecase.openFood

import by.drinevskiy.fitpal.data.dto.ProductDTO
import by.drinevskiy.fitpal.data.repository.remote.OpenFoodRepository
import by.drinevskiy.fitpal.domain.mapper.OpenFoodMapper
import by.drinevskiy.fitpal.domain.model.OpenFoodItem
import javax.inject.Inject

class GetProductByBarcode @Inject constructor(private val openFoodRepository: OpenFoodRepository, private val openFoodMapper: OpenFoodMapper){
    suspend operator fun invoke(id: String): OpenFoodItem?{
        return openFoodMapper.mapToDomain(openFoodRepository.getProductById(id))
    }
}