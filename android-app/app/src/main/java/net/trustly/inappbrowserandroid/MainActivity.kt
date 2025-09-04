package net.trustly.inappbrowserandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webViewClientButton = findViewById<Button>(R.id.widgetButton)
        webViewClientButton.setOnClickListener {
            //TODO Open widget
        }

        val webViewChromeButton = findViewById<Button>(R.id.lightboxButton)
        webViewChromeButton.setOnClickListener {
            //TODO Open lightbox
        }
    }

}