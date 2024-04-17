package com.example.news_finalproject.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import com.example.news_finalproject.MainActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val context = LocalContext.current

    if (currentUser != null) {
        // User is logged in
        LoggedInScreen(currentUser.email ?: "Unknown", auth, navController)
    } else {
        // User is not logged in
        SignInScreen(navController)
    }
}

@Composable
fun LoggedInScreen(email: String, auth: FirebaseAuth, navController: NavController) {
    // If the user is logged in, display logged-in information
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Currently logged in as $email",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                auth.signOut()
                navController.navigate("signInRoute") {
                    popUpTo("homeRoute") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
    }

}

@Composable
fun LogOutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "You've successfully logged out.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                // Navigate back to the sign-in screen or home screen
                navController.navigate("signInRoute") {
                    popUpTo("homeRoute") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to Sign In")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            "Log In",
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

        Button(
            onClick = {
                // Perform Firebase authentication
                performSignIn(email, password, context, navController, keyboardController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Sign In")
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
private fun performSignIn(
    email: String,
    password: String,
    context: Context,
    navController: NavController,
    keyboardController: SoftwareKeyboardController?,
) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, navigate to the next screen or perform desired action
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                // navigate to home screen
                navController.navigate(Destination.Article.route)

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }

            //loading = false
            keyboardController?.hide()
        }
}