package com.example.ecopav.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecopav.ui.theme.*

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onExploreAsGuest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo and Branding
        Image(
            painter = painterResource(id = com.example.ecopav.R.drawable.ic_logo_ecopave),
            contentDescription = "Logo EcoPavé",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "EcoPavé",
            style = MaterialTheme.typography.headlineMedium,
            color = Slate,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Valorisons nos déchets plastiques pour bâtir un avenir durable.",
            style = MaterialTheme.typography.bodyLarge,
            color = Slate.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(64.dp))
        
        Button(
            onClick = onNavigateToRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Slate),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Créer un compte", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(1.dp, Slate),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Se connecter", color = Slate, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        TextButton(onClick = onExploreAsGuest) {
            Text(
                "Explorer en mode invité",
                color = EcoGreenDark,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Se connecter",
            style = MaterialTheme.typography.headlineMedium,
            color = Slate,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Google Login Button
        OutlinedButton(
            onClick = { /* Simulated Google Login */ onLoginSuccess() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            border = BorderStroke(1.dp, BorderLine)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = com.example.ecopav.R.drawable.ic_google_logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continuer avec Google", color = Slate)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            "Ou avec vos identifiants",
            style = MaterialTheme.typography.labelMedium,
            color = Slate.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = identifier,
            onValueChange = { identifier = it },
            label = { Text("Email ou Téléphone") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Slate),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Se connecter", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = {
                identifier = "kanigui@ecopave.bj"
                password = "demo_password"
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Test Démo (Remplissage rapide)", color = EcoGreenDark)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Pas encore de compte ?", color = Slate.copy(alpha = 0.6f))
            TextButton(onClick = onNavigateToRegister) {
                Text("S'inscrire", color = EcoGreenDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: (Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Créer un compte",
            style = MaterialTheme.typography.headlineMedium,
            color = Slate,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedButton(
            onClick = { /* Simulated Google Login */ onRegisterSuccess(100) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            border = BorderStroke(1.dp, BorderLine)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = com.example.ecopav.R.drawable.ic_google_logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continuer avec Google", color = Slate)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom et Prénom") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Téléphone") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Quartier & Ville") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { onRegisterSuccess(100) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Slate),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Créer un compte", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "En créant un compte, vous recevez +100 points de bienvenue !",
            style = MaterialTheme.typography.bodySmall,
            color = EcoGreenDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
