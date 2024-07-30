package com.vinb.grocerystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vinb.grocerystore.auth.AccountScreen
import com.vinb.grocerystore.auth.LoginPage
import com.vinb.grocerystore.auth.RegistrationPage
import com.vinb.grocerystore.data.products
import com.vinb.grocerystore.navigation.Screen
import com.vinb.grocerystore.store.CheckoutScreen
import com.vinb.grocerystore.store.ProductDetailsScreen
import com.vinb.grocerystore.store.ProductListScreen
import com.vinb.grocerystore.store.ShoppingCartScreen
import com.vinb.grocerystore.ui.theme.GroceryStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize Firebase Auth
        val auth: FirebaseAuth = Firebase.auth
        setContent {
            GroceryStoreTheme {
                MyApp(auth)
//                NavGraph()
            }
        }
    }
}

//handle navigation
@Composable
fun MyApp(auth: FirebaseAuth = Firebase.auth) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController, auth) }
        composable("register") { RegistrationPage(navController, auth) }
        composable("product_list") { ProductListScreen(navController) }
        composable("product_details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
            val product = products.find { it.id == productId }
            if (product != null) {
                ProductDetailsScreen(navController = navController, product = product)
            }
        }
        composable(Screen.ShoppingScreen.route) { ShoppingCartScreen(navController) }
        composable(Screen.CheckOutScreen.route) { CheckoutScreen(navController) }
        composable(Screen.AccountScreen.route) { AccountScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroceryStoreTheme {
        MyApp()
    }
}