package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.BinCompostGreen
import com.example.ui.theme.BinCompostLight
import com.example.ui.theme.BinEWasteLight
import com.example.ui.theme.BinEWasteOrange
import com.example.ui.theme.BinLandfillLight
import com.example.ui.theme.BinLandfillSlate
import com.example.ui.theme.BinPaperAmber
import com.example.ui.theme.BinPaperLight
import com.example.ui.theme.BinPlasticBlue
import com.example.ui.theme.BinPlasticLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeContainerLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangeSecondary
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray

@Composable
fun AuditResultCard(
    result: EcoScanResult,
    onClaimPoints: (EcoScanResult) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val binTheme = resolveBinTheme(result.disposalBin, result.category)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("audit_result_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header: Category Pill & Dismiss
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = binTheme.lightBg,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = result.category.uppercase(),
                            color = binTheme.primaryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "• ${result.status.uppercase()}",
                        fontSize = 11.sp,
                        color = TextTertiaryGray,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PlusJakartaFontFamily
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp).testTag("dismiss_result_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = TextTertiaryGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Detected Item Name
            Text(
                text = result.itemName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                fontFamily = OutfitFontFamily,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Designated Campus Disposal Bin Hero Banner
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = binTheme.lightBg),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = binTheme.primaryColor,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "DESIGNATED CAMPUS BIN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = binTheme.primaryColor.copy(alpha = 0.9f),
                                letterSpacing = 0.5.sp,
                                fontFamily = OutfitFontFamily
                            )
                            Text(
                                text = result.disposalBin,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal,
                                fontFamily = OutfitFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = TextSecondaryGray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = result.campusLocationRecommendation,
                            fontSize = 12.sp,
                            color = TextSecondaryGray,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PlusJakartaFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Environmental Impact 3-Metric Grid
            Text(
                text = "Estimated Ecological Footprint",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                fontFamily = OutfitFontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ImpactMetricTile(
                    label = "CO2e Embodied",
                    value = "${result.estimatedImpact.carbonFootprintKg} kg",
                    icon = Icons.Default.Public,
                    color = Color(0xFFE11D48),
                    modifier = Modifier.weight(1f)
                )
                ImpactMetricTile(
                    label = "Virtual Water",
                    value = "${result.estimatedImpact.waterFootprintLiters} L",
                    icon = Icons.Default.Opacity,
                    color = Color(0xFF0284C7),
                    modifier = Modifier.weight(1f)
                )
                ImpactMetricTile(
                    label = "Decomposition",
                    value = "${result.estimatedImpact.decompositionYears} yrs",
                    icon = Icons.Default.Schedule,
                    color = OrangePrimary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Actionable Advice Card (Warm White with Orange Accent)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = OrangeContainerLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Advice",
                        tint = OrangePrimary,
                        modifier = Modifier.size(20.dp).padding(top = 1.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = result.actionableAdvice,
                        fontSize = 12.sp,
                        color = TextCharcoal,
                        lineHeight = 17.sp,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Claim Eco-Points Action Button (Vibrant Orange & White)
            Button(
                onClick = { onClaimPoints(result) },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = PureWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("claim_points_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Claim +${result.ecoPointsEarned} Eco-Points",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = OutfitFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun ImpactMetricTile(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CleanCanvas,
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = TextSecondaryGray,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PlusJakartaFontFamily
                )
            }
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

data class BinTheme(
    val primaryColor: Color,
    val lightBg: Color
)

fun resolveBinTheme(binName: String, category: String): BinTheme {
    val lower = "$binName $category".lowercase()
    return when {
        lower.contains("green") || lower.contains("compost") || lower.contains("organic") -> {
            BinTheme(BinCompostGreen, BinCompostLight)
        }
        lower.contains("blue") || lower.contains("plastic") || lower.contains("metal") || lower.contains("can") -> {
            BinTheme(BinPlasticBlue, BinPlasticLight)
        }
        lower.contains("paper") || lower.contains("cardboard") || lower.contains("yellow") -> {
            BinTheme(BinPaperAmber, BinPaperLight)
        }
        lower.contains("e-waste") || lower.contains("orange") || lower.contains("tech") || lower.contains("cable") -> {
            BinTheme(BinEWasteOrange, BinEWasteLight)
        }
        else -> {
            BinTheme(BinLandfillSlate, BinLandfillLight)
        }
    }
}
