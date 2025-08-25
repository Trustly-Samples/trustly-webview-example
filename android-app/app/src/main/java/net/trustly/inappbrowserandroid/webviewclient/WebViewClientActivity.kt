package net.trustly.inappbrowserandroid.webviewclient

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import net.trustly.inappbrowserandroid.TrustlyConstants
import net.trustly.inappbrowserandroid.WebViewBaseActivity

class WebViewClientActivity : WebViewBaseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url = request.url.toString()
                if (url.contains(TrustlyConstants.OAUTH_LOGIN_PATH))
                    launchUrl(this@WebViewClientActivity, url)

                // This return depends on your context, visit the Android documentation to learn more about it.
                // https://developer.android.com/reference/android/webkit/WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView,%20java.lang.String)
                return true
            }
        }
        webView.loadUrl(TrustlyConstants.TRUSTLY_URL + "web-view-client-redirect")
    }

    override fun onResume() {
        super.onResume()

        webView.loadUrl(TrustlyConstants.TRUSTLY_PROCEED_TO_CHOOSE_ACCOUNT_SCRIPT)
    }

    private fun launchUrl(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.launchUrl(context, url.toUri())
    }

}