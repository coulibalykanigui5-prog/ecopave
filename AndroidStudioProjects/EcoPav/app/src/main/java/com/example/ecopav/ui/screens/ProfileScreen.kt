package com.example.ecopav.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecopav.ui.theme.*

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SandSoft)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            ProfileHeader()
        }

        item {
            PointsConversionSection()
        }

        item {
            SettingsSection(onLogout)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF3EF)
@Composable
fun ProfileScreenPreview() {
    EcoPavéTheme {
        ProfileScreen(onLogout = {})
    }
}

@Composable
fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLine)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(EcoGreenLight),
                contentAlignment = Alignment.Center
            ) {
                Text("CK", color = EcoGreenDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Coulibaly Kanigui", style = MaterialTheme.typography.titleMedium)
                Text("Membre depuis Jan 2026", style = MaterialTheme.typography.labelSmall, color = Slate.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun PointsConversionSection() {
    Column {
        Text("Convertir mes points", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        
        ConversionItem("Recharge Téléphonique", "1 000 F CFA", "200 pts")
        ConversionItem("Kit Citoyen", "Gants + Sacs", "500 pts")
        ConversionItem("Remise Boutique", "-10% sur pavés", "800 pts")
        ConversionItem("Plaque Personnalisée", "Allée éco-citoyenne", "1 500 pts")
    }
}

@Composable
fun ConversionItem(title: String, benefit: String, cost: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                Text(benefit, style = MaterialTheme.typography.bodySmall, color = EcoGreenDark)
            }
            Surface(
                color = SandSoft,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    cost,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = Slate
                )
            }
        }
    }
}

@Composable
fun SettingsSection(onLogout: () -> Unit) {
    Column {
        Text("Paramètres", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        
        SettingRow("Langue locale", "Français")
        SettingRow("Notifications SMS", "Activées")
        SettingRow("Historique des devis", "")
        
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Terracotta),
            border = androidx.compose.foundation.BorderStroke(1.dp, Terracotta),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Se déconnecter")
        }
    }
}

@Composable
fun SettingRow(label: String, value: String) {
    Surface(
        onClick = { /* TODO */ },
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(20.dp), tint = Slate.copy(alpha = 0.6f))
                Spacer(Modifier.width(12.dp))
                Text(label, style = MaterialTheme.typography.bodyLarge)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (value.isNotEmpty()) {
                    Text(value, style = MaterialTheme.typography.bodyMedium, color = Slate.copy(alpha = 0.5f))
                    Spacer(Modifier.width(8.dp))
                }
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Slate.copy(alpha = 0.3f))
            }
        }
    }
}
