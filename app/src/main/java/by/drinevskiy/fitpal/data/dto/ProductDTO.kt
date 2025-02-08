package by.drinevskiy.fitpal.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("product_name_en") val productNameEn: String?,
    @SerializedName("product_quantity") val productQuantity: String?,
    val nutriments: NutrimentsDTO?
)