package net.trustly.inappbrowserandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InAppBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openLightboxDirectly = intent.getBooleanExtra(OPEN_LIGHTBOX_DIRECTLY, false)
        Toast.makeText(this, "Open Lightbox: $openLightboxDirectly", Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val OPEN_LIGHTBOX_DIRECTLY = "OPEN_LIGHTBOX_DIRECTLY"

        fun startIntent(context: Context, openLightboxDirectly: Boolean = false) =
            Intent(context, InAppBrowserActivity::class.java)
                .putExtra(OPEN_LIGHTBOX_DIRECTLY, openLightboxDirectly)

    }

}