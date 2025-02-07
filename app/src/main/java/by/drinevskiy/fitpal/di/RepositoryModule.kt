package by.drinevskiy.fitpal.di

import by.drinevskiy.fitpal.data.dao.FoodDao
import by.drinevskiy.fitpal.data.dao.PurchaseDao
import by.drinevskiy.fitpal.data.repository.FoodRoomRepositoryImpl
import by.drinevskiy.fitpal.data.repository.PurchaseRoomRepositoryImpl
import by.drinevskiy.fitpal.domain.repository.IFoodRepository
import by.drinevskiy.fitpal.domain.repository.IPurchaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFoodRepository(foodDao: FoodDao): IFoodRepository{
        return FoodRoomRepositoryImpl(foodDao)
    }
    @Singleton
    @Provides
    fun providePurchaseRepository(purchaseDao: PurchaseDao): IPurchaseRepository{
        return PurchaseRoomRepositoryImpl(purchaseDao)
    }
}