package net.trustly.inappbrowserandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

class InAppBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openLightboxDirectly = intent.getBooleanExtra(OPEN_LIGHTBOX_DIRECTLY, false)
        val url =
            "https://sandbox.paywithmybank.com/frontend/mobile/establish?widget=$openLightboxDirectly&token=\\(establishBase64)"
        launchUrl(this, url)
    }

    private fun getEstablishDataValues(context: Context): MutableMap<String, String> {
        val establishDataValues: MutableMap<String, String> = HashMap()
        establishDataValues["accessId"] = "<ACCESS_ID>"
        establishDataValues["amount"] = "0.00"
        establishDataValues["merchantId"] = "<MERCHANT_ID>"
        establishDataValues["currency"] = "USD"
        establishDataValues["merchantReference"] = "<MERCHANT_REFERENCE>"
        establishDataValues["paymentType"] = "Retrieval"
        establishDataValues["env"] = "<[int, sandbox, local]>"
        establishDataValues["description"] = "Android InAppBrowser"
        establishDataValues["localUrl"] = "<YOUR LOCAL URL WHEN `ENV` PROPERTY IS `LOCAL` (ex: https://192.168.0.30)>"
        establishDataValues["customer.name"] = "John Smith Android"
        establishDataValues["customer.address.address1"] = "2000 Broadway Street"
        establishDataValues["customer.address.city"] = "Redwood City"
        establishDataValues["customer.address.state"] = "CA"
        establishDataValues["customer.address.zip"] = "94063"
        establishDataValues["customer.address.country"] = "US"
        establishDataValues["customer.phone"] = "2145553434"
        establishDataValues["customer.email"] = "jsmith@email.com"
        establishDataValues["metadata.urlScheme"] = context.getString(R.string.url_scheme) + "://"
        return establishDataValues
    }

    private fun launchUrl(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.launchUrl(context, url.toUri())
    }

    companion object {

        private const val OPEN_LIGHTBOX_DIRECTLY = "OPEN_LIGHTBOX_DIRECTLY"

        fun startIntent(context: Context, openLightboxDirectly: Boolean = false) =
            Intent(context, InAppBrowserActivity::class.java)
                .putExtra(OPEN_LIGHTBOX_DIRECTLY, openLightboxDirectly)

    }

}