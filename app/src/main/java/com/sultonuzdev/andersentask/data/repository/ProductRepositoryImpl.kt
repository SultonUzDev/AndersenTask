package com.sultonuzdev.andersentask.data.repository

import android.util.Log
import com.sultonuzdev.andersentask.data.local.ProductDao
import com.sultonuzdev.andersentask.data.mapper.toDomain
import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product
import com.sultonuzdev.andersentask.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {
    override suspend fun getAllCategories(): List<Category> {
        return  dao.getAllCategories().map { it.toDomain() }
    }

    override suspend fun getProductsByCategory(categoryId: Int): List<Product> {
        return dao.getProductsByCategory(categoryId).map { it.toDomain() }
    }

    override fun searchProducts(
        categoryId: Int,
        query: String
    ): Flow<List<Product>> {
        return dao.searchProducts(categoryId, query).map { it.toDomain() }
    }

    override suspend fun getFirstImagesOfEachCategory(): List<String> {
        val categories = dao.getAllCategories()
        val firstImages = mutableListOf<String>()
        for (category in categories) {
            val products = dao.getProductsByCategory(category.id)
            if (products.isNotEmpty()) {
                firstImages.add(products.first().image)
            }
        }
        return firstImages
    }


}