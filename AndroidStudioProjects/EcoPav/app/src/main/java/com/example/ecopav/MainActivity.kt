package com.example.ecopav

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecopav.ui.theme.EcoPavéTheme
import com.example.ecopav.ui.theme.EcoGreen
import com.example.ecopav.ui.theme.Slate
import com.example.ecopav.ui.theme.EcoGreenLight
import com.example.ecopav.ui.screens.*
import java.util.Locale

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Accueil", Icons.Default.Home)
    object Collect : Screen("collect", "Signaler", Icons.Default.Place)
    object Order : Screen("order", "Boutique", Icons.Default.ShoppingCart)
    object Impact : Screen("impact", "Suivi", Icons.AutoMirrored.Filled.List)
    object Profile : Screen("profile", "Profil", Icons.Default.Person)
    
    object Welcome : Screen("welcome", "Bienvenue", Icons.Default.Star)
    object Login : Screen("login", "Connexion", Icons.Default.Lock)
    object Register : Screen("register", "Inscription", Icons.Default.AccountCircle)
}

val mainItems = listOf(
    Screen.Home,
    Screen.Collect,
    Screen.Order,
    Screen.Impact,
    Screen.Profile
)

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var isVoiceEnabledByGlobal = true // Shared state for TTS

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            Log.d("Permissions", "${it.key} accordée: ${it.value}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ))

        initSpeechRecognizer()
        tts = TextToSpeech(this, this)

        setContent {
            EcoPavéTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var isVoiceEnabled by remember { mutableStateOf(true) }
                
                // Keep class property in sync for the speak method
                isVoiceEnabledByGlobal = isVoiceEnabled

                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                
                // Observer les changements de navigation pour annoncer l'écran
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                LaunchedEffect(currentDestination) {
                    if (isVoiceEnabled) {
                        val label = when (currentDestination?.route) {
                            Screen.Home.route -> "Écran d'accueil"
                            Screen.Collect.route -> "Écran pour signaler un dépôt"
                            Screen.Order.route -> "Écran de la boutique"
                            Screen.Impact.route -> "Écran de suivi de votre impact"
                            Screen.Profile.route -> "Écran de votre profil"
                            Screen.Welcome.route -> "Bienvenue sur Eco Pavé"
                            Screen.Login.route -> "Écran de connexion"
                            Screen.Register.route -> "Écran d'inscription"
                            else -> null
                        }
                        label?.let { speak(it) }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        Surface(
                            color = Slate.copy(alpha = 0.05f),
                            modifier = Modifier.padding(top = 40.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (isLoggedIn) "Mode Application" else "Mode Authentification",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Slate.copy(alpha = 0.6f)
                                    )
                                    // Voice assistance status
                                    Text(
                                        text = if (isVoiceEnabled) "Assistance vocale active" else "Assistance vocale désactivée",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isVoiceEnabled) EcoGreen else Color.Gray
                                    )
                                }
                                
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Toggle Voice Button
                                    IconButton(
                                        onClick = { 
                                            isVoiceEnabled = !isVoiceEnabled
                                            if (isVoiceEnabled) speak("Assistance vocale activée")
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isVoiceEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                                            contentDescription = "Activer/Désactiver la voix",
                                            tint = if (isVoiceEnabled) EcoGreen else Color.Gray
                                        )
                                    }
                                    
                                    Spacer(Modifier.width(8.dp))
                                    
                                    Button(
                                        onClick = { 
                                            isLoggedIn = !isLoggedIn
                                            if (isLoggedIn) {
                                                navController.navigate(Screen.Home.route) { popUpTo(0) }
                                            } else {
                                                navController.navigate(Screen.Welcome.route) { popUpTo(0) }
                                            }
                                        },
                                        modifier = Modifier.height(32.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = EcoGreen),
                                        shape = RoundedCornerShape(16.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp)
                                    ) {
                                        Text(if (isLoggedIn) "Aller à Auth" else "Aller à l'App", style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    },
                    bottomBar = {
                        if (isLoggedIn) {
                            NavigationBar(containerColor = Color.White, contentColor = Slate) {
                                mainItems.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = null) },
                                        label = { Text(screen.label) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = EcoGreen,
                                            selectedTextColor = EcoGreen,
                                            indicatorColor = Color.Transparent
                                        )
                                    )
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        if (isVoiceEnabled) {
                            FloatingActionButton(
                                onClick = { 
                                    val currentRoute = navController.currentDestination?.route
                                    val label = when (currentRoute) {
                                        Screen.Home.route -> "Vous êtes sur l'accueil. Ici vous voyez vos points."
                                        Screen.Collect.route -> "Vous êtes sur signaler. Appuyez pour envoyer une photo de plastique."
                                        Screen.Order.route -> "Vous êtes sur la boutique. Vous pouvez commander des pavés."
                                        Screen.Impact.route -> "Vous êtes sur le suivi. Vous voyez le carbone économisé."
                                        Screen.Profile.route -> "Vous êtes sur le profil. Vous pouvez voir vos infos."
                                        else -> "Appuyez sur les boutons en bas pour changer d'écran."
                                    }
                                    speak(label)
                                },
                                containerColor = EcoGreen,
                                contentColor = Color.White,
                                shape = CircleShape
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Écouter où je suis")
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Welcome.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Auth Flow
                        composable(Screen.Welcome.route) {
                            WelcomeScreen(
                                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                                onExploreAsGuest = { 
                                    isLoggedIn = true
                                    navController.navigate(Screen.Home.route) { popUpTo(0) }
                                }
                            )
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                                onLoginSuccess = {
                                    isLoggedIn = true
                                    navController.navigate(Screen.Home.route) { popUpTo(0) }
                                }
                            )
                        }
                        composable(Screen.Register.route) {
                            RegisterScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onRegisterSuccess = { 
                                    isLoggedIn = true
                                    navController.navigate(Screen.Home.route) { popUpTo(0) }
                                }
                            )
                        }
                        composable(Screen.Home.route) { HomeScreen() }
                        composable(Screen.Collect.route) { CollectScreen() }
                        composable(Screen.Order.route) { OrderScreen() }
                        composable(Screen.Impact.route) { ImpactScreen() }
                        composable(Screen.Profile.route) { 
                            ProfileScreen(onLogout = {
                                isLoggedIn = false
                                navController.navigate(Screen.Welcome.route) { popUpTo(0) }
                            }) 
                        }
                    }
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.FRENCH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Langue française non supportée")
            } else {
                isTtsReady = true
            }
        }
    }

    private fun speak(text: String) {
        if (isTtsReady && isVoiceEnabledByGlobal) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun initSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR")
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        speechRecognizer.destroy()
        super.onDestroy()
    }
}
