package net.trustly.inappbrowserandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val widgetButton = findViewById<Button>(R.id.widgetButton)
        widgetButton.setOnClickListener {
            startActivity(InAppBrowserActivity.startIntent(this, false))
        }

        val lightboxButton = findViewById<Button>(R.id.lightboxButton)
        lightboxButton.setOnClickListener {
            startActivity(InAppBrowserActivity.startIntent(this, true))
        }
    }

}