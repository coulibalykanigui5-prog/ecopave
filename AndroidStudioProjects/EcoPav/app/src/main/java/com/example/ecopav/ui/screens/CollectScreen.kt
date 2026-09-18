package com.example.ecopav.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecopav.ui.theme.*

@Composable
fun CollectScreen() {
    var plasticType by remember { mutableStateOf("Bouteilles PET") }
    var quantity by remember { mutableStateOf("Moyenne") }
    var note by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SandSoft)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                "Signaler un dépôt",
                style = MaterialTheme.typography.titleLarge,
                color = Slate
            )
        }

        item {
            PhotoModule()
        }

        item {
            LocationModule()
        }

        item {
            CharacterizationModule(
                selectedType = plasticType,
                onTypeChange = { plasticType = it },
                selectedQuantity = quantity,
                onQuantityChange = { quantity = it }
            )
        }

        item {
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note libre (ex: derrière le marché)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EcoGreen,
                    unfocusedBorderColor = BorderLine
                )
            )
        }

        item {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EcoGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Envoyer le signalement (+20 pts)", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF3EF)
@Composable
fun CollectScreenPreview() {
    EcoPavéTheme {
        CollectScreen()
    }
}

@Composable
fun PhotoModule() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(2.dp, BorderLine, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = EcoGreen
            )
            Spacer(Modifier.height(8.dp))
            Text("Prendre une photo", color = Slate.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun LocationModule() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLine)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Terracotta)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Quartier Bopa", style = MaterialTheme.typography.titleSmall)
                    Text("6.368, 2.432 (GPS)", style = MaterialTheme.typography.labelSmall, color = Slate.copy(alpha = 0.5f))
                }
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = EcoGreen)
            }
        }
    }
}

@Composable
fun CharacterizationModule(
    selectedType: String,
    onTypeChange: (String) -> Unit,
    selectedQuantity: String,
    onQuantityChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Type de plastique dominant", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("PET", "Sachets", "PEHD", "Mixte").forEach { type ->
                FilterChip(
                    selected = selectedType.contains(type, ignoreCase = true),
                    onClick = { onTypeChange(type) },
                    label = { Text(type) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EcoGreenLight,
                        selectedLabelColor = EcoGreenDark
                    )
                )
            }
        }
        
        Text("Quantité estimée", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Faible", "Moyenne", "Importante").forEach { q ->
                FilterChip(
                    selected = selectedQuantity == q,
                    onClick = { onQuantityChange(q) },
                    label = { Text(q) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EcoGreenLight,
                        selectedLabelColor = EcoGreenDark
                    )
                )
            }
        }
    }
}
