package com.sultonuzdev.andersentask.presentation

import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product

data class ProductScreenState(

    val categories: List<Category> = emptyList(),
    val currentCategoryId: Int=0,
    val categoryImages: List<String> = emptyList(),
    val currentCategoryProducts: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val categoryProductStatistics: ProductStatistics = ProductStatistics(),
    val error: String? = null
)

data class ProductStatistics(
    val categoryName: String = "",
    val totalProduct: Int = 0,
    val topCharacter: Map<Char, Int> = emptyMap(),
)
