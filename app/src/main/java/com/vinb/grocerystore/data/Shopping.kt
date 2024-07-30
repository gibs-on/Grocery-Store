package com.vinb.grocerystore.data

import androidx.compose.ui.graphics.Color

data class Shopping(
    val id: Int,
    val image: Int,
    val name: String,
    val price: Double,
    val cardBg: Color,
    var quantity: Int,
)