package com.estexis.core.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

fun compressImage(context: Context, uri: Uri, maxDimension: Int = 1024, quality: Int = 80): ByteArray? = try {
    val inputStream = if (uri.scheme == "file") {
        FileInputStream(File(uri.path!!))
    } else {
        context.contentResolver.openInputStream(uri)
    }
    val bitmap = inputStream?.use { BitmapFactory.decodeStream(it) } ?: return null

    val scaled = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
        val scale = maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height)
        Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * scale).toInt(),
            (bitmap.height * scale).toInt(),
            true,
        )
    } else bitmap

    val out = ByteArrayOutputStream()
    scaled.compress(Bitmap.CompressFormat.JPEG, quality, out)
    out.toByteArray()
} catch (_: Exception) {
    null
}
