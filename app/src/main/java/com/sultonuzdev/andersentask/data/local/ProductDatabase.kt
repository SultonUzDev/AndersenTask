package com.sultonuzdev.andersentask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultonuzdev.andersentask.data.local.entities.CategoryEntity
import com.sultonuzdev.andersentask.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ProductDatabase : RoomDatabase() {
    abstract val dao: ProductDao
}