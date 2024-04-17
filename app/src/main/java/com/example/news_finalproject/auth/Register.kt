package com.example.news_finalproject.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news_finalproject.Destination
import com.google.firebase.auth.FirebaseAuth

// If a user clicks on the Register button, they should be redirected to this screen
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(navController: NavController) {

    // define variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(top = 16.dp)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // text
        Text(
            "Register",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(75.dp))

        // email text field
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        // password text field
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        // confirm password text field
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = {
                // Perform Firebase registration
                performRegistration(email, password, confirmPassword, context, navController, keyboardController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Register")
        }
    }
}

// If the password and confirm password text fields match, use the built in firebase method, createUserWithEmailAndPassword() to handle the register function
@OptIn(ExperimentalComposeUiApi::class)
private fun performRegistration(
    email: String,
    password: String,
    confirmPassword: String,
    context: Context,
    navController: NavController,
    keyboardController: SoftwareKeyboardController?,
) {
    if (password != confirmPassword) {
        // Passwords do not match, show error message
        Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
        return
    }

    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registration success, navigate to the next screen or perform desired action
                Toast.makeText(context, "Registration successful. Now logged in as $email", Toast.LENGTH_SHORT).show()
                // Navigate to the home screen or any other screen
                navController.navigate(Destination.Article.route)
            } else {
                // If registration fails, display a message to the user.
                Toast.makeText(context, "Registration failed. Password must contain at least 6 characters", Toast.LENGTH_SHORT).show()
            }

            // Hide the keyboard
            keyboardController?.hide()
        }
}
