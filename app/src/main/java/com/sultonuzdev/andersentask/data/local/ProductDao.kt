package com.sultonuzdev.andersentask.data.local

import androidx.room.Dao
import androidx.room.Query
import com.sultonuzdev.andersentask.data.local.entities.CategoryEntity
import com.sultonuzdev.andersentask.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    suspend fun getProductsByCategory(categoryId: Int): List<ProductEntity>

    @Query("SELECT * FROM products WHERE categoryId = :categoryId AND title LIKE :query || '%'")
    fun searchProducts(categoryId: Int, query: String): Flow<List<ProductEntity>>


}