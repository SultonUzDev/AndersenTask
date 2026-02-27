package com.sultonuzdev.andersentask.domain.repository

import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getProductsByCategory(categoryId: Int): List<Product>
    fun searchProducts(categoryId: Int, query: String): Flow<List<Product>>

    suspend fun getFirstImagesOfEachCategory(): List<String>

}