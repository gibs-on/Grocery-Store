package com.vinb.grocerystore.data

import androidx.compose.ui.graphics.Color
import com.vinb.grocerystore.R
import com.vinb.grocerystore.ui.theme.aliceBlue
import com.vinb.grocerystore.ui.theme.azureishWhite
import com.vinb.grocerystore.ui.theme.cultured
import com.vinb.grocerystore.ui.theme.seashell

val products = listOf(
    Product(id = 1, image = R.drawable.apple_fruit, name = "Apples", price = 26.50, cardBg = Color(0xFFFFDDC1), description = "Fresh, juicy apples perfect for snacks or baking."),
    Product(id = 2, image = R.drawable.banana_fruit, name = "Bananas", price = 13.50, cardBg = Color(0xFFE2F2A6), description = "Ripe bananas, ideal for smoothies or a quick snack."),
    Product(id = 3, image = R.drawable.carrot, name = "Carrot", price = 12.00, cardBg = seashell, description = ""),
    Product(id = 4, image = R.drawable.tomato, name = "Tomato", price = 13.50, cardBg = aliceBlue, description = ""),
    Product(id = 5, image = R.drawable.pumpkin, name = "Pumpkin", price = 9.60, cardBg = cultured,""),
    Product(id = 6, image = R.drawable.cauliflower, name = "Cauliflower", price = 10.00, cardBg = azureishWhite, description = ""),
    Product(id = 7, image = R.drawable.red_capsicum, name = "Capsicum", price = 30.10, cardBg = seashell, description = ""),
    Product(id = 8, image = R.drawable.onion, name = "Onion", price = 25.00, cardBg = aliceBlue, description = ""),
    Product(id = 9, image = R.drawable.potato, name = "Potato", price = 13.60, cardBg = azureishWhite, description = "")
)
