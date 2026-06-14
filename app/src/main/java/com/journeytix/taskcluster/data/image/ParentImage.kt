package com.journeytix.taskcluster.data.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.min

/* A parent's "emoji" slot can hold either a unicode emoji or a custom image. An
   image is stored as "img:<absolute path>" in the same field, so no schema change
   is needed. Images are resized and saved as PNG to keep transparency intact. */
object ParentImage {

    private const val PREFIX = "img:"
    private const val MAX_DIM = 256
    private const val DIR = "parent_images"

    fun isImage(value: String?): Boolean = value?.startsWith(PREFIX) == true

    fun pathOf(value: String): String = value.removePrefix(PREFIX)

    private fun valueFor(path: String): String = PREFIX + path

    /** Picks the file at [uri], resizes it, saves a PNG copy, and returns the
        "img:<path>" value to store. Returns null on any failure. */
    suspend fun importAndCompress(context: Context, uri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val original = context.contentResolver.openInputStream(uri).use { input ->
                BitmapFactory.decodeStream(input)
            } ?: return@withContext null

            val longest = max(original.width, original.height)
            val scale = min(1f, MAX_DIM.toFloat() / longest)
            val w = (original.width * scale).toInt().coerceAtLeast(1)
            val h = (original.height * scale).toInt().coerceAtLeast(1)
            val scaled = if (scale < 1f) Bitmap.createScaledBitmap(original, w, h, true) else original

            val dir = File(context.filesDir, DIR).apply { mkdirs() }
            val file = File(dir, "p_${System.currentTimeMillis()}.png")
            FileOutputStream(file).use { out ->
                // PNG is lossless and keeps the alpha channel (transparent PNGs work).
                scaled.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            if (scaled !== original) scaled.recycle()
            original.recycle()
            valueFor(file.absolutePath)
        } catch (e: Exception) {
            null
        }
    }

    /** Deletes the backing file if [value] is an image reference. No-op otherwise. */
    fun deleteIfImage(value: String?) {
        if (!isImage(value)) return
        runCatching { File(pathOf(value!!)).delete() }
    }
}
