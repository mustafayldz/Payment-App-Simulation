package com.example.paymentappsimulation.di


import android.app.Application
import androidx.room.Room
import com.example.paymentappsimulation.data.db.PaymentDatabase
import com.example.paymentappsimulation.data.local.SaleDao
import com.example.paymentappsimulation.data.remote.ApiService
import com.example.paymentappsimulation.data.respository.SaleReposityRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PaymentDatabase {
        return Room.databaseBuilder(
            app,
            PaymentDatabase::class.java,
            "sales_db"
        ).build()
    }

    @Provides
    fun provideSaleDao(db: PaymentDatabase): SaleDao {
        return db.saleDao()
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return SaleReposityRemote()
    }
}

