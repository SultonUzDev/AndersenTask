package com.sultonuzdev.andersentask.di

import androidx.room.Room
import com.sultonuzdev.andersentask.core.utils.AppConstants.DATABASE_NAME
import com.sultonuzdev.andersentask.data.local.ProductDao
import com.sultonuzdev.andersentask.data.local.ProductDatabase
import com.sultonuzdev.andersentask.data.repository.ProductRepositoryImpl
import com.sultonuzdev.andersentask.domain.repository.ProductRepository
import com.sultonuzdev.andersentask.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<ProductDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ProductDatabase::class.java, DATABASE_NAME)
            .createFromAsset(DATABASE_NAME)
            .fallbackToDestructiveMigration(true)
            .allowMainThreadQueries()
            .build()
    }
    single<ProductDao> { get<ProductDatabase>().dao }

    singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() }

    viewModelOf(::MainViewModel)

}