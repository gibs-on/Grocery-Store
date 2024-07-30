package com.vinb.grocerystore.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.vinb.grocerystore.R
import com.vinb.grocerystore.ui.theme.navajoWhite

val initialShoppingList = listOf(
    products[1].toShopping(2),
    products[8].toShopping(3),
    products[5].toShopping(1),

)

val shoppingList = mutableStateListOf<Shopping>().apply {
    addAll(initialShoppingList)
}

fun addToCart(shopping: Shopping) {
    shoppingList.add(shopping)
}

fun updateCartItem(id: Int, newQuantity: Int) {
    val index = shoppingList.indexOfFirst { it.id == id }
    if (index != -1) {
        shoppingList[index] = shoppingList[index].copy(quantity = newQuantity)
    }
}

fun removeFromCart(shopping: Shopping) {
    shoppingList.remove(shopping)
}
fun clearShoppingCart() {
    shoppingList.clear()
}