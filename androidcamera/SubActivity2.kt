package com.example.androidcamera

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class SubActivity2 : AppCompatActivity() {
    lateinit var apple : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)
        apple= findViewById(R.id.apple)
        linkSetup()



    }

    private fun linkSetup() {
        val wordLink = Link("Apple")
            .setTextColor(Color.BLUE)
            .setOnClickListener {
                Toast.makeText(this, "Linked clicked" , Toast.LENGTH_SHORT).show()
                //examples
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.dict.naver.com/#/search?query=apple"))
                startActivity(intent)
            }
        apple.applyLinks(wordLink)




    }
}