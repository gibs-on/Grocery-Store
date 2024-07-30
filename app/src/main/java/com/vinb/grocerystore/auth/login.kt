package com.vinb.grocerystore.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vinb.grocerystore.navigation.Screen
import com.vinb.grocerystore.ui.theme.GroceryStoreTheme
import com.vinb.grocerystore.ui.theme.gold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController, auth: FirebaseAuth = Firebase.auth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                    loginError = null
                },
                label = { Text("Email") },
                isError = emailError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
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
            if (emailError != null) {
                Text(emailError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                    loginError = null
                },
                label = { Text("Password") },
                isError = passwordError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
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
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (passwordError != null) {
                Text(passwordError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = gold,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                onClick = {
                    var isValid = true
                    if (email.isBlank()) {
                        emailError = "Email cannot be empty"
                        isValid = false
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Invalid email address"
                        isValid = false
                    }
                    if (password.isBlank()) {
                        passwordError = "Password cannot be empty"
                        isValid = false
                    }
                    if (isValid) {
                        coroutineScope.launch {
                            val error = signIn(auth, email, password)
                            if (error == null) {
                                navController.navigate("product_list") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                loginError = error
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            if (loginError != null) {
                Text(loginError!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                colors = ButtonDefaults.buttonColors(
                    contentColor = gold,
                    containerColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Don't have an account? Register")
            }
        }
    }
}


@Preview
@Composable
private fun LoginPagePreview() {
    GroceryStoreTheme {
        LoginPage(navController = rememberNavController())
    }

}
