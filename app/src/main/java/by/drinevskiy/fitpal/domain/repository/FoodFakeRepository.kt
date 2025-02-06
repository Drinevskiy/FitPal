package by.drinevskiy.fitpal.domain.repository

import by.drinevskiy.fitpal.domain.model.FoodListItem
import kotlin.random.Random

class FoodFakeRepository {
    private var foodList: List<FoodListItem> = listOf()
    init {
        foodList = generateFakeFoodList(20) // Генерируем 10 фейковых продуктов
    }
    private var _id = 1;
    private fun generateFakeFoodList(size: Int): List<FoodListItem> {
        val names = listOf("Яблоко", "Банан", "Груша", "Куриное филе", "Рис", "Макароны", "Морковь", "Творог", "Колбаса", "Шоколад")
        return List(size) {
            FoodListItem(
                id = _id++,
                name = names[Random.nextInt(names.size)],
                weight = Random.nextDouble(50.0, 500.0), // Вес от 50 до 500 грамм
                ccal = Random.nextInt(50, 500), // Калории от 50 до 500
                protein = Random.nextDouble(0.0, 30.0), // Белки от 0 до 30 грамм
                fat = Random.nextDouble(0.0, 20.0), // Жиры от 0 до 20 грамм
                carbons = Random.nextDouble(0.0, 100.0), // Углеводы от 0 до 100 грамм
                cost = Random.nextDouble(1.0, 20.0), // Стоимость от 1 до 20
                isLiked = Random.nextBoolean() // Случайное значение для isLiked
            )
        }
    }

    fun likeFood(foodListItem: FoodListItem){
        val index = foodList.indexOfFirst { it.id == foodListItem.id}
        if(index == -1) return

        foodList = ArrayList(foodList)
        (foodList as ArrayList<FoodListItem>)[index] = foodList[index].copy(isLiked = !foodList[index].isLiked)
    }

    fun deleteFood(foodListItem: FoodListItem){
        val index = foodList.indexOfFirst { it.id == foodListItem.id } // Находим индекс человека в списке
        if (index == -1) return // Останавливаемся, если не находим такого человека

        foodList = ArrayList<FoodListItem>(foodList) // Создаем новый список
        (foodList as ArrayList<FoodListItem>).removeAt(index) // Удаляем человека
    }
    fun getFoodList(): List<FoodListItem> {
        return foodList
    }
}