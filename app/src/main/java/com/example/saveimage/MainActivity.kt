package com.example.saveimage

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.net.toUri
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
        val root =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "Apte4ka")
        if(!root.exists()){
            root.mkdirs()
        }
        val fname = "img_" + System.currentTimeMillis() + ".jpg"
        val sdImageMainDirectory = File(root, fname)
        uriImg = FileProvider.getUriForFile(
            this,
            "com.example.saveimage",
            sdImageMainDirectory
        )
        takePicture.launch(uriImg)
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            success: Boolean ->
        if (success) {
            // The image was saved into the given Uri -> do something with it
            Picasso.get().load(uriImg).resize(200,200).into(bind.ivPicture)
        }
    }
}