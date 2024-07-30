package com.vinb.grocerystore.store

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vinb.grocerystore.R
import com.vinb.grocerystore.data.Product
import com.vinb.grocerystore.data.addToCart
import com.vinb.grocerystore.data.products
import com.vinb.grocerystore.data.shoppingList
import com.vinb.grocerystore.ui.theme.GroceryStoreTheme
import com.vinb.grocerystore.ui.theme.black
import com.vinb.grocerystore.ui.theme.gold
import com.vinb.grocerystore.ui.theme.lightSilver
import com.vinb.grocerystore.ui.theme.white
import kotlin.time.times

@Composable
fun ProductDetailsScreen(navController: NavController, product: Product) {
    val shopping = shoppingList.find { it.id == product.id }
    val quantity = remember{ mutableIntStateOf( shopping?.quantity ?: 1) }
    val context = LocalContext.current

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(product.cardBg)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(white),
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = stringResource(R.string.text_back_icon),
                    )
                }
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(white),
                    onClick = {
                        Toast.makeText(context, "Feature under Development", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.text_heart_icon),
                        tint = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = product.image),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(44.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .background(if (isSystemInDarkTheme()) black else white)
                    .padding(24.dp)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Kes. ${product.price}/Kg",
                        color = gold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = product.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(28.dp))
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
                            text = "Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = black,
                        )
                        Text(
                            text = "Kes. ${product.price * quantity.intValue}/Kg",
                            fontSize = 14.sp,
                            color = gold,
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        if(quantity.intValue > 1) {
                            IconButton(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(lightSilver),
                                onClick = {
                                    quantity.intValue--
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp, 20.dp),
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = stringResource(R.string.text_add_icon),
                                    tint = white
                                )
                            }
                        }
                        IconButton(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(lightSilver),
                            onClick = {
                                quantity.intValue++
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp, 20.dp),
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.text_add_icon),
                                tint = white
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    enabled = shopping == null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = gold,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick = { product.addToCart(quantity = quantity.intValue) }) {
                    Text(
                        text = "Add To Cart",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsPreview() {
    GroceryStoreTheme {
        ProductDetailsScreen(rememberNavController(), product = products[0])
    }
}
