package com.sultonuzdev.andersentask.data.mapper

import com.sultonuzdev.andersentask.data.local.entities.CategoryEntity
import com.sultonuzdev.andersentask.data.local.entities.ProductEntity
import com.sultonuzdev.andersentask.domain.model.Category
import com.sultonuzdev.andersentask.domain.model.Product


fun CategoryEntity.toDomain(
): Category {
    return Category(
        id = this.id,
        title = this.title,
    )
}


fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        categoryId = this.categoryId,
        title = this.title,
        subtitle = this.subTitle,
        image = this.image

    )
}

fun List<ProductEntity>.toDomain(): List<Product> {
    return this.map { it.toDomain() }
}