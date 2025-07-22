package net.trustly.inappbrowser.jetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class JetpackComposeWebViewClientRedirectActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }.run { startActivity(this) }
        finish()
    }

}