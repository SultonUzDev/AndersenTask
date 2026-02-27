package com.sultonuzdev.andersentask.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.sultonuzdev.andersentask.core.ui.theme.AndersenTaskTheme
import com.sultonuzdev.andersentask.core.ui.theme.ComponentSize
import com.sultonuzdev.andersentask.core.ui.theme.FloatButtonBlueColor
import com.sultonuzdev.andersentask.core.ui.theme.GreenishBackground
import com.sultonuzdev.andersentask.core.ui.theme.Spaces
import com.sultonuzdev.andersentask.domain.model.categoryLists
import com.sultonuzdev.andersentask.domain.model.productLists
import com.sultonuzdev.andersentask.presentation.components.CategoryStatistics
import com.sultonuzdev.andersentask.presentation.components.ImagePager
import com.sultonuzdev.andersentask.presentation.components.ProductItemScreen
import com.sultonuzdev.andersentask.presentation.components.SearchBar
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    viewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()



    ProductScreenContent(
        uiState = uiState,
        onSearchQueryChanged = { newQuery ->
            viewModel.onSearchQueryChange(newQuery)
        },
        onPagerPositionChanged = { newPosition ->
            viewModel.onPageChange(newPosition)
        })


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ProductScreenContent(
    modifier: Modifier = Modifier,
    uiState: ProductScreenState,
    onPagerPositionChanged: (Int) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
            )
        },
        containerColor = GreenishBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                shape = CircleShape,
                containerColor = FloatButtonBlueColor,

                ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
        }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Spaces.s),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item(key = "imagePager") {
                ImagePager(
                    modifier = Modifier.padding(horizontal = Spaces.xs),
                    currentPage = uiState.currentPage,
                    imagesSize = uiState.categoryImagesSize,
                    imageSource = uiState.currentCategoryImage,
                    onPageChanged = { newPosition ->
                        onPagerPositionChanged(newPosition)
                    })
            }


            stickyHeader("searchBar") {
                Box(
                    modifier = Modifier
                        .height(ComponentSize.searchBarHeight)
                        .background(GreenishBackground)
                        .padding(horizontal = Spaces.m),
                    contentAlignment = Alignment.Center
                ) {
                    SearchBar(
                        searchQuery = uiState.searchQuery,
                        onSearchQueryChanged = { newQuery ->
                            onSearchQueryChanged(newQuery)
                        }, modifier = Modifier.fillMaxWidth()
                    )

                }
            }
            if (!uiState.isLoading) {

                items(
                    items = uiState.filteredProducts,
                    key = { product -> product.id }
                ) { productItem ->
                    ProductItemScreen(
                        modifier = Modifier.padding(horizontal = Spaces.m),
                        product = productItem
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Spaces.xl))
            }


        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {
                CategoryStatistics(
                    statistics = uiState.categoryStatistics,
                )
            }
        }


    }

}


@Preview
@Composable
private fun ProductScreenPreview() {
    AndersenTaskTheme {
        ProductScreenContent(
            uiState = ProductScreenState(
                categories = categoryLists,
                currentCategoryProducts = productLists,
                currentPage = 0,
                filteredProducts = productLists
            ),
            onPagerPositionChanged = {},
            onSearchQueryChanged = {}
        )
    }
}