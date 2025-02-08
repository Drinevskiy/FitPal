package by.drinevskiy.fitpal.domain.mapper

import android.util.Log
import by.drinevskiy.fitpal.data.dto.ProductDTO
import by.drinevskiy.fitpal.domain.model.OpenFoodItem
import javax.inject.Inject

class OpenFoodMapper @Inject constructor() {
    fun mapToDomain(productDTO: ProductDTO?): OpenFoodItem?{
        productDTO?.let {
            return OpenFoodItem(
                name = if (it.productName.isNullOrEmpty()) it.productNameEn else it.productName,
                weight = it.productQuantity?.toDouble(),
                kcal = it.nutriments?.energyKcal100g,
                protein = it.nutriments?.proteins100g,
                fat = it.nutriments?.fat100g,
                carbons = it.nutriments?.carbohydrates100g
            )
        }
        return null
    }
}