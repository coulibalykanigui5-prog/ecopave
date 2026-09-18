package com.example.ecopav.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ecopav.ui.theme.*

data class PaveModel(val name: String, val price: Int, val color: Color, val description: String)

val paveModels = listOf(
    PaveModel("Gris Neutre", 300, Color.Gray, "Polyvalent et sobre"),
    PaveModel("Terracotta Ocre", 350, Terracotta, "Chaleureux pour allées et cours"),
    PaveModel("Mosaïque Bicolore", 400, EcoGreen, "Décoratif et drainant"),
    PaveModel("Autobloquant Lourd", 450, Slate, "Haute charge pour véhicules")
)

@Composable
fun OrderScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Modèles", "Configurateur")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SandSoft)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = EcoGreen,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = EcoGreen
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) },
                    selectedContentColor = EcoGreen,
                    unselectedContentColor = Slate.copy(alpha = 0.6f)
                )
            }
        }

        if (selectedTabIndex == 0) {
            ModelsList()
        } else {
            ConfiguratorModule()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF3EF)
@Composable
fun OrderScreenPreview() {
    EcoPavéTheme {
        OrderScreen()
    }
}

@Composable
fun ModelsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(paveModels) { model ->
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(model.color)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(model.name, style = MaterialTheme.typography.titleSmall)
                        Text(model.description, style = MaterialTheme.typography.bodySmall, color = Slate.copy(alpha = 0.6f))
                    }
                    Text(
                        "${model.price} F",
                        style = MaterialTheme.typography.labelLarge,
                        color = Terracotta,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ConfiguratorModule() {
    var length by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    
    val l = length.toDoubleOrNull() ?: 0.0
    val w = width.toDoubleOrNull() ?: 0.0
    val surface = l * w
    val count = (surface * 25).toInt()
    val weight = surface * 12 // Assuming 12kg/m2 of recycled plastic
    val total = count * 300 // Base price

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text("Dimensions de la surface", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = length,
                    onValueChange = { length = it },
                    label = { Text("Longueur (m)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = width,
                    onValueChange = { width = it },
                    label = { Text("Largeur (m)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = EcoGreenDark),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Estimation instantanée", color = Color.White.copy(alpha = 0.7f))
                    Spacer(Modifier.height(16.dp))
                    
                    ResultRow("Surface totale", "${"%.1f".format(surface)} m²")
                    ResultRow("Pavés nécessaires", "$count unités")
                    ResultRow("Plastique valorisé", "${"%.1f".format(weight)} kg")
                    
                    HorizontalDivider(color = Color.White.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("MONTANT TOTAL", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(
                            "$total F CFA",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                }
            }
        }

        item {
            Text("Mode de règlement", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            // Payment options (simplified)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Orange", "Wave", "MTN", "Cash").forEach { mode ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLine)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(mode, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Générer le devis", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.8f))
        Text(value, color = Color.White, fontWeight = FontWeight.Medium)
    }
}
