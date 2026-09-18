package com.example.ecopav.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecopav.ui.theme.*

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SandSoft)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            HeaderSection()
        }
        item {
            HeroCard()
        }
        item {
            PillarsSection()
        }
        item {
            CollectiveImpactSection()
        }
        item {
            RecentActivitySection()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "EcoPavé",
                style = MaterialTheme.typography.titleLarge,
                color = EcoGreenDark
            )
            Text(
                text = "Cotonou, Bénin",
                style = MaterialTheme.typography.bodySmall,
                color = Slate.copy(alpha = 0.6f)
            )
        }
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Slate)
        }
    }
}

@Composable
fun HeroCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Slate),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Coulibaly Kanigui",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Surface(
                        color = EcoGreen.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = EcoGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "Niveau Or",
                                color = EcoGreen,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
                Text(
                    text = "48 kg CO₂ évités",
                    color = EcoGreen,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "SOLDE POINTS",
                        color = Color.White.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "1 240 pts",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = EcoGreen),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("Échanger", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PillarsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PillarCard(
            title = "Collecter",
            subtitle = "+20 pts",
            backgroundColor = EcoGreenLight,
            contentColor = EcoGreenDark,
            modifier = Modifier.weight(1f)
        )
        PillarCard(
            title = "Commander",
            subtitle = "Dès 300 F",
            backgroundColor = SandBeige,
            contentColor = Terracotta,
            modifier = Modifier.weight(1f)
        )
        PillarCard(
            title = "Suivre",
            subtitle = "Impact réel",
            backgroundColor = Color.White,
            contentColor = Slate,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PillarCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = contentColor)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = contentColor.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun CollectiveImpactSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLine)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Impact Collectif (Ville)",
                style = MaterialTheme.typography.titleMedium,
                color = Slate
            )
            Spacer(Modifier.height(12.dp))
            
            // Mosaic representation (simplified)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(15) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (it < 9) EcoGreen else BorderLine)
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(15) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BorderLine)
                    )
                }
            }
            
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "62,4 t / 150 t",
                    style = MaterialTheme.typography.labelLarge,
                    color = EcoGreenDark,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Objectif annuel",
                    style = MaterialTheme.typography.labelSmall,
                    color = Slate.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun RecentActivitySection() {
    Column {
        Text(
            "Activité récente",
            style = MaterialTheme.typography.titleMedium,
            color = Slate
        )
        Spacer(Modifier.height(12.dp))
        ActivityItem("Signalement validé", "Quartier Bopa", "+20 pts", true)
        ActivityItem("Commande livrée", "150 pavés ocre", "3 200 pts", false)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF3EF, device = Devices.PIXEL_4)
@Composable
fun HomeScreenPreview() {
    EcoPavéTheme {
        HomeScreen()
    }
}

@Composable
fun ActivityItem(title: String, detail: String, value: String, isGreen: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.bodyMedium, color = Slate)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = Slate.copy(alpha = 0.6f))
        }
        Text(
            value,
            style = MaterialTheme.typography.labelLarge,
            color = if (isGreen) EcoGreenDark else Terracotta,
            fontWeight = FontWeight.Bold
        )
    }
}
