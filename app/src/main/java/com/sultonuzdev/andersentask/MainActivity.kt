package com.sultonuzdev.andersentask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sultonuzdev.andersentask.presentation.ProductScreen
import com.sultonuzdev.andersentask.core.ui.theme.AndersenTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndersenTaskTheme {
                ProductScreen()
            }
        }
    }
}
