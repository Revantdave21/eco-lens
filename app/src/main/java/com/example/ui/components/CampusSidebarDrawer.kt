package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
import com.example.ui.viewmodel.NavigationTab

@Composable
fun CampusSidebarDrawer(
    currentTab: NavigationTab,
    onSelectTab: (NavigationTab) -> Unit,
    onCloseDrawer: () -> Unit,
    totalPoints: Int,
    carbonMitigated: Double,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .width(310.dp)
            .fillMaxHeight(),
        drawerContainerColor = PureWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Drawer Header: SVVV Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = OrangeContainerLight,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "SV",
                            color = OrangePrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "SVVV Indore",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        fontFamily = OutfitFontFamily
                    )
                    Text(
                        text = "EcoLens Sustainability OS",
                        fontSize = 11.sp,
                        color = OrangePrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats Pill in Sidebar
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CleanCanvas,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$totalPoints pts",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangePrimary,
                            fontFamily = OutfitFontFamily
                        )
                        Text(
                            text = "Student Score",
                            fontSize = 10.sp,
                            color = TextSecondaryGray,
                            fontFamily = PlusJakartaFontFamily
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(30.dp)
                            .background(BorderSubtle)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$carbonMitigated kg",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            fontFamily = OutfitFontFamily
                        )
                        Text(
                            text = "CO2e Mitigated",
                            fontSize = 10.sp,
                            color = TextSecondaryGray,
                            fontFamily = PlusJakartaFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "CAMPUS NAVIGATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextTertiaryGray,
                letterSpacing = 1.sp,
                fontFamily = OutfitFontFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation Items
            SidebarItem(
                label = "Campus Overview",
                icon = Icons.Default.Dashboard,
                isSelected = currentTab == NavigationTab.OVERVIEW,
                onClick = {
                    onSelectTab(NavigationTab.OVERVIEW)
                    onCloseDrawer()
                },
                testTag = "sidebar_nav_overview"
            )

            SidebarItem(
                label = "Daily Electricity & Reports",
                icon = Icons.Default.Bolt,
                isSelected = currentTab == NavigationTab.ELECTRICITY,
                badgeText = "Telemetry",
                onClick = {
                    onSelectTab(NavigationTab.ELECTRICITY)
                    onCloseDrawer()
                },
                testTag = "sidebar_nav_electricity"
            )

            SidebarItem(
                label = "AI Vision Scanner",
                icon = Icons.Default.QrCodeScanner,
                isSelected = currentTab == NavigationTab.SCANNER,
                badgeText = "Gemini AI",
                onClick = {
                    onSelectTab(NavigationTab.SCANNER)
                    onCloseDrawer()
                },
                testTag = "sidebar_nav_scanner"
            )

            SidebarItem(
                label = "Campus Bins Guide",
                icon = Icons.Default.Delete,
                isSelected = currentTab == NavigationTab.CAMPUS_BINS,
                onClick = {
                    onSelectTab(NavigationTab.CAMPUS_BINS)
                    onCloseDrawer()
                },
                testTag = "sidebar_nav_bins"
            )

            SidebarItem(
                label = "Sustainability Ledger",
                icon = Icons.Default.History,
                isSelected = currentTab == NavigationTab.ECO_LEDGER,
                onClick = {
                    onSelectTab(NavigationTab.ECO_LEDGER)
                    onCloseDrawer()
                },
                testTag = "sidebar_nav_ledger"
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = BorderSubtle)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SVVV CAMPUS ZONES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextTertiaryGray,
                letterSpacing = 1.sp,
                fontFamily = OutfitFontFamily
            )

            Spacer(modifier = Modifier.height(10.dp))

            CampusZoneRow("Engineering Quad & MakerSpace", "Active 7,800 kWh")
            CampusZoneRow("Central Library & Study Zone", "Solar Powered")
            CampusZoneRow("Student Cafeteria Composting", "Zero Waste")
            CampusZoneRow("Tech Park Server Cluster", "Peak Load")

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = OrangeContainerLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🌱 Shri Vaishnav Vidyapeeth Vishwavidyalaya • Zero Waste Initiative",
                    fontSize = 10.sp,
                    color = OrangePrimaryDark,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PlusJakartaFontFamily,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun SidebarItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    badgeText: String? = null,
    onClick: () -> Unit,
    testTag: String
) {
    NavigationDrawerItem(
        label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) OrangePrimary else TextCharcoal,
                    fontFamily = OutfitFontFamily
                )
                badgeText?.let {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isSelected) OrangePrimary else OrangeContainerLight
                    ) {
                        Text(
                            text = it,
                            color = if (isSelected) PureWhite else OrangePrimaryDark,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = OutfitFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) OrangePrimary else TextSecondaryGray,
                modifier = Modifier.size(20.dp)
            )
        },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = OrangeContainerLight,
            unselectedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(vertical = 2.dp)
            .testTag(testTag)
    )
}

@Composable
fun CampusZoneRow(name: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(OrangePrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 11.sp,
                color = TextCharcoal,
                fontWeight = FontWeight.Medium,
                fontFamily = PlusJakartaFontFamily
            )
        }
        Text(
            text = status,
            fontSize = 10.sp,
            color = TextTertiaryGray,
            fontFamily = PlusJakartaFontFamily
        )
    }
}
