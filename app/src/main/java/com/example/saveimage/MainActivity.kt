package com.example.saveimage

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.saveimage.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private var uriImg : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bind.bAddPicture.setOnClickListener {
            takePicture()
        }
    }

    private fun takePicture() {
        val path = this.externalMediaDirs.first()
        val folder = File(path, "Apte4ka")
        folder.mkdirs()
        val fileName = "img_" + System.currentTimeMillis() + ".jpg"
        val sdImageDirectoryPath = File(folder, fileName)
        uriImg = FileProvider.getUriForFile(
            this,
            "com.example.saveimage",
            sdImageDirectoryPath
        )
        Log.i("SaveImage", uriImg.toString())
        takePicture.launch(uriImg)
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            success: Boolean ->
        if (success) {
            // The image was saved into the given Uri -> do something with it
            Picasso.get().load(uriImg).rotate(90F).into(bind.ivPicture)
        }
        Log.i("SaveImage", uriImg.toString())
    }
}