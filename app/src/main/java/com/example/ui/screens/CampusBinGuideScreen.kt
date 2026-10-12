package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CampusBinInfo
import com.example.ui.components.resolveBinTheme
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.theme.TextTertiaryGray

@Composable
fun CampusBinGuideScreen(
    campusBins: List<CampusBinInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CleanCanvas)
            .testTag("campus_bin_guide_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                Text(
                    text = "SVVV Campus Bin Standards",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Official color-coded zero-waste station guide across SVVV Indore academic blocks.",
                    fontSize = 13.sp,
                    color = TextSecondaryGray,
                    fontFamily = PlusJakartaFontFamily
                )
            }
        }

        items(campusBins) { binInfo ->
            CampusBinCard(binInfo = binInfo)
        }
    }
}

@Composable
fun CampusBinCard(binInfo: CampusBinInfo) {
    var expanded by remember { mutableStateOf(false) }
    val binTheme = resolveBinTheme(binInfo.name, binInfo.colorTag)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .testTag("bin_card_${binInfo.colorTag.lowercase()}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = binTheme.primaryColor,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = binInfo.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            fontFamily = OutfitFontFamily
                        )
                        Text(
                            text = "${binInfo.colorTag} Bin Protocol",
                            fontSize = 11.sp,
                            color = binTheme.primaryColor,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = OutfitFontFamily
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = TextTertiaryGray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = binInfo.description,
                fontSize = 13.sp,
                color = TextSecondaryGray,
                lineHeight = 18.sp,
                fontFamily = PlusJakartaFontFamily
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                ) {
                    Text(
                        text = "ACCEPTED MATERIALS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = binTheme.primaryColor,
                        letterSpacing = 0.5.sp,
                        fontFamily = OutfitFontFamily
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    binInfo.acceptedItems.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = binTheme.primaryColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item,
                                fontSize = 12.sp,
                                color = TextCharcoal,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "DO NOT DEPOSIT (CONTAMINANTS)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color(0xFFE11D48),
                        letterSpacing = 0.5.sp,
                        fontFamily = OutfitFontFamily
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    binInfo.prohibitedItems.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Block,
                                contentDescription = null,
                                tint = androidx.compose.ui.graphics.Color(0xFFE11D48),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item,
                                fontSize = 12.sp,
                                color = TextSecondaryGray,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "SVVV CAMPUS LOCATIONS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary,
                        letterSpacing = 0.5.sp,
                        fontFamily = OutfitFontFamily
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    binInfo.primaryLocations.forEach { loc ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = OrangePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = loc,
                                fontSize = 12.sp,
                                color = TextSecondaryGray,
                                fontFamily = PlusJakartaFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}
