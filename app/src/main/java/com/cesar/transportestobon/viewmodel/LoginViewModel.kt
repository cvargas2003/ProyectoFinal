package com.cesar.transportestobon.viewmodel

import androidx.lifecycle.ViewModel
import com.cesar.transportestobon.utils.FirebaseManager

class LoginViewModel : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        FirebaseManager.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Error al iniciar sesión")
            }
    }
}