package com.sultonuzdev.andersentask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultonuzdev.andersentask.domain.model.Product
import com.sultonuzdev.andersentask.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProductScreenState())
    val state: StateFlow<ProductScreenState> = _state.asStateFlow()


    private val searchQueryFlow = MutableStateFlow("")


    init {
        viewModelScope.launch {
            loadCategories()
            setupSearchFlow()

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private suspend fun setupSearchFlow() {
        searchQueryFlow.debounce(300).distinctUntilChanged().flatMapLatest { query ->
            if (query.isEmpty()) {
                flowOf(_state.value.currentCategoryProducts)
            } else {
                repository.searchProducts(
                    categoryId = _state.value.currentCategoryId, query = query
                )
            }
        }.collectLatest { result ->
            _state.update { it.copy(filteredProducts = result) }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val categories = repository.getAllCategories()
            val firstImages = repository.getFirstImagesOfEachCategory()

            _state.update { it.copy(categories = categories, categoryImages = firstImages) }

            if (categories.isNotEmpty()) {
                loadProductsForPage(0)
            }
        }
    }

    fun onPageChange(page: Int) {
        if (page == _state.value.currentPage) return
        loadProductsForPage(page)
    }

    fun onSearchQueryChange(query: String) {
        viewModelScope.launch {
            searchQueryFlow.value = query
            _state.update { it.copy(searchQuery = query) }
        }
    }


    private fun loadProductsForPage(pageIndex: Int) {
        val category = _state.value.categories.getOrNull(pageIndex) ?: return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentPage = pageIndex,
                    isLoading = true,
                    currentCategoryId = category.id,
                )
            }
            val products = repository.getProductsByCategory(category.id)
            _state.update { currentState ->
                currentState.copy(
                    currentCategoryProducts = products,
                    filteredProducts = products,
                    searchQuery = "",
                    isLoading = false,
                    categoryProductStatistics = currentState.categoryProductStatistics.copy(
                        categoryName = category.title,
                        totalProduct = products.size,
                        topCharacter = calculateTopOccurrenceCharacter(products)
                    )
                )
            }
        }
    }


    fun calculateTopOccurrenceCharacter(
        products: List<Product>
    ): Map<Char, Int> {
        val countMap = mutableMapOf<Char, Int>()
        products.forEach { productItem ->
            productItem.title.lowercase().forEach { char ->
                if (char.isLetter()) {
                    countMap[char] = countMap.getOrDefault(char, 0) + 1
                }
            }
        }
        return countMap.toList().sortedByDescending { it.second }.take(3).toMap()
    }
}