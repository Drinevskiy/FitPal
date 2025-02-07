package by.drinevskiy.fitpal.domain.repository

sealed class UIResources<out T> {
    data class Success<out T>(val data: T): UIResources<T>()
    data class Error(val msg: String): UIResources<Nothing>()
    object Loading : UIResources<Nothing>()
}
