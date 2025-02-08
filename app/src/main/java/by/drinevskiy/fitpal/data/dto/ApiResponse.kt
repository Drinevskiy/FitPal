package by.drinevskiy.fitpal.data.dto

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val code: String?,
    val product: ProductDTO?,
    val status: Int?,
    @SerializedName("status_verbose") val statusVerbose: String?
)
