package com.vinb.grocerystore.store

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vinb.grocerystore.MyApp
import com.vinb.grocerystore.R
import com.vinb.grocerystore.data.Product
import com.vinb.grocerystore.data.products
import com.vinb.grocerystore.data.shoppingList
import com.vinb.grocerystore.navigation.Screen
import com.vinb.grocerystore.ui.theme.GroceryStoreTheme
import com.vinb.grocerystore.ui.theme.black
import com.vinb.grocerystore.ui.theme.ghostWhite
import com.vinb.grocerystore.ui.theme.gold
import com.vinb.grocerystore.ui.theme.lightSilver
import com.vinb.grocerystore.ui.theme.platinum
import com.vinb.grocerystore.ui.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProductListScreen(navController: NavController) {

    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val filteredProducts = products.filter { product ->
        product.name.contains(searchText, ignoreCase = true) || product.description.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            Toast.makeText(context, "Feature Under Development", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(modifier = Modifier.size(22.dp), painter = painterResource(id = R.drawable.filter), contentDescription = "filter")
                        }

                        Box {
                            IconButton(onClick = {
                                navController.navigate(Screen.ShoppingScreen.route)
                            }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping Cart")
                            }
                            if (shoppingList.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = ((-8).dp), y = ((-8).dp))
                                        .size(24.dp)
                                        .background(gold, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = shoppingList.count().toString(),
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        IconButton(
                            modifier = Modifier
                                .padding(5.dp)
                                .border(width = 1.dp, color = Color(0xFFFFD700), shape = CircleShape), // gold color
                            onClick = {
                                navController.navigate(Screen.AccountScreen.route)
                            }) {
                            Icon(modifier = Modifier.size(22.dp), painter = painterResource(id = R.drawable.user), contentDescription = "Increase Quantity")
                        }
                    }
                }
            })
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search
            item {
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = ghostWhite,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    value = searchText,
                    shape = RoundedCornerShape(32.dp),
                    singleLine = true,
                    onValueChange = { searchText = it },
                    placeholder = {
                        Text(
                            text = "Search",
                            color = platinum
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.text_search_icon),
                            tint = lightSilver
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Product list
            item {
                FlowRow(maxItemsInEachRow = 2) {
                    filteredProducts.forEach { product ->
                        ProductListItem(product) {
                            navController.navigate("product_details/${product.id}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductListItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            containerColor = product.cardBg.copy(alpha = 0.8f),
            contentColor = black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = black
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(84.dp),
                    painter = painterResource(product.image),
                    contentDescription = "product image",
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Text(
                        text = product.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = black,
                    )
                    Text(
                        text = "Kes. ${product.price}/kg",
                        fontSize = 11.sp,
                        color = black,
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(gold)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp, 16.dp),
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = stringResource(R.string.text_add_icon),
                        tint = white
                    )
                }

            }

        }

        /*Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = "Kes. ${product.price}/Kg")
            }
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroceryStoreTheme {
        MyApp(Firebase.auth)
    }
}
