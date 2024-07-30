package com.vinb.grocerystore.store

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vinb.grocerystore.R
import com.vinb.grocerystore.data.Shopping
import com.vinb.grocerystore.data.shoppingList
import com.vinb.grocerystore.navigation.Screen
import com.vinb.grocerystore.ui.theme.gold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(navController: NavHostController) {

    var showSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showSheet) {
        BottomSheet() { provider ->
            when (provider) {
                "Vin-B Pay" -> {
                    navController.navigate(Screen.CheckOutScreen.route)
                }
                "M-Pesa" -> {
                    Toast.makeText(context, "Feature Under Development", Toast.LENGTH_SHORT).show()
                }
                "Paypal" -> {
                    Toast.makeText(context, "Feature Under Development", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
            showSheet = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Shopping Cart", fontWeight = FontWeight.Bold) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            shoppingList.apply {
//                addAll(initialShoppingList)
            }
            ShoppingCart(shoppingList)

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                enabled = shoppingList.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gold
                ),
                onClick = { showSheet = true }) {
                Text(
                    text = "CheckOut",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ShoppingCart(cartItems: MutableList<Shopping>) {
    LazyColumn {
        items(cartItems) { item ->
            CartItemRow(item, onRemove = { cartItems.remove(item) }, onUpdateQuantity = { newQuantity ->
                val index = cartItems.indexOf(item)
                if (index != -1) {
                    cartItems[index] = item.copy(quantity = newQuantity)
                }
            })
        }
    }
}

@Composable
fun CartItemRow(product: Shopping, onRemove: () -> Unit, onUpdateQuantity: (Int) -> Unit) {
    var quantity by remember { mutableIntStateOf(product.quantity) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(product.cardBg.copy(alpha = 0.5f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(product.image),
                contentDescription = "product image",
            )
        }
        Text(product.name, style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (quantity > 1) {
                    quantity -= 1
                    onUpdateQuantity(quantity)
                }
            }) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity")
            }
            Text(quantity.toString(), style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = {
                quantity += 1
                onUpdateQuantity(quantity)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
            }
        }
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Remove Item")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: (String) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss("") },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        data class PaymentMethod (
            val id: Int,
            val logo: Int,
            val provider: String,
        )
        val paymentMethods = listOf(
            PaymentMethod(id = 1, logo = R.drawable.vinb_pay_logo, provider = "Vin-B Pay"),
            PaymentMethod(id = 2, logo = R.drawable.mpesa_logo, provider = "M-Pesa"),
            PaymentMethod(id = 3, logo = R.drawable.paypal_logo, provider = "Paypal")
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(paymentMethods) { paymentMethod ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable { onDismiss(paymentMethod.provider) },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .padding(8.dp),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .aspectRatio(2f / 1f),
                                    painter = painterResource(paymentMethod.logo),
                                    contentDescription = "product image",
                                )
                            }
                            Text(
                                paymentMethod.provider,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CheckoutBottomSheetContent(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Checkout", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        // Add other UI elements here for checkout, e.g., order summary, payment info
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                // Handle checkout logic
                onDismiss() // Close the bottom sheet
            }
        ) {
            Text(text = "Confirm Order")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingCartPreview() {
    ShoppingCartScreen(rememberNavController())
}