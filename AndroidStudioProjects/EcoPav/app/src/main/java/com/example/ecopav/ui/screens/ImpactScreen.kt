package com.example.ecopav.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecopav.ui.theme.*

@Composable
fun ImpactScreen() {
    var surfaceSlider by remember { mutableFloatStateOf(50f) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SandSoft)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                "Mon Impact Écologique",
                style = MaterialTheme.typography.titleLarge,
                color = Slate
            )
        }

        item {
            CarbonBalanceCard()
        }

        item {
            DurabilityComparison()
        }

        item {
            EcoRenovationSimulator(
                surface = surfaceSlider,
                onSurfaceChange = { surfaceSlider = it }
            )
        }

        item {
            BadgesSection()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF3EF)
@Composable
fun ImpactScreenPreview() {
    EcoPavéTheme {
        ImpactScreen()
    }
}

@Composable
fun CarbonBalanceCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = EcoGreen),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Total CO₂ évité", color = Color.White.copy(alpha = 0.8f))
            Text(
                "48,2 kg",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
            Spacer(Modifier.height(20.dp))
            
            EquivalenceRow("🚗 320 km de voiture évités")
            EquivalenceRow("🌳 4 arbres préservés")
            EquivalenceRow("🍶 1 240 bouteilles recyclées")
        }
    }
}

@Composable
fun EquivalenceRow(text: String) {
    Surface(
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DurabilityComparison() {
    Column {
        Text("Durabilité des matériaux", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        
        DurabilityBar("Eco-Plastique", 1.0f, "+30 ans", EcoGreen)
        DurabilityBar("Ciment classique", 0.5f, "12 ans", Color.Gray)
        DurabilityBar("Bitume standard", 0.3f, "8 ans", Slate)
    }
}

@Composable
fun DurabilityBar(label: String, progress: Float, value: String, color: Color) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = color,
            trackColor = BorderLine
        )
    }
}

@Composable
fun EcoRenovationSimulator(surface: Float, onSurfaceChange: (Float) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLine)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Simulateur d'éco-rénovation", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(16.dp))
            
            Text("Surface: ${surface.toInt()} m²", style = MaterialTheme.typography.labelLarge, color = EcoGreenDark)
            Slider(
                value = surface,
                onValueChange = onSurfaceChange,
                valueRange = 5f..150f,
                colors = SliderDefaults.colors(thumbColor = EcoGreen, activeTrackColor = EcoGreen)
            )
            
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Plastique", style = MaterialTheme.typography.labelSmall)
                    Text("${(surface * 12).toInt()} kg", fontWeight = FontWeight.Bold, color = Slate)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("CO₂ absorbé", style = MaterialTheme.typography.labelSmall)
                    Text("${(surface * 2.5).toInt()} kg", fontWeight = FontWeight.Bold, color = Slate)
                }
            }
        }
    }
}

@Composable
fun BadgesSection() {
    Column {
        Text("Mes Badges", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            BadgeItem("Éco-débutant", true)
            BadgeItem("Sentinelle", true)
            BadgeItem("Bâtisseur", false)
            BadgeItem("Ambassadeur", false)
        }
    }
}

@Composable
fun BadgeItem(label: String, unlocked: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(if (unlocked) EcoGreenLight else BorderLine),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                tint = if (unlocked) EcoGreenDark else Color.Gray.copy(alpha = 0.5f)
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = if (unlocked) Slate else Color.Gray,
            maxLines = 1
        )
    }
}
