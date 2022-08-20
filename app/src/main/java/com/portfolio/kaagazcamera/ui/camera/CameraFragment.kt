package com.portfolio.kaagazcamera.ui.camera

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfolio.kaagazcamera.R
import com.portfolio.kaagazcamera.databinding.FragmentCameraBinding
import com.portfolio.kaagazcamera.domain.model.Image
import com.portfolio.kaagazcamera.ui.base.FragmentBase
import com.portfolio.kaagazcamera.ui.base.Success
import com.portfolio.kaagazcamera.ui.image.ImageThumbnailAdapter
import com.portfolio.kaagazcamera.ui.image.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


@AndroidEntryPoint
class CameraFragment : FragmentBase(R.layout.fragment_camera) {

    @Inject
    lateinit var imageViewModel: ImageViewModel

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var imageThumbnailAdapter: ImageThumbnailAdapter

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var imageCapture: ImageCapture? = null

    private lateinit var albumName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewBinding(view)
        setAdapter()
        setObservers()
        setEventHandlers()
        checkPermissionAndStartCamera()
        albumName = UUID.randomUUID().toString().substring(0, 6)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setViewBinding(view: View) {
        _binding = FragmentCameraBinding.bind(view)
    }

    private fun setAdapter() {
        imageThumbnailAdapter = ImageThumbnailAdapter(::onImageThumbnailClick)
        binding.imageThumbnailRecyclerView.adapter = imageThumbnailAdapter
        binding.imageThumbnailRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setObservers() {
        imageViewModel.viewState.observe { viewState ->
            when (viewState) {
                is Success -> {
                    imageThumbnailAdapter.submitList(viewState.data)
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun setEventHandlers() {
        binding.takePhoto.setOnClickListener { takePhoto() }
    }

    private fun checkPermissionAndStartCamera() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cameraExecutor.shutdown()
    }

    private fun onImageThumbnailClick(image: Image) {}

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/$albumName"
                )
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val image = Image(
                        fileName = name,
                        album = albumName,
                        uri = output.savedUri?.toString() ?: ""
                    )
                    imageViewModel.saveImage(image)
                    imageViewModel.getImages(albumName)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        }
    }

    companion object {
        private const val TAG = "Kaagaz Camera"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}


