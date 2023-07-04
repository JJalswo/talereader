package com.example.androidcamera

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class SubActivity2 : AppCompatActivity() {
    lateinit var apple : TextView
    lateinit var intents : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)
        intents= intent
        apple= findViewById(R.id.apple)
        apple.text = intents.getStringExtra("texts")
        var token = apple.text.split(' ')
        var indexs:Int = token.lastIndex
        var i:Int = 0
        while(i<=indexs){
            linkSetup(token[i])
            i += 1
        }


        //update with Textstream

    }



    private fun linkSetup(word:String) {
        val wordLink = Link(word)
            .setTextColor(Color.BLUE)
            .setOnClickListener {
                Toast.makeText(this, "Linked clicked" , Toast.LENGTH_SHORT).show()
                //examples
                var link :String = "https://en.dict.naver.com/#/search?query="
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.plus(word)))
                startActivity(intent)
            }
        apple.applyLinks(wordLink)




    }

}
