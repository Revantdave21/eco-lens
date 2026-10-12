package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AuditResultCard
import com.example.ui.components.CampusSidebarDrawer
import com.example.ui.components.ScannerViewfinder
import com.example.ui.screens.CampusBinGuideScreen
import com.example.ui.screens.CampusOverviewScreen
import com.example.ui.screens.EcoLedgerScreen
import com.example.ui.screens.EditCampusStatsDialog
import com.example.ui.screens.ElectricityScreen
import com.example.ui.screens.SimulateCampusEventDialog
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CleanCanvas
import com.example.ui.theme.EcoLensTheme
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeContainerLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.OrangePrimaryDark
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextCharcoal
import com.example.ui.theme.TextSecondaryGray
import com.example.ui.viewmodel.EcoLensViewModel
import com.example.ui.viewmodel.NavigationTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: EcoLensViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoLensTheme {
                EcoLensApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoLensApp(viewModel: EcoLensViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbarMessage()
        }
    }

    // Dialogs
    if (uiState.showEditStatsDialog) {
        EditCampusStatsDialog(
            currentStats = uiState.campusStats,
            onDismiss = { viewModel.openEditStats(false) },
            onSave = { c, e, w, i -> viewModel.updateCampusStats(c, e, w, i) }
        )
    }

    if (uiState.showSimulateEventDialog) {
        SimulateCampusEventDialog(
            events = viewModel.campusEvents,
            onDismiss = { viewModel.openSimulateEvent(false) },
            onSelectEvent = { event -> viewModel.applySimulationEvent(event) }
        )
    }

    // Working Sidebar Modal Navigation Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CampusSidebarDrawer(
                currentTab = uiState.currentTab,
                onSelectTab = { tab ->
                    viewModel.setTab(tab)
                    scope.launch { drawerState.close() }
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                },
                totalPoints = uiState.totalEcoPoints,
                carbonMitigated = uiState.campusStats.carbonMitigatedKg
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("ecolens_root_scaffold"),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier.testTag("open_sidebar_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Explore campus sidebar",
                                tint = TextCharcoal
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "SVVV Indore",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextCharcoal,
                                        fontFamily = OutfitFontFamily
                                    )
                                    Text(
                                        text = when (uiState.currentTab) {
                                            NavigationTab.OVERVIEW -> " / Overview"
                                            NavigationTab.ELECTRICITY -> " / Electricity"
                                            NavigationTab.SCANNER -> " / Scanner"
                                            NavigationTab.CAMPUS_BINS -> " / Campus Bins"
                                            NavigationTab.ECO_LEDGER -> " / Ledger"
                                        },
                                        fontSize = 13.sp,
                                        color = TextSecondaryGray,
                                        fontFamily = PlusJakartaFontFamily
                                    )
                                }
                                Text(
                                    text = "EcoLens Campus AI",
                                    fontSize = 11.sp,
                                    color = OrangePrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = OutfitFontFamily
                                )
                            }

                            // Eco-Points & User Profile Pill
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = OrangeContainerLight,
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .testTag("user_points_badge")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = null,
                                        tint = OrangePrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${uiState.totalEcoPoints} pts",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OrangePrimaryDark,
                                        fontFamily = OutfitFontFamily
                                    )
                                }
                            }

                            Surface(
                                shape = CircleShape,
                                color = PureWhite,
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "SV",
                                        color = OrangePrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = OutfitFontFamily
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PureWhite
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = PureWhite,
                    tonalElevation = 8.dp,
                    modifier = Modifier.border(1.dp, BorderSubtle)
                ) {
                    NavigationBarItem(
                        selected = uiState.currentTab == NavigationTab.OVERVIEW,
                        onClick = { viewModel.setTab(NavigationTab.OVERVIEW) },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Overview") },
                        label = {
                            Text(
                                text = "Overview",
                                fontSize = 10.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (uiState.currentTab == NavigationTab.OVERVIEW) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryGray,
                            unselectedTextColor = TextSecondaryGray,
                            indicatorColor = OrangeContainerLight
                        ),
                        modifier = Modifier.testTag("tab_overview")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == NavigationTab.ELECTRICITY,
                        onClick = { viewModel.setTab(NavigationTab.ELECTRICITY) },
                        icon = { Icon(Icons.Default.Bolt, contentDescription = "Electricity") },
                        label = {
                            Text(
                                text = "Electricity",
                                fontSize = 10.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (uiState.currentTab == NavigationTab.ELECTRICITY) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryGray,
                            unselectedTextColor = TextSecondaryGray,
                            indicatorColor = OrangeContainerLight
                        ),
                        modifier = Modifier.testTag("tab_electricity")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == NavigationTab.SCANNER,
                        onClick = { viewModel.setTab(NavigationTab.SCANNER) },
                        icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "AI Scanner") },
                        label = {
                            Text(
                                text = "Scanner",
                                fontSize = 10.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (uiState.currentTab == NavigationTab.SCANNER) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryGray,
                            unselectedTextColor = TextSecondaryGray,
                            indicatorColor = OrangeContainerLight
                        ),
                        modifier = Modifier.testTag("tab_scanner")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == NavigationTab.CAMPUS_BINS,
                        onClick = { viewModel.setTab(NavigationTab.CAMPUS_BINS) },
                        icon = { Icon(Icons.Default.Delete, contentDescription = "Campus Bins") },
                        label = {
                            Text(
                                text = "Bins",
                                fontSize = 10.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (uiState.currentTab == NavigationTab.CAMPUS_BINS) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryGray,
                            unselectedTextColor = TextSecondaryGray,
                            indicatorColor = OrangeContainerLight
                        ),
                        modifier = Modifier.testTag("tab_campus_bins")
                    )

                    NavigationBarItem(
                        selected = uiState.currentTab == NavigationTab.ECO_LEDGER,
                        onClick = { viewModel.setTab(NavigationTab.ECO_LEDGER) },
                        icon = { Icon(Icons.Default.History, contentDescription = "Ledger") },
                        label = {
                            Text(
                                text = "Ledger",
                                fontSize = 10.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (uiState.currentTab == NavigationTab.ECO_LEDGER) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryGray,
                            unselectedTextColor = TextSecondaryGray,
                            indicatorColor = OrangeContainerLight
                        ),
                        modifier = Modifier.testTag("tab_eco_ledger")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(CleanCanvas)
            ) {
                when (uiState.currentTab) {
                    NavigationTab.OVERVIEW -> {
                        CampusOverviewScreen(
                            stats = uiState.campusStats,
                            events = viewModel.campusEvents,
                            onOpenEditStats = { viewModel.openEditStats(true) },
                            onOpenSimulateEvent = { viewModel.openSimulateEvent(true) },
                            onNavigateToScanner = { viewModel.setTab(NavigationTab.SCANNER) },
                            onNavigateToElectricity = { viewModel.setTab(NavigationTab.ELECTRICITY) }
                        )
                    }

                    NavigationTab.ELECTRICITY -> {
                        ElectricityScreen(
                            dailyRecords = uiState.dailyElectricity,
                            selectedDay = uiState.selectedDayRecord,
                            overLimitCauses = uiState.overLimitCauses,
                            weeklyReports = uiState.weeklyReports,
                            selectedWeekIndex = uiState.selectedWeekIndex,
                            isMitigationApplied = uiState.isMitigationApplied,
                            onSelectDay = { day -> viewModel.selectDayRecord(day) },
                            onSelectWeek = { weekIdx -> viewModel.selectWeekIndex(weekIdx) },
                            onApplyMitigation = { viewModel.applyMitigationShaving() },
                            onGenerateReport = { weekNum -> viewModel.generateWeeklyReport(weekNum) }
                        )
                    }

                    NavigationTab.SCANNER -> {
                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .padding(vertical = 12.dp)
                                .testTag("scanner_screen_container")
                        ) {
                            ScannerViewfinder(
                                isScanning = uiState.isScanning,
                                scanningStatusText = uiState.scanningStatusText,
                                demoSamples = viewModel.demoSamples,
                                onBitmapCaptured = { bmp -> viewModel.auditBitmap(bmp) },
                                onSampleSelected = { sample -> viewModel.auditDemoSample(sample) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            AnimatedVisibility(
                                visible = uiState.activeScanResult != null,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                uiState.activeScanResult?.let { result ->
                                    AuditResultCard(
                                        result = result,
                                        onClaimPoints = { viewModel.claimPoints(it) },
                                        onDismiss = { viewModel.dismissActiveResult() }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }

                    NavigationTab.CAMPUS_BINS -> {
                        CampusBinGuideScreen(campusBins = viewModel.campusBins)
                    }

                    NavigationTab.ECO_LEDGER -> {
                        EcoLedgerScreen(
                            totalPoints = uiState.totalEcoPoints,
                            totalCo2SavedKg = uiState.totalCo2SavedKg,
                            itemsAuditedCount = uiState.itemsAuditedCount,
                            history = uiState.auditHistory,
                            activeFilter = uiState.activeCategoryFilter,
                            onFilterChanged = { viewModel.setCategoryFilter(it) },
                            onItemClick = { clicked ->
                                viewModel.auditBitmap(
                                    bitmap = clicked.imageBitmap ?: android.graphics.Bitmap.createBitmap(1, 1, android.graphics.Bitmap.Config.ARGB_8888),
                                    promptHint = clicked.itemName
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
