package com.spyguard.security.core.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class IntruderCameraEngine @Inject constructor(
    private val context: Context
) {

    private companion object {
        private const val TAG = "IntruderCameraEngine"
    }

    suspend fun captureIntruderPhoto(lifecycleOwner: LifecycleOwner): String? = suspendCoroutine { continuation ->
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                if (!cameraProvider.hasCamera(cameraSelector)) {
                    Log.e(TAG, "No front camera available")
                    continuation.resume(null)
                    return@addListener
                }

                val imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture)

                val intruderDir = File(context.filesDir, "intruders").apply {
                    if (!exists()) mkdirs()
                }

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val photoFile = File(intruderDir, "INTRUDER_$timeStamp.jpg")

                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            Log.d(TAG, "Intruder photo saved successfully: ${photoFile.absolutePath}")
                            cameraProvider.unbindAll()
                            continuation.resume(photoFile.absolutePath)
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                            cameraProvider.unbindAll()
                            continuation.resume(null)
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing camera provider: ${e.message}", e)
                continuation.resume(null)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}
