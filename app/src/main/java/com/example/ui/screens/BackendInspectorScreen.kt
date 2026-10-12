package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeContainerLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangePrimaryDark
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray

@Composable
fun BackendInspectorScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CleanCanvas)
            .testTag("backend_inspector_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "AI Service Specification",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "FastAPI + google-generativeai backend component specification.",
                    fontSize = 13.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )
            }
        }

        // Live Component Architecture Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = OrangeContainerLight,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Hub,
                                    contentDescription = null,
                                    tint = OrangePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CORE SERVICE SPECIFICATION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimaryDark,
                                letterSpacing = 0.5.sp,
                                fontFamily = OutfitFontFamily
                            )
                            Text(
                                text = "google-generativeai • FastAPI",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    BackendMetaRow(label = "Model", value = "gemini-1.5-flash (Multimodal Vision)")
                    BackendMetaRow(label = "Primary Route", value = "POST /api/scan")
                    BackendMetaRow(label = "Input Format", value = "Multipart File / Base64 image")
                    BackendMetaRow(label = "Output Format", value = "Strict Pydantic JSON")
                    BackendMetaRow(label = "Resilience", value = "Rate limit & timeout mock fallback")
                }
            }
        }

        // System Instruction Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = OrangePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SYSTEM INSTRUCTION",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimaryDark,
                            letterSpacing = 0.5.sp,
                            fontFamily = OutfitFontFamily
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"You are EcoLens AI, an expert campus environmental auditor and sustainability engine. Analyze images of waste, cafeteria food, discarded resources, or campus facilities to categorize materials, recommend proper college campus disposal bins, estimate environmental footprints, and provide actionable advice.\"",
                        fontSize = 12.sp,
                        color = TextCharcoal,
                        lineHeight = 17.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // JSON Schema Code Block
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2024)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "REQUIRED JSON SCHEMA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary,
                            letterSpacing = 0.5.sp,
                            fontFamily = OutfitFontFamily
                        )
                        Text(
                            text = "Pydantic V2",
                            fontSize = 10.sp,
                            color = Color(0xFFAAB8B0),
                            fontFamily = OutfitFontFamily
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val schemaJson = """{
  "status": "string",
  "item_name": "string",
  "category": "string",
  "disposal_bin": "string",
  "campus_location_recommendation": "string",
  "estimated_impact": {
    "carbon_footprint_kg": 0.082,
    "water_footprint_liters": 5.4,
    "decomposition_years": 450.0
  },
  "actionable_advice": "string",
  "eco_points_earned": 35
}"""

                    val scrollState = rememberScrollState()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF131417), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                            .horizontalScroll(scrollState)
                    ) {
                        Text(
                            text = schemaJson,
                            color = Color(0xFFFFB280),
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BackendMetaRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondaryGray,
            fontFamily = PlusJakartaFontFamily
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = TextCharcoal,
            fontWeight = FontWeight.SemiBold,
            fontFamily = OutfitFontFamily
        )
    }
}
