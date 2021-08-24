package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.myapplication2.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    var Image_request_code = 45

    private var currentPhotoPath: String? = null
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        supportActionBar!!.hide()
        binding!!.editBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, Image_request_code)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_request_code) {
            if (data!!.data != null) {
                val file_path = data.data
            }
            val intent = Intent(baseContext, EditorActivity::class.java)
            intent.putExtra("IMAGE_URI", data.data.toString())
            startActivity(intent)
        }
    }
}