package com.sultonuzdev.andersentask.presentation

import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product

data class ProductScreenState(

    val categories: List<Category> = emptyList(),
    val currentCategoryId: Int = 1,
    val categoryImagesSize: Int = 0,
    val currentCategoryImage: String? = null,
    val currentCategoryProducts: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val categoryStatistics: Statistics = Statistics(),
    val error: String? = null
)

data class Statistics(
    val categoryName: String = "",
    val totalProduct: Int = 0,
    val topCharacter: Map<Char, Int> = emptyMap(),
)
