package com.sultonuzdev.andersentask.presentation.viewmodel

import com.sultonuzdev.andersentask.domain.model.Product
import com.sultonuzdev.andersentask.domain.repository.FakeProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)

class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var viewModel: MainViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeProductRepository = FakeProductRepository()
        viewModel = MainViewModel(fakeProductRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `onPageChange updates state correctly`() = runTest {
        advanceUntilIdle()

        viewModel.onPageChange(1)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(1, state.currentPage)
        assertEquals(fakeProductRepository.getProductsByCategory(2), state.currentCategoryProducts)
    }


    @Test
    fun `after page change and reset searchQuery and filteredProducts`() = runTest {
        advanceUntilIdle()

        viewModel.onSearchQueryChange("Lenovo")
        advanceTimeBy(350)
        advanceUntilIdle()


        viewModel.onPageChange(1)
        advanceUntilIdle()

        assertEquals("", viewModel.state.value.searchQuery)


    }


    @Test
    fun `searchQueryChange updates state correctly and filteredProducts`() = runTest {

        advanceUntilIdle()

        viewModel.onSearchQueryChange("MacBook")
        advanceTimeBy(350)
        advanceUntilIdle()


        val state = viewModel.state.value
        assertEquals("MacBook", state.searchQuery)


        val filteredProducts = fakeProductRepository.testProducts.filter { product ->
            product.title.contains(
                "MacBook",
                ignoreCase = true
            ) || product.subtitle.contains("MacBook", ignoreCase = true)
        }

        assertEquals(filteredProducts, state.filteredProducts)

    }


    @Test
    fun `empty searchQueryChange restore state correctly and filteredProducts`() = runTest {
        advanceUntilIdle()

        viewModel.onSearchQueryChange("")
        advanceTimeBy(350)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("", state.searchQuery)


        val filteredProducts = fakeProductRepository.testProducts

        assertEquals(filteredProducts, state.filteredProducts)

    }


    @Test
    fun `search no matching products and return empty list`() = runTest {
        advanceUntilIdle()
        viewModel.onSearchQueryChange("NonExistentProduct")
        advanceTimeBy(350)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("NonExistentProduct", state.searchQuery)
        assertTrue(state.filteredProducts.isEmpty())

    }


    @Test
    fun `calculateTopOccurrenceCharacter returns correct result`() = runTest {
        val testProducts = listOf(
            Product(id = 1, title = "aaabbb", subtitle = "ccc", categoryId = 1, image = ""),
            Product(id = 2, title = "aaa", subtitle = "bb", categoryId = 1, image = "")
        )
        val result = viewModel.calculateTopOccurrenceCharacter(testProducts)
        assertEquals(3, result.size)

        val keys = result.keys.toList()
        assertEquals('a', keys[0])
        assertEquals('b', keys[1])
        assertEquals('c', keys[2])

    }


    @Test
    fun `calculateTopOccurrenceCharacter returns empty map for symbols and spaces`() = runTest {
        val testProducts = listOf(
            Product(id = 1, title = " ", subtitle = " ", categoryId = 1, image = ""),
            Product(id = 2, title = "!@#$@#$@", subtitle = ")(*^^**%%%", categoryId = 1, image = "")
        )

        val result = viewModel.calculateTopOccurrenceCharacter(testProducts)
        assertEquals(0, result.size)

    }


}

