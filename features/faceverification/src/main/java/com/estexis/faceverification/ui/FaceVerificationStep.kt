package com.estexis.faceverification.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.estexis.core.ui.gds.HorizontalSpacer
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.faceverification.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

private enum class FacePose(
    @StringRes val instructionRes: Int,
    @DrawableRes val drawableRes: Int,
) {
    LEFT(R.string.face_verification_turn_left, R.drawable.ic_face_left),
    RIGHT(R.string.face_verification_turn_right, R.drawable.ic_face_right),
    STRAIGHT(R.string.face_verification_look_straight, R.drawable.ic_face_straight),
}

private val POSE_SEQUENCE = listOf(FacePose.LEFT, FacePose.RIGHT, FacePose.STRAIGHT)

private fun isPoseDetected(eulerY: Float, eulerX: Float, pose: FacePose): Boolean = when (pose) {
    FacePose.LEFT -> eulerY > 25f
    FacePose.RIGHT -> eulerY < -25f
    FacePose.STRAIGHT -> abs(eulerY) < 15f && abs(eulerX) < 15f
}

@Composable
fun FaceVerificationStep(
    uiState: FaceVerificationUiState,
    event: (FaceVerificationUiEvent) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted -> hasCameraPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    var currentPoseIndex by remember { mutableIntStateOf(0) }
    var isCapturing by remember { mutableStateOf(false) }
    val isCompleted = uiState.isCompleted

    LaunchedEffect(currentPoseIndex) {
        isCapturing = false
    }

    val arcFraction by animateFloatAsState(
        targetValue = uiState.capturedPhotos.size / POSE_SEQUENCE.size.toFloat(),
        animationSpec = tween(500),
        label = "arc_progress",
    )

    val currentPose = POSE_SEQUENCE.getOrNull(currentPoseIndex)

    val capturedFaceUri = uiState.latestPhotoUri

    val smallPreviewView = remember {
        PreviewView(context).apply { scaleType = PreviewView.ScaleType.FILL_CENTER }
    }

    val imageCapture = remember { ImageCapture.Builder().build() }

    val faceDetector = remember {
        FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                .build()
        )
    }

    DisposableEffect(Unit) {
        onDispose { faceDetector.close() }
    }

    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    DisposableEffect(lifecycleOwner, hasCameraPermission) {
        if (!hasCameraPermission) return@DisposableEffect onDispose {}

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage == null) {
                imageProxy.close()
                return@setAnalyzer
            }
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            faceDetector.process(image)
                .addOnSuccessListener { faces ->
                    if (!isCapturing && !isCompleted) {
                        val pose = POSE_SEQUENCE.getOrNull(currentPoseIndex) ?: return@addOnSuccessListener
                        val face = faces.firstOrNull() ?: return@addOnSuccessListener
                        if (isPoseDetected(face.headEulerAngleY, face.headEulerAngleX, pose)) {
                            isCapturing = true
                            val fileName = "face_${pose.name.lowercase()}_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())}.jpg"
                            val file = File(context.cacheDir, fileName)
                            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                        val uri = output.savedUri ?: Uri.fromFile(file)
                                        event(FaceVerificationUiEvent.PhotoCaptured(uri))
                                        currentPoseIndex++
                                    }
                                    override fun onError(exc: ImageCaptureException) {
                                        currentPoseIndex++
                                    }
                                }
                            )
                        }
                    }
                }
                .addOnCompleteListener { imageProxy.close() }
        }

        var cameraProvider: ProcessCameraProvider? = null
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener({
            cameraProvider = future.get()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = smallPreviewView.surfaceProvider
            }
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_FRONT_CAMERA,
                preview,
                imageAnalysis,
                imageCapture,
            )
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            cameraProvider?.unbindAll()
            imageAnalysis.clearAnalyzer()
        }
    }

    val arcColor = colors.secondary
    val arcTrackColor = colors.secondary.copy(alpha = 0.15f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier.size(220.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEF1F7)),
                    contentAlignment = Alignment.Center,
                ) {
                    if (capturedFaceUri != null) {
                        AsyncImage(
                            model = capturedFaceUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else if (hasCameraPermission) {
                        AndroidView(
                            factory = { smallPreviewView },
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF2A1F18)),
                        )
                    }

                    if (!isCompleted && currentPose != null && capturedFaceUri == null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(alpha = 0.35f))
                                .padding(6.dp),
                        ) {
                            Image(
                                painter = painterResource(currentPose.drawableRes),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    }
                }

                Canvas(modifier = Modifier.size(220.dp)) {
                    val strokePx = 8.dp.toPx()
                    val inset = strokePx / 2f
                    val arcTopLeft = Offset(inset, inset)
                    val arcSize = Size(size.width - strokePx, size.height - strokePx)

                    drawArc(
                        color = arcTrackColor,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = arcTopLeft,
                        size = arcSize,
                        style = Stroke(width = strokePx, cap = StrokeCap.Round),
                    )

                    if (arcFraction > 0f) {
                        drawArc(
                            color = arcColor,
                            startAngle = -90f,
                            sweepAngle = arcFraction * 360f,
                            useCenter = false,
                            topLeft = arcTopLeft,
                            size = arcSize,
                            style = Stroke(width = strokePx, cap = StrokeCap.Round),
                        )
                    }
                }
            }
        }

        VerticalSpacer(dimensions.spaces.x4)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaces.x8),
            horizontalArrangement = Arrangement.spacedBy(dimensions.spaces.x2),
        ) {
            repeat(POSE_SEQUENCE.size) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if (index < uiState.capturedPhotos.size) colors.secondary
                            else colors.secondary.copy(alpha = 0.2f)
                        ),
                )
            }
        }

        VerticalSpacer(dimensions.spaces.x6)

        if (!isCompleted && currentPose != null) {
            Text(
                text = stringResource(currentPose.instructionRes),
                style = AppTextStyles.BodyText1Regular,
                color = colors.tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        AnimatedVisibility(
            visible = isCompleted,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        ) {
            Column {
                VerticalSpacer(dimensions.spaces.x4)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensions.radius.large))
                        .background(colors.onPrimary)
                        .padding(dimensions.spaces.x4),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = colors.secondary,
                        modifier = Modifier.size(dimensions.sizes.x6),
                    )
                    HorizontalSpacer(dimensions.spaces.x3)
                    Text(
                        text = stringResource(R.string.face_verification_captured),
                        style = AppTextStyles.BodyText2SemiBold,
                        color = colors.tertiary,
                    )
                }
            }
        }
    }
}
