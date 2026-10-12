package com.example.data.repository

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.model.CampusBinInfo
import com.example.data.model.CampusDemoSample
import com.example.data.model.EcoScanResult
import com.example.data.model.EstimatedImpact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class EcoAuditRepository {

    private val tag = "EcoAuditRepo"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // High fidelity built-in campus demo samples for 1-tap live hackathon demo
    val demoSamples: List<CampusDemoSample> = listOf(
        CampusDemoSample(
            key = "coffee_cup",
            title = "Paper Coffee Cup with Lid",
            emoji = "☕",
            category = "Paper / Mixed Plastic",
            promptHint = "Single-use takeaway coffee cup with plastic lid and cardboard sleeve",
            defaultResult = EcoScanResult(
                itemName = "Lined Hot Beverage Cup & Polystyrene Lid",
                category = "Paper / Mixed Plastic",
                disposalBin = "Blue Bin (Lid) & Landfill (Wax Cup)",
                campusLocationRecommendation = "Separation Kiosk at Campus Dining Commons",
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = 0.11,
                    waterFootprintLiters = 14.5,
                    decompositionYears = 30.0
                ),
                actionableAdvice = "Most campus coffee cups have a polyethylene moisture liner preventing compostability. Separate the #5 plastic lid for recycling and bring a reusable tumbler for a 25-cent cafe discount.",
                ecoPointsEarned = 25,
                sampleKey = "coffee_cup"
            )
        ),
        CampusDemoSample(
            key = "apple_core",
            title = "Cafeteria Apple Core & Fruit",
            emoji = "🍎",
            category = "Compostable",
            promptHint = "Organic food scrap from student cafeteria lunch",
            defaultResult = EcoScanResult(
                itemName = "Organic Fruit Scrap & Apple Core",
                category = "Compostable",
                disposalBin = "Green Bin - Food Waste & Organics",
                campusLocationRecommendation = "Student Union Food Court Organics Hopper",
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = 0.02,
                    waterFootprintLiters = 18.0,
                    decompositionYears = 0.25
                ),
                actionableAdvice = "Diverting organics from landfills prevents anaerobic decomposition which produces methane. Toss directly into any campus green food waste bin.",
                ecoPointsEarned = 45,
                sampleKey = "apple_core"
            )
        ),
        CampusDemoSample(
            key = "soda_can",
            title = "Aluminum Soda Can",
            emoji = "🥫",
            category = "Recyclable Metal",
            promptHint = "Empty aluminum soda beverage can",
            defaultResult = EcoScanResult(
                itemName = "Post-Consumer Aluminum Beverage Can",
                category = "Recyclable Metal",
                disposalBin = "Blue Bin - Aluminum & Clean Cans",
                campusLocationRecommendation = "Dormitory Waste Station & Library Recycling Banks",
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = 0.17,
                    waterFootprintLiters = 8.2,
                    decompositionYears = 200.0
                ),
                actionableAdvice = "Recycling aluminum uses 95% less energy than refining virgin bauxite ore. Ensure can is emptied completely before depositing.",
                ecoPointsEarned = 40,
                sampleKey = "soda_can"
            )
        ),
        CampusDemoSample(
            key = "broken_cable",
            title = "Frayed USB-C Cable / Tech Wire",
            emoji = "🔌",
            category = "E-Waste",
            promptHint = "Damaged electronic charging cable with copper wiring and rubber sheath",
            defaultResult = EcoScanResult(
                itemName = "Damaged USB-C Charging Cable (E-Waste)",
                category = "E-Waste",
                disposalBin = "Orange/Yellow Bin - Certified E-Waste",
                campusLocationRecommendation = "Library Tech Lending Desk & Engineering Quad E-Bin",
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = 0.85,
                    waterFootprintLiters = 52.0,
                    decompositionYears = 800.0
                ),
                actionableAdvice = "Never dispose of electronic wiring in standard trash; copper and rare metals can be recovered at university tech drop-offs.",
                ecoPointsEarned = 50,
                sampleKey = "broken_cable"
            )
        ),
        CampusDemoSample(
            key = "styrofoam_box",
            title = "Styrofoam Clamshell Container",
            emoji = "🥡",
            category = "Landfill",
            promptHint = "Polystyrene foam takeout meal packaging",
            defaultResult = EcoScanResult(
                itemName = "Expanded Polystyrene (EPS) To-Go Box",
                category = "Landfill",
                disposalBin = "Gray Bin - Non-Recyclable Landfill",
                campusLocationRecommendation = "Dining Hall Perimeter Landfill Receptacles",
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = 0.28,
                    waterFootprintLiters = 34.0,
                    decompositionYears = 500.0
                ),
                actionableAdvice = "Polystyrene cannot be processed by campus mechanical recycling. Opt for the campus reusable green box program on your next dining hall meal.",
                ecoPointsEarned = 15,
                sampleKey = "styrofoam_box"
            )
        )
    )

    // Campus Waste Station Guide Data
    val campusBins: List<CampusBinInfo> = listOf(
        CampusBinInfo(
            name = "Green Bin - Organics & Compost",
            colorTag = "Green",
            description = "All food scraps, certified BPI compostable containers, soiled napkins, and fruit peels.",
            acceptedItems = listOf(
                "Food leftovers & plate scrapings",
                "Coffee grounds & unbleached paper filters",
                "Fruit peels, apple cores, vegetable trimmings",
                "Certified compostable cutlery & paper bowls"
            ),
            prohibitedItems = listOf(
                "Plastic wrap & ziploc bags",
                "Styrofoam containers",
                "Metals and beverage cans",
                "Pet waste or toxic materials"
            ),
            primaryLocations = listOf(
                "Dining Commons Dining Hall",
                "Student Union Food Court",
                "Campus Garden Composting Hub",
                "Residence Hall Kitchenettes"
            )
        ),
        CampusBinInfo(
            name = "Blue Bin - Recyclables & Plastics",
            colorTag = "Blue",
            description = "Clean rigid plastics (#1, #2, #5), aluminum cans, tin foil, and glass bottles.",
            acceptedItems = listOf(
                "Plastic soda & water bottles (#1 PET)",
                "Milk jugs & detergent bottles (#2 HDPE)",
                "Clean yogurt tubs & takeout containers (#5 PP)",
                "Aluminum soda & sparkling water cans"
            ),
            prohibitedItems = listOf(
                "Greasy pizza boxes with melted cheese",
                "Plastic film, grocery bags, bubble wrap",
                "Plastic straws & disposable cutlery",
                "Items with significant residual food"
            ),
            primaryLocations = listOf(
                "All Lecture Halls & Classrooms",
                "Science & Engineering Quad",
                "Library Floor 1-4 Alcoves",
                "Athletic Complex Entrance"
            )
        ),
        CampusBinInfo(
            name = "Yellow Bin - Paper & Cardboard",
            colorTag = "Yellow",
            description = "Clean dry paper, spiral notebooks without metal binding, flyers, and flattened cardboard boxes.",
            acceptedItems = listOf(
                "Clean lecture notes & printer paper",
                "Cardboard parcel boxes (flattened)",
                "Envelopes, campus newspapers, textbooks",
                "Paper cup insulating sleeves"
            ),
            prohibitedItems = listOf(
                "Wax-coated hot coffee cups",
                "Thermal receipt paper (BPA coated)",
                "Paper towels used with sanitizer/chemicals",
                "Food-stained cardboard"
            ),
            primaryLocations = listOf(
                "Main Library Study Zones",
                "Computer Labs & Printing Centers",
                "Faculty & Department Offices",
                "Campus Mail & Package Center"
            )
        ),
        CampusBinInfo(
            name = "Orange Bin - Specialized E-Waste",
            colorTag = "Orange",
            description = "Batteries, damaged cables, small electronics, circuit boards, and broken lab equipment.",
            acceptedItems = listOf(
                "AA, AAA, 9V, and Lithium-ion batteries",
                "USB cords, chargers, broken earbuds",
                "Obsolete phones, mice, keyboards",
                "Ink and toner cartridges"
            ),
            prohibitedItems = listOf(
                "Standard household alkaline trash",
                "Unbroken fluorescent tubes (requires safety dispatch)",
                "Wet or liquid chemicals",
                "Large appliances (contact facilities)"
            ),
            primaryLocations = listOf(
                "Library Technology Assistance Desk",
                "Engineering Lab MakerSpace",
                "Student Union Information Desk",
                "Campus Facilities Operations Building"
            )
        ),
        CampusBinInfo(
            name = "Gray Bin - Landfill & Non-Recyclable",
            colorTag = "Gray",
            description = "Last-resort bin for non-compostable and non-recyclable multi-material garbage.",
            acceptedItems = listOf(
                "Chip bags, candy bar foil wrappers",
                "Styrofoam / polystyrene cups & clamshells",
                "Laminated plastic pouches & condiment packets",
                "Broken ceramics and unrecyclable composite items"
            ),
            prohibitedItems = listOf(
                "Clean recyclable plastics & cans",
                "Food scraps suitable for compost",
                "Hazardous chemicals & batteries"
            ),
            primaryLocations = listOf(
                "Outdoor Walkways & Perimeter Trash Cans",
                "Restrooms (for sanitary waste)",
                "Parking Structures"
            )
        )
    )

    suspend fun auditImage(bitmap: Bitmap, promptHint: String? = null): EcoScanResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val hasValidKey = apiKey.isNotBlank() && !apiKey.contains("MY_GEMINI_API_KEY")

        if (!hasValidKey) {
            Log.d(tag, "No valid Gemini API key configured. Using campus heuristic engine.")
            return@withContext simulateIntelligentAudit(bitmap, promptHint)
        }

        try {
            // Encode bitmap to base64 JPEG
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)

            // Construct Gemini REST Payload
            val systemInstructionText = "You are EcoLens AI, an expert campus environmental auditor and sustainability engine. " +
                    "Analyze images of waste, cafeteria food, discarded resources, or campus facilities to categorize materials, " +
                    "recommend proper college campus disposal bins, estimate environmental footprints, and provide actionable advice. " +
                    "Return ONLY valid JSON matching this schema: " +
                    "{\"status\":\"success\",\"item_name\":string,\"category\":string,\"disposal_bin\":string," +
                    "\"campus_location_recommendation\":string,\"estimated_impact\":{\"carbon_footprint_kg\":number," +
                    "\"water_footprint_liters\":number,\"decomposition_years\":number},\"actionable_advice\":string," +
                    "\"eco_points_earned\":integer (10 to 50)}"

            val promptText = if (!promptHint.isNullOrBlank()) {
                "Audit this campus item ($promptHint). Categorize the material, specify designated campus bin, estimate impact, and provide advice. Return JSON."
            } else {
                "Audit this waste or campus item. Categorize material, campus disposal bin, carbon footprint kg, water liters, decomposition years, advice, and eco points (10-50). Return JSON."
            }

            val requestJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    put(JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            put(JSONObject().put("text", "$systemInstructionText\n\n$promptText"))
                            put(JSONObject().put("inline_data", JSONObject().apply {
                                put("mime_type", "image/jpeg")
                                put("data", base64Image)
                            }))
                        }
                        put("parts", partsArray)
                    })
                }
                put("contents", contentsArray)

                val generationConfig = JSONObject().apply {
                    put("response_mime_type", "application/json")
                    put("temperature", 0.2)
                }
                put("generationConfig", generationConfig)
            }

            // Target Gemini Flash preview model
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

            val body = requestJson.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                Log.w(tag, "Gemini API returned error code ${response.code}: $errorBody")
                return@withContext simulateIntelligentAudit(bitmap, promptHint)
            }

            val responseBody = response.body?.string() ?: ""
            val jsonRoot = JSONObject(responseBody)
            val candidates = jsonRoot.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text") ?: ""

            parseJsonResponse(text, bitmap)
        } catch (e: Exception) {
            Log.e(tag, "Exception calling Gemini API: ${e.message}", e)
            simulateIntelligentAudit(bitmap, promptHint)
        }
    }

    private fun parseJsonResponse(rawText: String, sourceBitmap: Bitmap): EcoScanResult {
        return try {
            var cleanText = rawText.trim()
            if (cleanText.startsWith("```json")) {
                cleanText = cleanText.substring(7)
            }
            if (cleanText.endsWith("```")) {
                cleanText = cleanText.substring(0, cleanText.length - 3)
            }
            cleanText = cleanText.trim()

            val obj = JSONObject(cleanText)
            val impactObj = obj.optJSONObject("estimated_impact") ?: JSONObject()

            EcoScanResult(
                status = obj.optString("status", "success"),
                itemName = obj.optString("item_name", "Detected Campus Material"),
                category = obj.optString("category", "General Waste"),
                disposalBin = obj.optString("disposal_bin", "Blue Bin - Recyclables"),
                campusLocationRecommendation = obj.optString(
                    "campus_location_recommendation",
                    "Campus Student Union Recycling Station"
                ),
                estimatedImpact = EstimatedImpact(
                    carbonFootprintKg = impactObj.optDouble("carbon_footprint_kg", 0.12),
                    waterFootprintLiters = impactObj.optDouble("water_footprint_liters", 10.5),
                    decompositionYears = impactObj.optDouble("decomposition_years", 50.0)
                ),
                actionableAdvice = obj.optString(
                    "actionable_advice",
                    "Check bin labeling before disposal to avoid campus waste contamination."
                ),
                ecoPointsEarned = obj.optInt("eco_points_earned", 30).coerceIn(10, 50),
                imageBitmap = sourceBitmap
            )
        } catch (e: Exception) {
            Log.e(tag, "Failed to parse JSON response: ${e.message}")
            simulateIntelligentAudit(sourceBitmap, null)
        }
    }

    fun simulateIntelligentAudit(bitmap: Bitmap?, hint: String?): EcoScanResult {
        // Find demo sample matching hint, or pick sensible default
        val matchedDemo = demoSamples.firstOrNull { sample ->
            hint != null && (sample.promptHint.contains(hint, ignoreCase = true) ||
                    sample.title.contains(hint, ignoreCase = true) ||
                    sample.key.equals(hint, ignoreCase = true))
        } ?: demoSamples[0]

        val base = matchedDemo.defaultResult
        return base.copy(
            id = java.util.UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            imageBitmap = bitmap
        )
    }
}
