package net.trustly.inappbrowserandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val widgetButton = findViewById<Button>(R.id.widgetButton)
        widgetButton.setOnClickListener {
            startActivity(InAppBrowserActivity.startIntent(this))
        }

        val lightboxButton = findViewById<Button>(R.id.lightboxButton)
        lightboxButton.setOnClickListener {
            startActivity(InAppBrowserActivity.startIntent(this, true))
        }
    }

    override fun onResume() {
        super.onResume()

        if (intent.hasExtra(ESTABLISH_DATA)) {
            val establishDataValues =
                intent.getSerializableExtra(ESTABLISH_DATA) as Map<String, String>
            if (establishDataValues.contains(STATUS_PARAM)) {
                val status = establishDataValues[STATUS_PARAM].equals(STATUS_SUCCESS)
                if (status) showDialogResult(true)
                else showDialogResult(false)
            }
        }
    }

    private fun showDialogResult(result: Boolean) {
        val message = if (result) "Authorization successful!" else "Authorization failed!"
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message).setTitle("Authorization")
            .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    companion object {

        const val ESTABLISH_DATA = "establishData"
        const val STATUS_PARAM = "status"
        const val STATUS_SUCCESS = "2"

    }

}