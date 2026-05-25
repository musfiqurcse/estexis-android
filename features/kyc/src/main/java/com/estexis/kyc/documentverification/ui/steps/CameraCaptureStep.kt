package com.estexis.kyc.documentverification.ui.steps

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.kyc.R
import java.io.File
import androidx.compose.ui.tooling.preview.Preview as ComposePreview

@Composable
fun CameraCaptureStep(
    captureController: CaptureController,
    onPhotoTaken: (Uri) -> Unit,
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
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    val imageCapture = remember { ImageCapture.Builder().build() }

    DisposableEffect(lifecycleOwner, hasCameraPermission) {
        if (!hasCameraPermission) return@DisposableEffect onDispose {}

        var cameraProvider: ProcessCameraProvider? = null
        val future = ProcessCameraProvider.getInstance(context)

        future.addListener({
            cameraProvider = future.get()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture,
            )
        }, ContextCompat.getMainExecutor(context))

        onDispose { cameraProvider?.unbindAll() }
    }

    SideEffect {
        captureController.onCapture = {
            val file = File(context.cacheDir, "kyc_doc_${System.currentTimeMillis()}.jpg")
            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        onPhotoTaken(output.savedUri ?: Uri.fromFile(file))
                    }

                    override fun onError(exception: ImageCaptureException) {
                    }
                },
            )
        }
    }

    val frameWidthFraction = 0.80f
    val frameAspectRatio = 1.4f
    val borderColor = colors.secondary
    val borderWidth = dimensions.borders.medium
    val cornerRadius = dimensions.radius.large

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.document_verification_camera_help),
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalSpacer(dimensions.spaces.x6)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(cornerRadius)),
        ) {
            if (hasCameraPermission) {
                AndroidView(
                    factory = { previewView },
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2A1F18)),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen },
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val frameWidth = size.width * frameWidthFraction
                    val frameHeight = frameWidth / frameAspectRatio
                    val frameLeft = (size.width - frameWidth) / 2f
                    val frameTop = (size.height - frameHeight) / 2f
                    val frameOffset = Offset(frameLeft, frameTop)
                    val frameSize = Size(frameWidth, frameHeight)
                    val frameCorner = CornerRadius(cornerRadius.toPx())

                    drawRect(Color.Black.copy(alpha = 0.55f))

                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = frameOffset,
                        size = frameSize,
                        cornerRadius = frameCorner,
                        blendMode = BlendMode.Clear,
                    )

                    drawRoundRect(
                        color = borderColor,
                        topLeft = frameOffset,
                        size = frameSize,
                        cornerRadius = frameCorner,
                        style = Stroke(width = borderWidth.toPx()),
                    )
                }
            }
        }
    }
}

class CaptureController {
    internal var onCapture: (() -> Unit)? = null

    fun capture() {
        onCapture?.invoke()
    }
}

@Composable
fun rememberCaptureController(): CaptureController = remember { CaptureController() }

@ComposePreview(showBackground = true)
@Composable
private fun CameraCaptureStepPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CameraCaptureStep(
                captureController = CaptureController(),
                onPhotoTaken = {},
            )
        }
    }
}
