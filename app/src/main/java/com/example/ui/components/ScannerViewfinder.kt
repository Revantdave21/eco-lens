package com.example.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.model.CampusDemoSample
import com.example.ui.theme.BorderSubtle
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

@Composable
fun ScannerViewfinder(
    isScanning: Boolean,
    scanningStatusText: String,
    demoSamples: List<CampusDemoSample>,
    onBitmapCaptured: (Bitmap) -> Unit,
    onSampleSelected: (CampusDemoSample) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Zero-permission Photo Picker (100% stable across all devices)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.isMutableRequired = true
                    }
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
                onBitmapCaptured(bitmap)
            } catch (e: Exception) {
                Toast.makeText(context, "Could not load selected image: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Safe Camera Launcher with try/catch
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            onBitmapCaptured(bitmap)
        } else {
            // Camera was cancelled or returned empty; notify user and allow demo selection
            Toast.makeText(context, "No photo captured. Tap any sample below to test audit.", Toast.LENGTH_SHORT).show()
        }
    }

    // Runtime Permission Launcher (Prevents SecurityException force-close on real phones)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                cameraLauncher.launch()
            } catch (e: Exception) {
                Toast.makeText(context, "Camera hardware unavailable. You can upload photos or select sample items below.", Toast.LENGTH_LONG).show()
                demoSamples.firstOrNull()?.let { onSampleSelected(it) }
            }
        } else {
            Toast.makeText(
                context,
                "Camera permission was denied. You can use 'Upload Image' or tap any demo item below.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun safeLaunchCamera() {
        val permissionState = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            try {
                cameraLauncher.launch()
            } catch (e: Exception) {
                Toast.makeText(context, "Device camera app failed to launch: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                demoSamples.firstOrNull()?.let { onSampleSelected(it) }
            }
        } else {
            // Request runtime permission first
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Minimalist Scanner Viewport with Orange Reticle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.18f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF1E2024))
                .border(1.5.dp, OrangeBorder.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                .clickable(enabled = !isScanning) {
                    // Tapping the viewport directly triggers a quick audit on the first demo item
                    demoSamples.firstOrNull()?.let { onSampleSelected(it) }
                }
                .testTag("scanner_viewport"),
            contentAlignment = Alignment.Center
        ) {
            ReticleOverlay(isScanning = isScanning)

            if (isScanning) {
                ScanningLaserAnimation()
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                if (isScanning) {
                    CircularProgressIndicator(
                        color = OrangePrimary,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = scanningStatusText,
                        color = PureWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        fontFamily = PlusJakartaFontFamily
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "EcoLens Vision Engine • Gemini 1.5 Flash",
                        color = OrangeSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = OutfitFontFamily
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = PureWhite.copy(alpha = 0.12f),
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Scan Target",
                                tint = OrangePrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Position Waste or Resource in Frame",
                        color = PureWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontFamily = OutfitFontFamily
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tap here or use buttons below to capture & audit materials",
                        color = Color(0xFFD1D5DB),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp,
                        fontFamily = PlusJakartaFontFamily
                    )
                }
            }

            // Campus Badge in upper right
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(OrangePrimary, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SVVV CAMPUS AI",
                        color = PureWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = OutfitFontFamily,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trigger Buttons: Safe Camera Launcher & Photo Picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { safeLaunchCamera() },
                enabled = !isScanning,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = PureWhite
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("snap_photo_button")
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Take Photo", fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = OutfitFontFamily)
            }

            OutlinedButton(
                onClick = {
                    try {
                        photoPickerLauncher.launch(
                            androidx.activity.result.PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } catch (e: Exception) {
                        Toast.makeText(context, "Could not open photo gallery", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isScanning,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = OrangePrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("upload_photo_button")
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = OrangePrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload Image", fontSize = 13.sp, color = OrangePrimary, fontWeight = FontWeight.Bold, fontFamily = OutfitFontFamily)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Campus Samples Row (Pure White Cards with Orange Accents)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.TouchApp,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Instant Demo Audits",
                    fontSize = 14.sp,
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
                    text = "1-Tap Scan",
                    fontSize = 10.sp,
                    color = OrangePrimaryDark,
                    fontWeight = FontWeight.Bold,
                    fontFamily = OutfitFontFamily,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(demoSamples) { sample ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .width(135.dp)
                        .clickable(enabled = !isScanning, onClick = { onSampleSelected(sample) })
                        .testTag("sample_item_${sample.key}")
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(OrangeContainerLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = sample.emoji, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = sample.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = OutfitFontFamily
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = sample.category,
                            fontSize = 10.sp,
                            color = TextSecondaryGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = PlusJakartaFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReticleOverlay(isScanning: Boolean) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 3.dp.toPx()
        val cornerLength = 28.dp.toPx()
        val padding = 20.dp.toPx()
        val reticleColor = if (isScanning) Color(0xFFFF5C00) else Color(0x66FFFFFF)

        // Top-Left
        drawLine(reticleColor, Offset(padding, padding), Offset(padding + cornerLength, padding), strokeWidth, StrokeCap.Round)
        drawLine(reticleColor, Offset(padding, padding), Offset(padding, padding + cornerLength), strokeWidth, StrokeCap.Round)

        // Top-Right
        drawLine(reticleColor, Offset(size.width - padding, padding), Offset(size.width - padding - cornerLength, padding), strokeWidth, StrokeCap.Round)
        drawLine(reticleColor, Offset(size.width - padding, padding), Offset(size.width - padding, padding + cornerLength), strokeWidth, StrokeCap.Round)

        // Bottom-Left
        drawLine(reticleColor, Offset(padding, size.height - padding), Offset(padding + cornerLength, size.height - padding), strokeWidth, StrokeCap.Round)
        drawLine(reticleColor, Offset(padding, size.height - padding), Offset(padding, size.height - padding - cornerLength), strokeWidth, StrokeCap.Round)

        // Bottom-Right
        drawLine(reticleColor, Offset(size.width - padding, size.height - padding), Offset(size.width - padding - cornerLength, size.height - padding), strokeWidth, StrokeCap.Round)
        drawLine(reticleColor, Offset(size.width - padding, size.height - padding), Offset(size.width - padding, size.height - padding - cornerLength), strokeWidth, StrokeCap.Round)
    }
}

@Composable
fun ScanningLaserAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "laser_anim")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_y"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val y = size.height * (0.15f + 0.7f * progress)
        drawLine(
            color = Color(0xFFFF5C00),
            start = Offset(24.dp.toPx(), y),
            end = Offset(size.width - 24.dp.toPx(), y),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}
