package by.drinevskiy.fitpal.di

import android.content.Context
import androidx.room.Room
import by.drinevskiy.fitpal.data.dao.FoodDao
import by.drinevskiy.fitpal.data.dao.FridgeFoodDao
import by.drinevskiy.fitpal.data.dao.PurchaseDao
import by.drinevskiy.fitpal.data.db.FitPalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FitPalDatabase{
        return Room.databaseBuilder(context.applicationContext, FitPalDatabase::class.java, "FitPalDatabase")
//            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFoodDao(db: FitPalDatabase): FoodDao{
        return db.foodDao()
    }

    @Singleton
    @Provides
    fun providePurchaseDao(db: FitPalDatabase): PurchaseDao{
        return db.purchaseDao()
    }

    @Singleton
    @Provides
    fun provideFridgeFoodDao(db: FitPalDatabase): FridgeFoodDao{
        return db.fridgeFoodDao()
    }
}