# About this demo

This app demo has a propose to demonstrate how to implement the oauth authentication in Android apps.


**IMPORTANT:**

`Chrome Custom Tabs` has no method to close itself, and this implementation is based on redirecting to previous Activity, by Intent flags, finishing the called Activity with registered scheme.


## Introduction

These are some example how to implement a sign-in on OAuth flow to use Trustly JavaScript SDK.
The code is using Kotlin language implementation with Jetpack Compose.

### CustomWebViewClient implementation

Using `WebViewClient` you'll need to add a configuration in the `settings` property.
Set the `javaScriptCanOpenWindowsAutomatically` property to true in order to enable the application to properly handle `window.open` events.

```kotlin
    webView.apply {
        ...
        webViewClient = CustomWebViewClient()
        settings.apply {
            javaScriptCanOpenWindowsAutomatically = true
        }
    }
```

Using `WebViewClient` you can override many methods, but you need to implement the `shouldOverrideUrlLoading` method. This method determines what will happen when a URL is loaded in WebView.
The example below is a simple implementation that calls the method which opens the CustomTabs.

```kotlin
class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        if (url.contains(TrustlyConstants.OAUTH_LOGIN_PATH))
            launchUrl(view.context, url)
        return true
    }
}
```

### CustomTabsIntent

It is a simple custom view with a WebView inside, to open the transported url.
In your custom web view you need to create a CustomTabIntent to open the url:

```kotlin
    private fun launchUrl(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
```

### JetpackComposeWebViewClientRedirectActivity

When the application receive some action for example `jetpack-compose-web-view-client-redirect`, or the name that you defined in `urlScheme`, it will call your target Activity with some flags, and reload it.
The example below is from `JetpackComposeWebViewClientRedirectActivity`

```kotlin
    Intent(this, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }.run { startActivity(this) }
    finish()
```

### AndroidManifest

```xml
    <activity
    android:name=".JetpackComposeWebViewClientRedirectActivity"
    android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="jetpack-compose-web-view-client-redirect" />
        </intent-filter>
    </activity>
```

### ProceedToChooseAccount

Finally, in order to support a smooth user experience when an OAuth login authorization is completed and the user returns to the Lightbox, call this function using some code like this:

```kotlin
    webView.loadUrl("javascript:window.Trustly.proceedToChooseAccount();")
```