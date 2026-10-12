package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Co2
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CampusEvent
import com.example.data.model.CampusStats
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeContainerLight
import com.example.ui.theme.OrangeContainerMuted
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangePrimaryDark
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TelemetryCarbon
import com.example.ui.theme.TelemetryCarbonBg
import com.example.ui.theme.TelemetryEnergy
import com.example.ui.theme.TelemetryEnergyBg
import com.example.ui.theme.TelemetryWaste
import com.example.ui.theme.TelemetryWasteBg
import com.example.ui.theme.TelemetryWater
import com.example.ui.theme.TelemetryWaterBg
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray

@Composable
fun CampusOverviewScreen(
    stats: CampusStats,
    events: List<CampusEvent>,
    onOpenEditStats: () -> Unit,
    onOpenSimulateEvent: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToElectricity: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CleanCanvas)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag("campus_overview_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Section (Matching user's layout in Orange & White)
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
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = OrangeContainerLight
                    ) {
                        Text(
                            text = "CAMPUS SUSTAINABILITY",
                            color = OrangePrimaryDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = CleanCanvas,
                        border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(OrangePrimary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SVVV INDORE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = stats.greeting,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = OutfitFontFamily,
                    color = TextCharcoal,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Resources in view. Waste less, together.",
                    fontSize = 14.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons: Edit campus stats & Simulate campus event
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onOpenEditStats,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = OrangePrimary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("edit_campus_stats_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Edit stats",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = OutfitFontFamily
                        )
                    }

                    Button(
                        onClick = onOpenSimulateEvent,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OrangePrimary,
                            contentColor = PureWhite
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(44.dp)
                            .testTag("simulate_campus_event_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Simulate event",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = OutfitFontFamily
                        )
                    }
                }
            }
        }

        // 4 Key Stat Cards (Orange & White Theme)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricStatCard(
                title = "Carbon mitigated",
                value = "${stats.carbonMitigatedKg}",
                unit = "kg CO₂e",
                subtitle = "This month • estimated",
                accentColor = TelemetryCarbon,
                accentBg = TelemetryCarbonBg,
                modifier = Modifier.weight(1f)
            )

            MetricStatCard(
                title = "Energy saved",
                value = formatNumber(stats.energySavedKwh.toLong()),
                unit = "kWh",
                subtitle = "Tap for daily & weekly audit",
                accentColor = TelemetryEnergy,
                accentBg = TelemetryEnergyBg,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToElectricity() }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricStatCard(
                title = "Water conserved",
                value = formatNumber(stats.waterConservedLiters.toLong()),
                unit = "L",
                subtitle = "Reported campus fixes",
                accentColor = TelemetryWater,
                accentBg = TelemetryWaterBg,
                modifier = Modifier.weight(1f)
            )

            MetricStatCard(
                title = "Sustainability index",
                value = "${stats.sustainabilityIndex}",
                unit = "/ 100",
                subtitle = "Good progress score",
                accentColor = OrangePrimary,
                accentBg = OrangeContainerLight,
                modifier = Modifier.weight(1f)
            )
        }

        // Electricity Over-Limit Alert Banner
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFECDD3)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToElectricity() }
                .testTag("electricity_alert_banner")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE11D48),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Daily Electricity Draw: 7,800 kWh (104% Over Limit)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9F1239),
                        fontFamily = OutfitFontFamily
                    )
                    Text(
                        text = "Exceeds 7,500 kWh guide. Tap to inspect why & view weekly reports.",
                        fontSize = 11.sp,
                        color = Color(0xFFE11D48),
                        fontFamily = PlusJakartaFontFamily
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = null,
                    tint = Color(0xFFE11D48),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // RESOURCE PULSE Card (Today, at a glance with Interactive Pie Chart)
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("resource_pulse_card")
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
                            text = "RESOURCE PULSE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary,
                            letterSpacing = 1.sp,
                            fontFamily = OutfitFontFamily
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Today, at a glance",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            fontFamily = OutfitFontFamily
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = OrangeContainerLight
                    ) {
                        Text(
                            text = "SIMULATED • 12 OCT",
                            color = OrangePrimaryDark,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Daily sample consumption compared with its planning reference.",
                    fontSize = 12.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Pie Chart and Telemetry Legend Layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Custom Crisp Canvas Pie / Donut Chart
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ResourcePieChart(
                            waterPercent = stats.waterGuidePercent.toFloat(),
                            energyPercent = stats.electricityGuidePercent.toFloat(),
                            wastePercent = stats.wasteGuidePercent.toFloat()
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SVVV",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                            Text(
                                text = "Live",
                                fontSize = 10.sp,
                                color = OrangePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Legends with percentages
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ResourceLegendRow(
                            label = "Water",
                            value = "${formatNumber(stats.waterDailyLiters.toLong())} L/day",
                            percentBadge = "${stats.waterGuidePercent}%",
                            guideSub = "of guide",
                            dotColor = TelemetryWater
                        )

                        ResourceLegendRow(
                            label = "Electricity",
                            value = "${formatNumber(stats.electricityDailyKwh.toLong())} kWh/day",
                            percentBadge = "${stats.electricityGuidePercent}%",
                            guideSub = "over",
                            dotColor = TelemetryEnergy
                        )

                        ResourceLegendRow(
                            label = "Waste",
                            value = "${stats.wasteDailyKg.toInt()} kg/day",
                            percentBadge = "${stats.wasteGuidePercent}%",
                            guideSub = "over",
                            dotColor = TelemetryWaste
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Pie shows each resource's share of normalized guide use across academic blocks.",
                    fontSize = 11.sp,
                    color = TextTertiaryGray,
                    lineHeight = 15.sp,
                    fontFamily = PlusJakartaFontFamily
                )
            }
        }

        // Multimodal AI Scanner Banner (Direct Bridge to the Gemini Image Detection System!)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = OrangeContainerLight),
            border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ECO-LENS AI AUDITOR",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimaryDark,
                        letterSpacing = 1.sp,
                        fontFamily = OutfitFontFamily
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Scan Campus Waste or Resource",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        fontFamily = OutfitFontFamily
                    )
                    Text(
                        text = "Instant Gemini vision audit & bin sorting",
                        fontSize = 12.sp,
                        color = TextSecondaryGray,
                        fontFamily = PlusJakartaFontFamily
                    )
                }

                Button(
                    onClick = onNavigateToScanner,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = PureWhite
                    ),
                    modifier = Modifier.testTag("quick_scan_banner_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Scan", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = OutfitFontFamily)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MetricStatCard(
    title: String,
    value: String,
    unit: String,
    subtitle: String,
    accentColor: Color,
    accentBg: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = TextSecondaryGray,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PlusJakartaFontFamily
                )
                Surface(
                    shape = CircleShape,
                    color = accentBg,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(accentColor)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = unit,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OrangePrimary,
                    fontFamily = OutfitFontFamily,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextTertiaryGray,
                fontFamily = PlusJakartaFontFamily,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ResourcePieChart(
    waterPercent: Float,
    energyPercent: Float,
    wastePercent: Float
) {
    val total = waterPercent + energyPercent + wastePercent
    val sweepWater = (waterPercent / total) * 360f
    val sweepEnergy = (energyPercent / total) * 360f
    val sweepWaste = (wastePercent / total) * 360f

    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 20.dp.toPx()
        val diameter = size.minDimension - strokeWidth
        val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize = Size(diameter, diameter)

        // Arc 1: Water
        drawArc(
            color = Color(0xFF0284C7),
            startAngle = -90f,
            sweepAngle = sweepWater - 4f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Arc 2: Energy
        drawArc(
            color = Color(0xFFF59E0B),
            startAngle = -90f + sweepWater,
            sweepAngle = sweepEnergy - 4f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Arc 3: Waste
        drawArc(
            color = Color(0xFFFF5C00),
            startAngle = -90f + sweepWater + sweepEnergy,
            sweepAngle = sweepWaste - 4f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun ResourceLegendRow(
    label: String,
    value: String,
    percentBadge: String,
    guideSub: String,
    dotColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                Text(
                    text = value,
                    fontSize = 10.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = percentBadge,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                fontFamily = OutfitFontFamily
            )
            Text(
                text = guideSub,
                fontSize = 9.sp,
                color = TextTertiaryGray,
                fontFamily = PlusJakartaFontFamily
            )
        }
    }
}

// Edit Campus Stats Dialog
@Composable
fun EditCampusStatsDialog(
    currentStats: CampusStats,
    onDismiss: () -> Unit,
    onSave: (Double, Double, Double, Int) -> Unit
) {
    var carbon by remember { mutableStateOf(currentStats.carbonMitigatedKg.toString()) }
    var energy by remember { mutableStateOf(currentStats.energySavedKwh.toInt().toString()) }
    var water by remember { mutableStateOf(currentStats.waterConservedLiters.toInt().toString()) }
    var index by remember { mutableStateOf(currentStats.sustainabilityIndex.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Campus Telemetry",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = OutfitFontFamily
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = carbon,
                    onValueChange = { carbon = it },
                    label = { Text("Carbon Mitigated (kg CO2e)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = energy,
                    onValueChange = { energy = it },
                    label = { Text("Energy Saved (kWh)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = water,
                    onValueChange = { water = it },
                    label = { Text("Water Conserved (L)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = index,
                    onValueChange = { index = it },
                    label = { Text("Sustainability Index (0-100)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val c = carbon.toDoubleOrNull() ?: currentStats.carbonMitigatedKg
                    val e = energy.toDoubleOrNull() ?: currentStats.energySavedKwh
                    val w = water.toDoubleOrNull() ?: currentStats.waterConservedLiters
                    val i = index.toIntOrNull() ?: currentStats.sustainabilityIndex
                    onSave(c, e, w, i)
                },
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
            ) {
                Text("Save Stats", fontFamily = OutfitFontFamily)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondaryGray)
            }
        }
    )
}

// Simulate Campus Event Dialog
@Composable
fun SimulateCampusEventDialog(
    events: List<CampusEvent>,
    onDismiss: () -> Unit,
    onSelectEvent: (CampusEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Simulate Campus Event",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = OutfitFontFamily
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Select an environmental initiative to test real-time telemetry impact across SVVV Indore:",
                    fontSize = 12.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )
                events.forEach { event ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CleanCanvas),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectEvent(event) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = event.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal,
                                    fontFamily = OutfitFontFamily
                                )
                                Text(
                                    text = "+${event.carbonDeltaKg}kg CO₂e",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OrangePrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = event.description,
                                fontSize = 11.sp,
                                color = TextSecondaryGray,
                                lineHeight = 15.sp,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextSecondaryGray)
            }
        }
    )
}

private fun formatNumber(number: Long): String {
    return java.text.NumberFormat.getIntegerInstance().format(number)
}
