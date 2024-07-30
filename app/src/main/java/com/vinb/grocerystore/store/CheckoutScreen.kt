package com.vinb.grocerystore.store

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vinb.grocerystore.R
import com.vinb.grocerystore.data.shoppingList
import com.vinb.grocerystore.navigation.Screen
import com.vinb.grocerystore.ui.theme.GroceryStoreTheme
import com.vinb.grocerystore.ui.theme.gold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
    val context = LocalContext.current
    val loading = remember { mutableStateOf(false) }
    val orderSuccess = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var address by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var addressError by remember { mutableStateOf(false) }
    var cardNumberError by remember { mutableStateOf(false) }
    var expiryDateError by remember { mutableStateOf(false) }
    var cvvError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.checkout), fontWeight = FontWeight.Bold)

                    IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Shopping Cart")
                        }

                }
            })
        }
    ) { paddingValues ->
        when {
            loading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), color = Color(0xFFFFD700)) // gold color
                }
            }
            orderSuccess.value -> {
                OrderSuccessView(navController = navController)
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        // Order Summary
                        Text(text = "Order Summary", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        // Replace with your own logic to display items and total price
                        OrderSummary()

                        Spacer(modifier = Modifier.height(24.dp))

                        // Delivery Information
                        Text(text = "Delivery Information", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = address,
                            onValueChange = {
                                address = it
                                addressError = it.isBlank()
                            },
                            label = { Text("Address") },
                            isError = addressError,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                cursorColor = gold,
                                selectionColors = TextSelectionColors(
                                    handleColor = gold,
                                    backgroundColor = gold.copy(alpha = .3f)
                                ),
                                focusedLabelColor = gold,
                                unfocusedContainerColor = gold.copy(alpha = .1f),
                                unfocusedIndicatorColor = gold,
                                focusedContainerColor = gold.copy(alpha = .3f),
                                focusedIndicatorColor = gold
                            )
                        )
                        if (addressError) {
                            Text(text = "Address cannot be empty", color = Color.Red, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Payment Information
                        Text(text = "Payment Information", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = cardNumber,
                            onValueChange = {
                                cardNumber = it
                                cardNumberError = !it.matches(Regex("\\d{16}"))
                            },
                            label = { Text("Card Number") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            isError = cardNumberError,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                cursorColor = gold,
                                selectionColors = TextSelectionColors(
                                    handleColor = gold,
                                    backgroundColor = gold.copy(alpha = .3f)
                                ),
                                focusedLabelColor = gold,
                                unfocusedContainerColor = gold.copy(alpha = .1f),
                                unfocusedIndicatorColor = gold,
                                focusedContainerColor = gold.copy(alpha = .3f),
                                focusedIndicatorColor = gold
                            )
                        )
                        if (cardNumberError) {
                            Text(text = "Enter a valid 16-digit card number", color = Color.Red, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = expiryDate,
                            onValueChange = {
                                expiryDate = it
                                expiryDateError = !it.matches(Regex("\\d{2}/\\d{2}"))
                            },
                            label = { Text("Expiry Date (MM/YY)") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            isError = expiryDateError,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                cursorColor = gold,
                                selectionColors = TextSelectionColors(
                                    handleColor = gold,
                                    backgroundColor = gold.copy(alpha = .3f)
                                ),
                                focusedLabelColor = gold,
                                unfocusedContainerColor = gold.copy(alpha = .1f),
                                unfocusedIndicatorColor = gold,
                                focusedContainerColor = gold.copy(alpha = .3f),
                                focusedIndicatorColor = gold
                            )
                        )
                        if (expiryDateError) {
                            Text(text = "Enter a valid expiry date (MM/YY)", color = Color.Red, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = cvv,
                            onValueChange = {
                                cvv = it
                                cvvError = !it.matches(Regex("\\d{3}"))
                            },
                            label = { Text("CVV") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            isError = cvvError,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                cursorColor = gold,
                                selectionColors = TextSelectionColors(
                                    handleColor = gold,
                                    backgroundColor = gold.copy(alpha = .3f)
                                ),
                                focusedLabelColor = gold,
                                unfocusedContainerColor = gold.copy(alpha = .1f),
                                unfocusedIndicatorColor = gold,
                                focusedContainerColor = gold.copy(alpha = .3f),
                                focusedIndicatorColor = gold
                            )
                        )
                        if (cvvError) {
                            Text(text = "Enter a valid 3-digit CVV", color = Color.Red, fontSize = 12.sp)
                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            shape = RoundedCornerShape(24.dp),
                            enabled = validateFields(address, cardNumber, expiryDate, cvv),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = gold,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            ),
                            onClick = {
                                if (validateFields(address, cardNumber, expiryDate, cvv)) {
                                    loading.value = true
                                    scope.launch {
                                        delay(2000)
                                        orderSuccess.value = true
                                        loading.value = false
                                    }
                                } else {
                                    Toast.makeText(context, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = "Confirm Order")
                        }
                    }
                }
            }
        }
    }
}

private fun validateFields(address: String, cardNumber: String, expiryDate: String, cvv: String): Boolean {
    val isAddressValid = address.isNotBlank()
    val isCardNumberValid = cardNumber.matches(Regex("\\d{16}"))
    val isExpiryDateValid = expiryDate.matches(Regex("\\d{2}/\\d{2}"))
    val isCvvValid = cvv.matches(Regex("\\d{3}"))

    return isAddressValid && isCardNumberValid && isExpiryDateValid && isCvvValid
}

@Composable
fun OrderSummary() {
    val totalCost = remember {
        mutableDoubleStateOf(0.00)
    }

    shoppingList.forEach { item ->
        totalCost.doubleValue += (item.price * item.quantity)
        Text(text = " - ${item.name} x ${item.quantity} - kes. ${item.price * item.quantity}")
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Total Price
    Text(
        text = "Total: Kes. ${totalCost.doubleValue}",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Preview
@Composable
fun CheckOutScreenPreview() {
    GroceryStoreTheme {
        CheckoutScreen(rememberNavController())
    }
}

@Composable
fun OrderSuccessView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Success Message
        Text(
            text = "Your order has been placed successfully!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Success Icon (You can use any drawable or image here)
        Icon(
            imageVector = Icons.Default.CheckCircle,
//            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = "Success",
            modifier = Modifier.size(120.dp),
            tint = gold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button to Return to Home or Perform Other Actions
        Button(
            onClick = {
                // Navigate back to the home screen or another screen
                shoppingList.clear()
                navController.navigate("product_list") {
                    popUpTo(route = Screen.CheckOutScreen.route) {
                        inclusive = true
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = gold
            ),
            modifier = Modifier
        ) {
            Text("Return to Home")
        }
    }
}

@Preview
@Composable
fun SuccessViewPreview() {
    GroceryStoreTheme {
        OrderSuccessView(navController = rememberNavController())
    }
}