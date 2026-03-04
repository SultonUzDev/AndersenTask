package com.sultonuzdev.andersentask.di

import android.content.Context
import androidx.room.Room
import com.sultonuzdev.andersentask.core.utils.AppConstants.DATABASE_NAME
import com.sultonuzdev.andersentask.data.local.ProductDao
import com.sultonuzdev.andersentask.data.local.ProductDatabase
import com.sultonuzdev.andersentask.data.repository.ProductRepositoryImpl
import com.sultonuzdev.andersentask.domain.repository.ProductRepository

interface AppContainer {
    val repository: ProductRepository
}

class AppContainerImpl(context: Context) : AppContainer {

    private val productDatabase: ProductDatabase by lazy {
        Room.databaseBuilder(
            context,
            ProductDatabase::class.java, DATABASE_NAME
        )
            .createFromAsset(DATABASE_NAME)
            .fallbackToDestructiveMigration(true)
            .build()
    }
    private val productDao: ProductDao by lazy {
        productDatabase.dao
    }


    override val repository: ProductRepository by lazy {
        ProductRepositoryImpl(productDao)
    }

}