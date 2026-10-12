package com.example.data.model

import android.graphics.Bitmap

data class EstimatedImpact(
    val carbonFootprintKg: Double,
    val waterFootprintLiters: Double,
    val decompositionYears: Double
)

data class EcoScanResult(
    val id: String = java.util.UUID.randomUUID().toString(),
    val status: String = "success",
    val itemName: String,
    val category: String,
    val disposalBin: String,
    val campusLocationRecommendation: String,
    val estimatedImpact: EstimatedImpact,
    val actionableAdvice: String,
    val ecoPointsEarned: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val imageBitmap: Bitmap? = null,
    val sampleKey: String? = null
)

data class CampusStats(
    val campusName: String = "SVVV Indore",
    val greeting: String = "Good morning, SVVV",
    val carbonMitigatedKg: Double = 186.4,
    val energySavedKwh: Double = 3420.0,
    val waterConservedLiters: Double = 12480.0,
    val sustainabilityIndex: Int = 84,
    val waterDailyLiters: Double = 24860.0,
    val waterGuidePercent: Int = 89,
    val electricityDailyKwh: Double = 7800.0,
    val electricityGuidePercent: Int = 104,
    val wasteDailyKg: Double = 390.0,
    val wasteGuidePercent: Int = 111,
    val electricityLimitKwh: Double = 7500.0
)

data class DailyElectricityRecord(
    val dayName: String,
    val dayShort: String,
    val consumptionKwh: Double,
    val limitKwh: Double = 7500.0,
    val primaryDriver: String,
    val peakHour: String
) {
    val isOverLimit: Boolean get() = consumptionKwh > limitKwh
    val percentOfLimit: Int get() = ((consumptionKwh / limitKwh) * 100).toInt()
    val excessKwh: Double get() = if (isOverLimit) consumptionKwh - limitKwh else 0.0
}

data class OverLimitCause(
    val id: String,
    val title: String,
    val excessKwh: Double,
    val location: String,
    val rootCause: String,
    val recommendedAction: String,
    val estimatedSavingsKwh: Double
)

data class WeeklyEnergyReport(
    val weekNumber: Int,
    val weekLabel: String,
    val dateRange: String,
    val totalKwh: Double,
    val dailyAvgKwh: Double,
    val limitTotalKwh: Double,
    val peakDay: String,
    val peakKwh: Double,
    val carbonFootprintTons: Double,
    val costEstimatedInr: Long,
    val isOverallOverLimit: Boolean,
    val summaryAnalysis: String,
    val recommendations: List<String>
)

data class CampusEvent(
    val id: String,
    val title: String,
    val description: String,
    val carbonDeltaKg: Double,
    val energyDeltaKwh: Double,
    val waterDeltaL: Double,
    val indexDelta: Int
)

data class CampusBinInfo(
    val name: String,
    val colorTag: String,
    val description: String,
    val acceptedItems: List<String>,
    val prohibitedItems: List<String>,
    val primaryLocations: List<String>
)

data class CampusDemoSample(
    val key: String,
    val title: String,
    val emoji: String,
    val category: String,
    val promptHint: String,
    val defaultResult: EcoScanResult
)
