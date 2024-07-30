package com.vinb.grocerystore.data

import androidx.compose.ui.graphics.Color

private var nextId = 1

data class Product(
    val id: Int,
    val image: Int,
    val name: String,
    val price: Double,
    val cardBg: Color,
    val description: String
)
fun Product.toShopping(quantity: Int): Shopping {
//    val newId = if (shoppingList.toList().isNotEmpty()) {
//        shoppingList.size + 1 } else 0
    return Shopping(
        id = this.id,
        image = this.image,
        name = this.name,
        price = this.price,
        cardBg = this.cardBg,
        quantity = quantity
    )
}

fun Product.addToCart(quantity: Int): Boolean {
    addToCart(this.toShopping(quantity))
    return true
}

private fun getNextId(): Int {
    return nextId++
}