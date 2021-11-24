package com.example.imagefilters.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.imagefilters.data.ImageFilter
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

class EditImageRepositoryImpl(private val context: Context): EditImageRepository {


    override suspend fun getImageFilters(image: Bitmap): List<ImageFilter> {
        val gpuImage = GPUImage(context).apply {
            setImage(image)
        }
        val imageFilters: ArrayList<ImageFilter> = ArrayList()

        //region:: Image Filters
        // Normal
        GPUImageFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Normal",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // Retro
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.2f, 0.0f,
                0.1f, 0.1f, 1.0f, 0.0f,
                1.0f, 0.0f, 0.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Retro",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // 1
        GPUImageColorMatrixFilter(
            0.9f,
            floatArrayOf(
                0.4f, 0.6f, 0.5f, 0.0f,
                0.5f, 0.4f, 1.0f, 0.0f,
                0.5f, 10.1f, 0.4f, 0.4f,
                1.5f, 1.0f, 1.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Test",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // 2
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.25f, 0.0f, 0.2f, 0.0f,
                0.0f, 1.0f, 0.2f, 0.0f,
                0.0f, 0.3f, 1.0f, 0.3f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Test2",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        // 3
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0.6f, 0.4f, 0.2f, 0.05f,
                0.0f, 0.8f, 0.3f, 0.05f,
                0.3f, 0.3f, 0.5f, 0.08f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Test3",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }

        //4
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                1.0f, 0.05f, 0.0f, 0.0f,
                -0.2f, 1.1f, -0.2f, 0.11f,
                0.2f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Test4",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }



        // Sepia
        GPUImageSepiaToneFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "Sepia",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }


        // BW
        GPUImageColorMatrixFilter(
            1.0f,
            floatArrayOf(
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f
            )
        ).also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(
                ImageFilter(
                    name = "BW",
                    filter = filter,
                    filterPreview = gpuImage.bitmapWithFilterApplied
                )
            )
        }


        //endregion

        return imageFilters
    }

    override suspend fun prepareImagePreview(imageUri: Uri): Bitmap? {
        getInputStreamFromUri(imageUri)?.let { inputStream ->
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            val width = context.resources.displayMetrics.widthPixels
            val height = ((originalBitmap.height * width) / originalBitmap.width)
            return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
        } ?: return null
    }

    private fun getInputStreamFromUri(uri: Uri): InputStream? {
        return context.contentResolver.openInputStream(uri)
    }

    override suspend fun saveFilteredImage(filteredBitmap: Bitmap): Uri? {
        return try {
            val mediaStorageDirectory = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "Saved Images"
            )
            if (!mediaStorageDirectory.exists()) {
                mediaStorageDirectory.mkdirs()
            }
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val file = File(mediaStorageDirectory, fileName)
            saveFile(file, filteredBitmap)
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (exception: Exception) {
            null
        }
    }

    private fun saveFile(file: File, bitmap: Bitmap) {
        with(FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
    }
}