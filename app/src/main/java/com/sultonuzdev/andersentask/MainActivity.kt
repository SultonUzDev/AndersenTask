package com.sultonuzdev.andersentask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sultonuzdev.andersentask.core.ui.theme.AndersenTaskTheme
import com.sultonuzdev.andersentask.presentation.ProductScreen
import com.sultonuzdev.andersentask.presentation.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val container by lazy { (application as MainApplication).container }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndersenTaskTheme {
                ProductScreen(
                    viewModel = viewModel(factory = MainViewModelFactory(container.repository))
                )
            }
        }
    }
}
