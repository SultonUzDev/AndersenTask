package com.sultonuzdev.andersentask.domain.repository


import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product
import com.sultonuzdev.andersentask.domain.model.SampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductRepository : ProductRepository {

    val testCategories = SampleData.categoryLists
    val testProducts = SampleData.productLists



    override suspend fun getAllCategories(): List<Category> = testCategories


    override suspend fun getProductsByCategory(categoryId: Int): List<Product> {
        return testProducts.filter { it.categoryId == categoryId }
    }

    override fun searchProducts(
        categoryId: Int,
        query: String
    ): Flow<List<Product>> {
        val categoryProducts = testProducts.filter { it.categoryId == categoryId }
        val filteredProducts = categoryProducts.filter {
            it.title.contains(query, ignoreCase = true)
                    || it.subtitle.contains(query, ignoreCase = true)
        }
        return flowOf(filteredProducts)
    }

    override suspend fun getFirstImagesOfEachCategory(): List<String> {
        return testProducts.groupBy { it.categoryId }.map { (_, products) -> products.first().image }
    }
}
