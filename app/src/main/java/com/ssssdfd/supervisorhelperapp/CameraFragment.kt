package com.ssssdfd.supervisorhelperapp

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import com.ssssdfd.supervisorhelperapp.databinding.FragmentCameraBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraFragment : Fragment(R.layout.fragment_camera) {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var preview: Preview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)
        // Set up camera permission request
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permission granted, start camera
                    startCamera()
                } else {
                    // Permission denied, show an error message or handle it in some other way
                }
            }
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }else{
            startCamera()
        }
    }

    private fun startCamera() {
        // Initialize camera executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Initialize Preview use case and set up camera provider
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder().build()

            // Select back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Set up camera provider with preview use case and default camera
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
            )

            // Bind the preview use case to the camera provider
            preview.setSurfaceProvider(object : Preview.SurfaceProvider {
                override fun onSurfaceRequested(requestedSurface: SurfaceRequest) {
                    requestedSurface.provideSurface(Surface(binding.cfViewFinder.surfaceTexture), Executors.newSingleThreadExecutor(),
                        Consumer { result ->
                            Log.d("TAAG", "provideSurface result: ${result.resultCode}")
                        })
                }
            })

        }, ContextCompat.getMainExecutor(requireContext()))
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}