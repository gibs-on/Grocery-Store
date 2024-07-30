package com.vinb.grocerystore.navigation

import androidx.annotation.StringRes
import com.vinb.grocerystore.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Dashboard : Screen("dashboard", R.string.dashboard)
    data object ProductDetail : Screen("product_detail", R.string.product_detail)
    data object ShoppingScreen : Screen("shopping_screen", R.string.shopping)
    data object CheckOutScreen : Screen("checkout_screen", R.string.shopping)
    data object AccountScreen : Screen("account_screen", R.string.account)

}