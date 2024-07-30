package com.vinb.grocerystore.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

suspend fun signIn(auth: FirebaseAuth, email: String, password: String): String? {
    return try {
        auth.signInWithEmailAndPassword(email, password).await()
        null
    } catch (e: Exception) {
        e.message
    }
}

suspend fun signUp(auth: FirebaseAuth, email: String, password: String): String? {
    return try {
        auth.createUserWithEmailAndPassword(email, password).await()
        null
    } catch (e: Exception) {
        e.message
    }
}