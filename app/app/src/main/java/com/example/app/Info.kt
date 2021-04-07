package com.example.app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class Info : AppCompatActivity() {

    lateinit var detailImage: ImageView
    lateinit var downloadURL: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        detailImage = findViewById(R.id.det_image)
        downloadURL = findViewById(R.id.det_url)

        downloadURL.setText(intent.getStringExtra("TAGS"))
        Picasso.get().load(intent.getStringExtra("IMAGE")).into(detailImage)
    }
}