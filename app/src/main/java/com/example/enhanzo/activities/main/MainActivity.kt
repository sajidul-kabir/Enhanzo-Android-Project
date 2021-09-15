package com.example.enhanzo.activities.main

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.enhanzo.activities.editimage.EditImageActivity
import com.example.enhanzo.activities.savedimages.SavedImagesActivity
import com.example.enhanzo.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.File
class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
        const val KEY_IMAGE_URI = "imageUri"
    }

    private lateinit var binding: ActivityMainBinding
    private var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {

        binding.buttonCamera.setOnClickListener {
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).also { pickerIntent ->
                pickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(pickerIntent, REQUEST_CODE_PICK_IMAGE)
            }
        }
        binding.buttonViewSavedImages.setOnClickListener {
            Intent(applicationContext, SavedImagesActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.camera.setOnClickListener {

            val takePictureIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)


            if(takePictureIntent.resolveActivity(this.packageManager)!=null){
                startActivityForResult(takePictureIntent,32)
            }
            else{
                Toast.makeText(this,"unable to open camera",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getPhotoFile(fileName: String): File {

        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { imageUri ->
                Intent(applicationContext, EditImageActivity::class.java).also { editImageIntent ->
                    editImageIntent.putExtra(KEY_IMAGE_URI, imageUri)
                    startActivity(editImageIntent)
                }
            }
        }
        else if(requestCode == 32 && resultCode == RESULT_OK){
            //    val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = data?.extras?.get("data") as Bitmap
            val uri = getImageUri(takenImage)


            //Toast.makeText(this,"uri is"+uri,Toast.LENGTH_SHORT).show()

            Intent(applicationContext,EditImageActivity::class.java).also { cameraIntent->
                cameraIntent.putExtra(KEY_IMAGE_URI,uri)
                startActivity(cameraIntent)
            }

        }

    }


    fun getImageUri(bitmap:Bitmap): Uri? {
        val arrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayOutputStream)
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
        return Uri.parse(path)

    }
}