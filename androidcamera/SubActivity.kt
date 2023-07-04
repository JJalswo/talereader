
package com.example.androidcamera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class SubActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    private var bitmap: Bitmap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView2)

        val button2:Button = findViewById(R.id.button2)
        val button3:Button = findViewById(R.id.button3)

        dispatchTakePictureIntent()
        button3.setOnClickListener { view: View? -> detectText() }
        button2.setOnClickListener{
            val intent = Intent(this,SubActivity2::class.java)
            intent.putExtra("texts",textView.text)
            startActivity(intent)
        }


    }
    private fun detectText() {
        if (bitmap != null) {
            val image = InputImage.fromBitmap(bitmap!!, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val result = recognizer.process(image)
                .addOnSuccessListener { text ->
                    val resultBuilder = StringBuilder()
                    for (block in text.textBlocks) {
                        val blockText = block.text
                        val blockCornerPoints = block.cornerPoints
                        val blockFrame = block.boundingBox
                        for (line in block.lines) {
                            val lineText = line.text
                            val lineCornerPoints = line.cornerPoints
                            val lineRect = line.boundingBox
                            for (element in line.elements) {
                                val elementText = element.text
                                resultBuilder.append(elementText)
                            }
                            textView.text = blockText
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Failed to detect text from the image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(applicationContext, "No image available.", Toast.LENGTH_SHORT).show()
        }
    }




    @Throws(IOException::class)
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            bitmap = data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
            }
        }
    }

}
