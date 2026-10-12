package com.example.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CampusBinInfo
import com.example.data.model.CampusDemoSample
import com.example.data.model.CampusEvent
import com.example.data.model.CampusStats
import com.example.data.model.DailyElectricityRecord
import com.example.data.model.EcoScanResult
import com.example.data.model.OverLimitCause
import com.example.data.model.WeeklyEnergyReport
import com.example.data.repository.EcoAuditRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class NavigationTab {
    OVERVIEW,
    ELECTRICITY,
    SCANNER,
    CAMPUS_BINS,
    ECO_LEDGER
}

data class EcoLensUiState(
    val currentTab: NavigationTab = NavigationTab.OVERVIEW,
    val campusStats: CampusStats = CampusStats(),
    val dailyElectricity: List<DailyElectricityRecord> = emptyList(),
    val overLimitCauses: List<OverLimitCause> = emptyList(),
    val weeklyReports: List<WeeklyEnergyReport> = emptyList(),
    val selectedWeekIndex: Int = 3, // Week 4 (Current)
    val selectedDayRecord: DailyElectricityRecord? = null,
    val totalEcoPoints: Int = 145,
    val totalCo2SavedKg: Double = 186.4,
    val itemsAuditedCount: Int = 6,
    val auditHistory: List<EcoScanResult> = emptyList(),
    val activeScanResult: EcoScanResult? = null,
    val isScanning: Boolean = false,
    val scanningStatusText: String = "Ready to audit",
    val activeCategoryFilter: String = "All",
    val snackbarMessage: String? = null,
    val showEditStatsDialog: Boolean = false,
    val showSimulateEventDialog: Boolean = false,
    val isMitigationApplied: Boolean = false
)

