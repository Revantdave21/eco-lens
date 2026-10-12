package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EcoScanResult
import com.example.ui.components.resolveBinTheme
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeContainerLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangePrimaryDark
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EcoLedgerScreen(
    totalPoints: Int,
    totalCo2SavedKg: Double,
    itemsAuditedCount: Int,
    history: List<EcoScanResult>,
    activeFilter: String,
    onFilterChanged: (String) -> Unit,
    onItemClick: (EcoScanResult) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "Compostable", "Recyclable", "E-Waste", "Paper", "Landfill")

    val filteredHistory = if (activeFilter == "All") {
        history
    } else {
        history.filter {
            it.category.contains(activeFilter, ignoreCase = true) ||
                    it.disposalBin.contains(activeFilter, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CleanCanvas)
            .testTag("eco_ledger_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary Card in Orange & White
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SVVV Sustainability Ledger",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary,
                            fontFamily = OutfitFontFamily
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OrangeContainerLight
                        ) {
                            Text(
                                text = "TERM 2026",
                                color = OrangePrimaryDark,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = OutfitFontFamily,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "$totalPoints",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                            Text(
                                text = "Earned Eco-Points",
                                fontSize = 12.sp,
                                color = TextSecondaryGray,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$totalCo2SavedKg kg",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimary,
                                fontFamily = OutfitFontFamily
                            )
                            Text(
                                text = "CO2e Mitigated",
                                fontSize = 12.sp,
                                color = TextSecondaryGray,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "$itemsAuditedCount verified campus resource audits completed",
                        fontSize = 12.sp,
                        color = TextTertiaryGray,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { f ->
                    FilterChip(
                        selected = activeFilter == f,
                        onClick = { onFilterChanged(f) },
                        label = {
                            Text(
                                text = f,
                                fontSize = 12.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (activeFilter == f) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = OrangePrimary,
                            selectedLabelColor = PureWhite,
                            containerColor = PureWhite,
                            labelColor = TextCharcoal
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = activeFilter == f,
                            borderColor = if (activeFilter == f) OrangePrimary else BorderSubtle
                        )
                    )
                }
            }
        }

        // Audit Log Items
        if (filteredHistory.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No audits recorded in this category yet.",
                        color = TextTertiaryGray,
                        fontSize = 13.sp,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }
        } else {
            items(filteredHistory) { item ->
                LedgerItemRow(result = item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Composable
fun LedgerItemRow(
    result: EcoScanResult,
    onClick: () -> Unit
) {
    val binTheme = resolveBinTheme(result.disposalBin, result.category)
    val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    val formattedTime = dateFormat.format(Date(result.timestamp))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("ledger_item_${result.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = binTheme.lightBg,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = binTheme.primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.itemName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${result.category} • $formattedTime",
                    fontSize = 11.sp,
                    color = TextTertiaryGray,
                    fontFamily = PlusJakartaFontFamily
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = OrangeContainerLight
            ) {
                Text(
                    text = "+${result.ecoPointsEarned} pts",
                    color = OrangePrimaryDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = OutfitFontFamily,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
