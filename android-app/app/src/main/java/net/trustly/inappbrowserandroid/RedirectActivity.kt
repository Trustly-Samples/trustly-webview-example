package net.trustly.inappbrowserandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.extras != null && intent.data!!.getQueryParameter(STATUS_PARAM) != null) {
            val transactionDetail = getTransactionDetailFromUri(intent.data!!)
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }.putExtra(MainActivity.ESTABLISH_DATA, transactionDetail as Serializable)
                .run { startActivity(this) }
        }
        finish()
    }

    private fun getTransactionDetailFromUri(appLinkData: Uri): Map<String, String> {
        return mapOf(
            Pair(TRANSACTION_ID_PARAM, appLinkData.getQueryParameter(TRANSACTION_ID_PARAM)!!),
            Pair(TRANSACTION_TYPE_PARAM, appLinkData.getQueryParameter(TRANSACTION_TYPE_PARAM)!!),
            Pair(PANEL_PARAM, appLinkData.getQueryParameter(PANEL_PARAM)!!),
            Pair(PAYMENT_TYPE_PARAM, appLinkData.getQueryParameter(PAYMENT_TYPE_PARAM)!!),
            Pair(STATUS_PARAM, appLinkData.getQueryParameter(STATUS_PARAM)!!)
        )
    }

    companion object {
        const val TRANSACTION_ID_PARAM = "transactionId"
        const val TRANSACTION_TYPE_PARAM = "transactionType"
        const val PANEL_PARAM = "panel"
        const val PAYMENT_TYPE_PARAM = "payment.paymentType"
        const val STATUS_PARAM = "status"
    }

}