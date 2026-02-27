package com.sultonuzdev.andersentask.domain.model

data class Product(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val image: String,
    val subtitle: String
)