class EcoLensViewModel(
    private val repository: EcoAuditRepository = EcoAuditRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(EcoLensUiState())
    val uiState: StateFlow<EcoLensUiState> = _uiState.asStateFlow()

    val demoSamples: List<CampusDemoSample> = repository.demoSamples
    val campusBins: List<CampusBinInfo> = repository.campusBins

    val campusEvents: List<CampusEvent> = listOf(
        CampusEvent(
            id = "cafeteria_zero_waste",
            title = "Zero-Waste Cafeteria Drive",
            description = "Diverts 85% of food scraps into campus composters during peak lunch hours.",
            carbonDeltaKg = 18.5,
            energyDeltaKwh = 120.0,
            waterDeltaL = 850.0,
            indexDelta = 3
        ),
        CampusEvent(
            id = "solar_hostel_hour",
            title = "Hostel Energy Conservation Blitz",
            description = "Campus-wide voluntary off-peak energy shift and smart lighting switch-off.",
            carbonDeltaKg = 32.0,
            energyDeltaKwh = 480.0,
            waterDeltaL = 120.0,
            indexDelta = 4
        ),
        CampusEvent(
            id = "svvv_hackathon_cleanup",
            title = "SVVV Hackathon E-Waste Collection",
            description = "Safe recovery of damaged lithium cells, circuit boards, and peripherals at Tech Desk.",
            carbonDeltaKg = 24.0,
            energyDeltaKwh = 180.0,
            waterDeltaL = 340.0,
            indexDelta = 2
        )
    )

    init {
        val initialHistory = listOf(
            repository.demoSamples[1].defaultResult.copy(
                id = "init_1",
                timestamp = System.currentTimeMillis() - 3600000 * 2
            ),
            repository.demoSamples[2].defaultResult.copy(
                id = "init_2",
                timestamp = System.currentTimeMillis() - 3600000 * 5
            ),
            repository.demoSamples[0].defaultResult.copy(
                id = "init_3",
                timestamp = System.currentTimeMillis() - 3600000 * 24
            )
        )

        val days = listOf(
            DailyElectricityRecord("Monday", "Mon", 7250.0, 7500.0, "Classrooms & Central Library", "11:30 AM"),
            DailyElectricityRecord("Tuesday", "Tue", 7420.0, 7500.0, "Engineering Laboratories", "2:15 PM"),
            DailyElectricityRecord("Wednesday", "Wed", 8120.0, 7500.0, "High Compute Cluster & AC Chillers", "1:45 PM"),
            DailyElectricityRecord("Thursday", "Thu", 7960.0, 7500.0, "Auditorium Seminars & Labs", "3:00 PM"),
            DailyElectricityRecord("Friday", "Fri", 8350.0, 7500.0, "Workshop Machinery & Server Load", "2:30 PM"),
            DailyElectricityRecord("Saturday", "Sat", 6150.0, 7500.0, "Library & Hostels Weekend Load", "7:00 PM"),
            DailyElectricityRecord("Sunday", "Sun", 5400.0, 7500.0, "Hostel Domestic Baseline", "8:15 PM")
        )

        val causes = listOf(
            OverLimitCause(
                id = "cause_hvac",
                title = "Central Chiller Overcooling (Blocks B & C)",
                excessKwh = 420.0,
                location = "Engineering & Science Complex",
                rootCause = "Thermostats set to 19°C instead of recommended 24°C standard during 12:00 PM – 4:00 PM peak outdoor heat.",
                recommendedAction = "Implement campus automated setpoint setback to 24°C and pre-cool facilities prior to 11 AM.",
                estimatedSavingsKwh = 380.0
            ),
            OverLimitCause(
                id = "cause_compute",
                title = "Overnight AI Workstation & Server Standby",
                excessKwh = 270.0,
                location = "SVVV Tech Park & IT Labs",
                rootCause = "Over 180 desktop workstations and GPU nodes running idle overnight without dynamic power sleep policies.",
                recommendedAction = "Deploy automated Wake-on-LAN and sleep policies for all non-essential academic computer labs at 9:00 PM.",
                estimatedSavingsKwh = 240.0
            ),
            OverLimitCause(
                id = "cause_lights",
                title = "Sports Complex & Quad Floodlights After Hours",
                excessKwh = 150.0,
                location = "Auditorium & Athletic Grounds",
                rootCause = "Manual lighting timers desynchronized, leaving 2,000W metal-halide fixtures illuminated past 11:00 PM.",
                recommendedAction = "Sync astronomical digital timers with automatic shut-off at 10:00 PM.",
                estimatedSavingsKwh = 140.0
            )
        )

        val reports = listOf(
            WeeklyEnergyReport(
                weekNumber = 1,
                weekLabel = "Week 1",
                dateRange = "Oct 1 – Oct 7",
                totalKwh = 49200.0,
                dailyAvgKwh = 7028.0,
                limitTotalKwh = 52500.0,
                peakDay = "Friday",
                peakKwh = 7650.0,
                carbonFootprintTons = 40.3,
                costEstimatedInr = 393600,
                isOverallOverLimit = false,
                summaryAnalysis = "Week 1 operated within planning guide. Mild temperatures kept chiller loads minimal.",
                recommendations = listOf("Maintain 24°C cooling standard across academic blocks.")
            ),
            WeeklyEnergyReport(
                weekNumber = 2,
                weekLabel = "Week 2",
                dateRange = "Oct 8 – Oct 14",
                totalKwh = 51400.0,
                dailyAvgKwh = 7342.0,
                limitTotalKwh = 52500.0,
                peakDay = "Thursday",
                peakKwh = 7890.0,
                carbonFootprintTons = 42.1,
                costEstimatedInr = 411200,
                isOverallOverLimit = false,
                summaryAnalysis = "Moderate increase due to mid-term laboratory examinations.",
                recommendations = listOf("Stagger high-power lab equipment start times to avoid demand peaks.")
            ),
            WeeklyEnergyReport(
                weekNumber = 3,
                weekLabel = "Week 3",
                dateRange = "Oct 15 – Oct 21",
                totalKwh = 53100.0,
                dailyAvgKwh = 7585.0,
                limitTotalKwh = 52500.0,
                peakDay = "Wednesday",
                peakKwh = 8240.0,
                carbonFootprintTons = 43.5,
                costEstimatedInr = 424800,
                isOverallOverLimit = true,
                summaryAnalysis = "Overall limit breached by +1.1% driven by campus tech symposium and auditorium cooling.",
                recommendations = listOf("Utilize smart zone cooling during multi-hall conferences.")
            ),
            WeeklyEnergyReport(
                weekNumber = 4,
                weekLabel = "Week 4 (Current)",
                dateRange = "Oct 22 – Oct 28",
                totalKwh = 54650.0,
                dailyAvgKwh = 7807.0,
                limitTotalKwh = 52500.0,
                peakDay = "Friday",
                peakKwh = 8350.0,
                carbonFootprintTons = 44.8,
                costEstimatedInr = 437200,
                isOverallOverLimit = true,
                summaryAnalysis = "Current week is 104% of guide. Daily average exceeds the 7,500 kWh target by +307 kWh/day.",
                recommendations = listOf(
                    "Activate Chiller Setback: Adjust cooling thermostats to 24°C.",
                    "Enable IT Lab Standby Policy: Power down idle GPU nodes.",
                    "Recalibrate sports floodlighting timers."
                )
            )
        )

        _uiState.update {
            it.copy(
                auditHistory = initialHistory,
                dailyElectricity = days,
                overLimitCauses = causes,
                weeklyReports = reports,
                selectedDayRecord = days[4] // Friday peak as initial selection
            )
        }
    }

    fun setTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectDayRecord(record: DailyElectricityRecord) {
        _uiState.update { it.copy(selectedDayRecord = record) }
    }

    fun selectWeekIndex(index: Int) {
        _uiState.update { it.copy(selectedWeekIndex = index) }
    }

    fun applyMitigationShaving() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isMitigationApplied = true,
                    snackbarMessage = "Applying Automated Peak Load Shaving across SVVV..."
                )
            }
            delay(800)

            _uiState.update { current ->
                val shavedDays = current.dailyElectricity.map { day ->
                    if (day.isOverLimit) {
                        day.copy(consumptionKwh = 7350.0)
                    } else {
                        day
                    }
                }

                val shavedStats = current.campusStats.copy(
                    electricityDailyKwh = 7240.0,
                    electricityGuidePercent = 96,
                    energySavedKwh = current.campusStats.energySavedKwh + 560.0,
                    sustainabilityIndex = (current.campusStats.sustainabilityIndex + 5).coerceAtMost(100)
                )

                val updatedCauses = current.overLimitCauses.map {
                    it.copy(excessKwh = 0.0)
                }

                current.copy(
                    campusStats = shavedStats,
                    dailyElectricity = shavedDays,
                    overLimitCauses = updatedCauses,
                    selectedDayRecord = shavedDays[4],
                    totalEcoPoints = current.totalEcoPoints + 75,
                    snackbarMessage = "Peak Load Shaved! Daily consumption reduced below 7,500 kWh limit (+75 pts)!"
                )
            }
        }
    }

    fun generateWeeklyReport(weekNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(snackbarMessage = "Generating comprehensive Week $weekNumber audit report...") }
            delay(600)
            _uiState.update {
                it.copy(
                    selectedWeekIndex = (weekNumber - 1).coerceIn(0, it.weeklyReports.lastIndex),
                    snackbarMessage = "Week $weekNumber Energy Telemetry Report Ready!"
                )
            }
        }
    }

    fun setCategoryFilter(category: String) {
        _uiState.update { it.copy(activeCategoryFilter = category) }
    }

    fun openEditStats(open: Boolean) {
        _uiState.update { it.copy(showEditStatsDialog = open) }
    }

    fun openSimulateEvent(open: Boolean) {
        _uiState.update { it.copy(showSimulateEventDialog = open) }
    }

    fun updateCampusStats(carbon: Double, energy: Double, water: Double, index: Int) {
        _uiState.update { current ->
            current.copy(
                campusStats = current.campusStats.copy(
                    carbonMitigatedKg = carbon,
                    energySavedKwh = energy,
                    waterConservedLiters = water,
                    sustainabilityIndex = index.coerceIn(0, 100)
                ),
                showEditStatsDialog = false,
                snackbarMessage = "Campus telemetry updated successfully!"
            )
        }
    }

    fun applySimulationEvent(event: CampusEvent) {
        _uiState.update { current ->
            val prev = current.campusStats
            val updatedStats = prev.copy(
                carbonMitigatedKg = ((prev.carbonMitigatedKg + event.carbonDeltaKg) * 10).toLong() / 10.0,
                energySavedKwh = prev.energySavedKwh + event.energyDeltaKwh,
                waterConservedLiters = prev.waterConservedLiters + event.waterDeltaL,
                sustainabilityIndex = (prev.sustainabilityIndex + event.indexDelta).coerceAtMost(100),
                wasteDailyKg = (prev.wasteDailyKg - 15.0).coerceAtLeast(150.0),
                wasteGuidePercent = (prev.wasteGuidePercent - 4).coerceAtLeast(80)
            )

            current.copy(
                campusStats = updatedStats,
                showSimulateEventDialog = false,
                totalEcoPoints = current.totalEcoPoints + 50,
                snackbarMessage = "Simulated: ${event.title}! +${event.carbonDeltaKg}kg CO2e mitigated."
            )
        }
    }

    fun auditBitmap(bitmap: Bitmap, promptHint: String? = null) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentTab = NavigationTab.SCANNER,
                    isScanning = true,
                    scanningStatusText = "Analyzing resource spectrum with Gemini..."
                )
            }

            delay(600)
            _uiState.update { it.copy(scanningStatusText = "Calculating embodied carbon & water footprint...") }

            delay(600)
            _uiState.update { it.copy(scanningStatusText = "Routing to SVVV Indore zero-waste station...") }

            val result = repository.auditImage(bitmap, promptHint)

            _uiState.update {
                it.copy(
                    isScanning = false,
                    activeScanResult = result,
                    scanningStatusText = "Audit complete"
                )
            }
        }
    }

    fun auditDemoSample(sample: CampusDemoSample) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isScanning = true,
                    scanningStatusText = "Auditing ${sample.title}..."
                )
            }

            delay(700)
            _uiState.update { it.copy(scanningStatusText = "Simulating campus sorting & lifecycle audit...") }

            delay(700)
            val generatedBitmap = createSyntheticBitmapForSample(sample.emoji)
            val result = sample.defaultResult.copy(
                id = java.util.UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                imageBitmap = generatedBitmap
            )

            _uiState.update {
                it.copy(
                    isScanning = false,
                    activeScanResult = result,
                    scanningStatusText = "Analysis complete"
                )
            }
        }
    }

    fun claimPoints(result: EcoScanResult) {
        _uiState.update { current ->
            val updatedHistory = listOf(result) + current.auditHistory.filter { it.id != result.id }
            val newTotalPoints = current.totalEcoPoints + result.ecoPointsEarned
            val newCarbon = ((current.campusStats.carbonMitigatedKg + result.estimatedImpact.carbonFootprintKg) * 10).toLong() / 10.0
            val newCount = current.itemsAuditedCount + 1

            val updatedStats = current.campusStats.copy(
                carbonMitigatedKg = newCarbon,
                sustainabilityIndex = (current.campusStats.sustainabilityIndex + 1).coerceAtMost(100)
            )

            current.copy(
                campusStats = updatedStats,
                totalEcoPoints = newTotalPoints,
                totalCo2SavedKg = newCarbon,
                itemsAuditedCount = newCount,
                auditHistory = updatedHistory,
                activeScanResult = null,
                snackbarMessage = "+${result.ecoPointsEarned} Eco-Points Awarded to SVVV Account!"
            )
        }
    }

    fun dismissActiveResult() {
        _uiState.update { it.copy(activeScanResult = null) }
    }

    fun clearSnackbarMessage() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    private fun createSyntheticBitmapForSample(emoji: String): Bitmap {
        val size = 512
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val bgPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#FFF2EB")
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), bgPaint)

        val emojiPaint = Paint().apply {
            textSize = 140f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(emoji, size / 2f, size / 2f + 40f, emojiPaint)

        return bitmap
    }
}
