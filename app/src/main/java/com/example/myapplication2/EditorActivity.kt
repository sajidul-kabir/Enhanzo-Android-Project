package com.example.myapplication2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class EditorActivity : AppCompatActivity() {
    /*private val cropActivityResultContract= object :ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity().setAspectRatio(16,9).getIntent(this@EditorActivity);
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        val image_uri = Uri.parse(intent.getStringExtra("IMAGE_URI"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        val crop = findViewById<Button>(R.id.crop)
        val img = findViewById<ImageView>(R.id.imageView2)
        supportActionBar!!.hide()
        img.setImageURI(image_uri)


        crop.setOnClickListener{
            CropImage.activity(image_uri)
                .start(this);
        }

/*
        cropActivityResultLauncher= registerForActivityResult(cropActivityResultContract){
            it?.let { uri->img.setImageURI(uri)

            }
        }
        btn.setOnClickListener{
            cropActivityResultLauncher.launch(null)
        }
    }
*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            val resultUri = result.uri
            val img = findViewById<ImageView>(R.id.imageView2)
            img.setImageURI(resultUri)
            //R.id.imageView2.setImageURI(result.uri)
        }
    }
}