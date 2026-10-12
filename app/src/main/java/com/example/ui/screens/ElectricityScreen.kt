package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyElectricityRecord
import com.example.data.model.OverLimitCause
import com.example.data.model.WeeklyEnergyReport
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
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray

@Composable
fun ElectricityScreen(
    dailyRecords: List<DailyElectricityRecord>,
    selectedDay: DailyElectricityRecord?,
    overLimitCauses: List<OverLimitCause>,
    weeklyReports: List<WeeklyEnergyReport>,
    selectedWeekIndex: Int,
    isMitigationApplied: Boolean,
    onSelectDay: (DailyElectricityRecord) -> Unit,
    onSelectWeek: (Int) -> Unit,
    onApplyMitigation: () -> Unit,
    onGenerateReport: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeReport = weeklyReports.getOrNull(selectedWeekIndex) ?: weeklyReports.lastOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CleanCanvas)
            .testTag("electricity_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card with Clean Typography & Spacing
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
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
                                text = "CAMPUS TELEMETRY",
                                color = OrangePrimaryDark,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = OutfitFontFamily,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isMitigationApplied) Color(0xFFDCFCE7) else Color(0xFFFFF1F2)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(if (isMitigationApplied) Color(0xFF16A34A) else Color(0xFFE11D48))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isMitigationApplied) "COMPLIANT • 96%" else "OVER LIMIT • 104%",
                                    color = if (isMitigationApplied) Color(0xFF16A34A) else Color(0xFFE11D48),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = OutfitFontFamily
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Electricity Usage & Audit",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = OutfitFontFamily,
                        color = TextCharcoal,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Real-time consumption telemetry across SVVV academic blocks against the 7,500 kWh daily planning guide.",
                        fontSize = 13.sp,
                        color = TextSecondaryGray,
                        lineHeight = 19.sp,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }
        }

        // Section 1: Every Day Usage Visualizer & Day Details
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "DAILY CONSUMPTION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimary,
                                letterSpacing = 1.sp,
                                fontFamily = OutfitFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Every Day Usage (Mon - Sun)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OrangeContainerLight
                        ) {
                            Text(
                                text = "Guide: 7,500 kWh",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimaryDark,
                                fontFamily = OutfitFontFamily,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Daily Interactive 7-Day Chart
                    DailyInteractiveBarChart(
                        records = dailyRecords,
                        selectedDay = selectedDay,
                        onSelect = onSelectDay
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Day Details Card
                    selectedDay?.let { day ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (day.isOverLimit) Color(0xFFFFF5F5) else CleanCanvas,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (day.isOverLimit) Color(0xFFFECDD3) else OrangeBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "${day.dayName} Power Draw",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextCharcoal,
                                            fontFamily = OutfitFontFamily
                                        )
                                        Text(
                                            text = "Peak hour: ${day.peakHour}",
                                            fontSize = 11.sp,
                                            color = TextSecondaryGray,
                                            fontFamily = PlusJakartaFontFamily
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (day.isOverLimit) Color(0xFFE11D48) else Color(0xFF16A34A)
                                    ) {
                                        Text(
                                            text = if (day.isOverLimit) "${day.percentOfLimit}% (OVER LIMIT)" else "${day.percentOfLimit}% (OK)",
                                            color = PureWhite,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = OutfitFontFamily,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = PureWhite,
                                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                text = "Total Drawn",
                                                fontSize = 10.sp,
                                                color = TextTertiaryGray,
                                                fontFamily = PlusJakartaFontFamily
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = "${day.consumptionKwh.toInt()} kWh",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (day.isOverLimit) Color(0xFFE11D48) else OrangePrimary,
                                                fontFamily = OutfitFontFamily
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = PureWhite,
                                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                text = if (day.isOverLimit) "Excess Over Guide" else "Remaining Buffer",
                                                fontSize = 10.sp,
                                                color = TextTertiaryGray,
                                                fontFamily = PlusJakartaFontFamily
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = if (day.isOverLimit) "+${day.excessKwh.toInt()} kWh" else "${(day.limitKwh - day.consumptionKwh).toInt()} kWh",
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (day.isOverLimit) Color(0xFFE11D48) else Color(0xFF16A34A),
                                                fontFamily = OutfitFontFamily
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = OrangePrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Primary Driver: ${day.primaryDriver}",
                                        fontSize = 12.sp,
                                        color = TextCharcoal,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Why Daily Usage is Over Limit (Root Cause Diagnostics)
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("why_over_limit_card")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "CAMPUS DIAGNOSTICS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE11D48),
                                letterSpacing = 1.sp,
                                fontFamily = OutfitFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Why Daily Usage is Over Limit",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFFF1F2),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.WarningAmber,
                                    contentDescription = null,
                                    tint = Color(0xFFE11D48),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "The campus exceeds 7,500 kWh on mid-week days (Wed, Thu, Fri) due to three core facility drivers identified by telemetry:",
                        fontSize = 12.sp,
                        color = TextSecondaryGray,
                        lineHeight = 18.sp,
                        fontFamily = PlusJakartaFontFamily
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3 Root Cause Items with Clean Visuals
                    overLimitCauses.forEach { cause ->
                        RootCauseCard(cause = cause)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Action Button: Run Mitigation Shaving
                    Button(
                        onClick = onApplyMitigation,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMitigationApplied) Color(0xFF16A34A) else OrangePrimary,
                            contentColor = PureWhite
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("apply_mitigation_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isMitigationApplied) Icons.Default.CheckCircle else Icons.Default.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isMitigationApplied) "Peak Shaving Active (Within Limit)" else "Run Peak Load Shaving Mitigation",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = OutfitFontFamily
                            )
                        }
                    }
                }
            }
        }

        // Section 3: Generated Weekly Usage Reports
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("weekly_report_section")
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "WEEKLY AUDIT REPORTS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangePrimary,
                                letterSpacing = 1.sp,
                                fontFamily = OutfitFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Usage Report of Each Week",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Clean Week Selector Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(weeklyReports) { index, report ->
                            FilterChip(
                                selected = selectedWeekIndex == index,
                                onClick = { onSelectWeek(index) },
                                label = {
                                    Text(
                                        text = report.weekLabel,
                                        fontSize = 12.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = if (selectedWeekIndex == index) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = OrangePrimary,
                                    selectedLabelColor = PureWhite,
                                    containerColor = CleanCanvas,
                                    labelColor = TextCharcoal
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedWeekIndex == index,
                                    borderColor = if (selectedWeekIndex == index) OrangePrimary else BorderSubtle
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Active Weekly Report Card
                    activeReport?.let { report ->
                        PolishedWeeklyReportCard(
                            report = report,
                            onGenerate = { onGenerateReport(report.weekNumber) }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DailyInteractiveBarChart(
    records: List<DailyElectricityRecord>,
    selectedDay: DailyElectricityRecord?,
    onSelect: (DailyElectricityRecord) -> Unit
) {
    val maxVal = 9200f
    val limitVal = 7500f

    Column(modifier = Modifier.fillMaxWidth()) {
        // Limit legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(2.dp)
                    .background(Color(0xFFE11D48))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Planning limit 7,500 kWh",
                fontSize = 10.sp,
                color = Color(0xFFE11D48),
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaFontFamily
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bar Chart Rows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            records.forEach { record ->
                val isSelected = record == selectedDay
                val barFraction = (record.consumptionKwh.toFloat() / maxVal).coerceIn(0.1f, 1f)
                val barColor = when {
                    isSelected -> OrangePrimary
                    record.isOverLimit -> Color(0xFFE11D48)
                    else -> OrangeSecondary
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(record) }
                        .padding(horizontal = 2.dp)
                ) {
                    // Top Value Label
                    Text(
                        text = "${(record.consumptionKwh / 1000).format(1)}k",
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) OrangePrimary else if (record.isOverLimit) Color(0xFFE11D48) else TextSecondaryGray,
                        fontFamily = OutfitFontFamily
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Bar Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(130.dp * barFraction)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(barColor)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Day Name Chip
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isSelected) OrangeContainerLight else Color.Transparent
                    ) {
                        Text(
                            text = record.dayShort,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) OrangePrimaryDark else TextCharcoal,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RootCauseCard(cause: OverLimitCause) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CleanCanvas,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cause.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                if (cause.excessKwh > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFFFF1F2)
                    ) {
                        Text(
                            text = "+${cause.excessKwh.toInt()} kWh",
                            color = Color(0xFFE11D48),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFDCFCE7)
                    ) {
                        Text(
                            text = "RESOLVED",
                            color = Color(0xFF16A34A),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Facility: ${cause.location}",
                fontSize = 11.sp,
                color = OrangePrimary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaFontFamily
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = cause.rootCause,
                fontSize = 11.sp,
                color = TextSecondaryGray,
                lineHeight = 16.sp,
                fontFamily = PlusJakartaFontFamily
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Action: ${cause.recommendedAction}",
                fontSize = 11.sp,
                color = TextCharcoal,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp,
                fontFamily = PlusJakartaFontFamily
            )
        }
    }
}

@Composable
fun PolishedWeeklyReportCard(
    report: WeeklyEnergyReport,
    onGenerate: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = CleanCanvas,
        border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Energy Audit: ${report.weekLabel}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        fontFamily = OutfitFontFamily
                    )
                    Text(
                        text = report.dateRange,
                        fontSize = 11.sp,
                        color = TextSecondaryGray,
                        fontFamily = PlusJakartaFontFamily
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (report.isOverallOverLimit) Color(0xFFFFF1F2) else Color(0xFFDCFCE7)
                ) {
                    Text(
                        text = if (report.isOverallOverLimit) "OVER LIMIT" else "WITHIN GUIDE",
                        color = if (report.isOverallOverLimit) Color(0xFFE11D48) else Color(0xFF16A34A),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = OutfitFontFamily,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4 Key Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ReportDataTile(
                    label = "Total Draw",
                    value = "${formatNumber(report.totalKwh.toLong())} kWh",
                    modifier = Modifier.weight(1f)
                )
                ReportDataTile(
                    label = "Daily Avg",
                    value = "${formatNumber(report.dailyAvgKwh.toLong())} kWh",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ReportDataTile(
                    label = "Peak Day",
                    value = "${report.peakDay} (${(report.peakKwh / 1000).format(1)}k)",
                    modifier = Modifier.weight(1f)
                )
                ReportDataTile(
                    label = "Carbon Footprint",
                    value = "${report.carbonFootprintTons} T CO2e",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Summary: ${report.summaryAnalysis}",
                fontSize = 12.sp,
                color = TextSecondaryGray,
                lineHeight = 17.sp,
                fontFamily = PlusJakartaFontFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            report.recommendations.forEach { rec ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = OrangePrimary,
                        modifier = Modifier.size(14.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = rec,
                        fontSize = 11.sp,
                        color = TextCharcoal,
                        lineHeight = 15.sp,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onGenerate,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = OrangePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("export_weekly_report_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Generate & Export Week ${report.weekNumber} Report",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = OutfitFontFamily
                )
            }
        }
    }
}

@Composable
fun ReportDataTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = PureWhite,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = TextTertiaryGray,
                fontFamily = PlusJakartaFontFamily
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                fontFamily = OutfitFontFamily
            )
        }
    }
}

private fun formatNumber(number: Long): String {
    return java.text.NumberFormat.getIntegerInstance().format(number)
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)
