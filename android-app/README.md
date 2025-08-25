# About this demo

This app demo has a propose to demonstrate how to implement the oauth authentication in Android apps.


**IMPORTANT:**

`Chrome Custom Tabs` has no method to close itself, and this implementation is based on redirecting to previous Activity, by Intent flags, finishing the called Activity with registered scheme.


## Introduction

These are some example how to implement a sign-in on OAuth flow to use Trustly JavaScript SDK.
The code is using Kotlin language implementation.

## WebClient

There are two ways to create web clients for a WebView, `WebViewClient` and `WebChromeClient`.

### WebViewClient implementation

Using `WebViewClient` you'll need to add a configuration in the `settings` property.
Set the `javaScriptCanOpenWindowsAutomatically` property to true in order to enable the application to properly handle `window.open` events.

```kotlin
    webView.settings.apply {
        javaScriptCanOpenWindowsAutomatically = true
    }
```

Using `WebViewClient` you can override many methods, but you need to implement the `shouldOverrideUrlLoading` method. This method determines what will happen when a URL is loaded in WebView.
The example below is a simple implementation that calls the method which opens the CustomTabs.

```kotlin
    webView.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            if (url.contains(TrustlyConstants.OAUTH_LOGIN_PATH))
                launchUrl(this@WebViewClientActivity, url)
            return true
        }
    }
```

### WebChromeClient implementation

Using `WebChromeClient` you'll need to create your own WebView, add some configuration in the `settings` property, and transport itself to a custom WebView.
The example below explain more about those implementation. First you need to add both `javaScriptCanOpenWindowsAutomatically` and `setSupportMultipleWindows(true)`, they are needed to listen the `window.open` method.

```kotlin
    webView.settings.apply {
        javaScriptCanOpenWindowsAutomatically = true
        setSupportMultipleWindows(true)
    }
```

This is the `WebChromeClient` implementation, using a custom WebView to transport the URL and than open it inside that.
The `trustlyWebView` is an instance of the custom WebView, which has the same implementation of a `WebViewClient`.

```kotlin
    webView.webChromeClient = object : WebChromeClient() {
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            return if (view.hitTestResult.type == 0) {
                //window.open
                webView.addView(trustlyWebView)
                val transport = resultMsg.obj as WebViewTransport
                transport.webView = trustlyWebView.webView
                resultMsg.sendToTarget()
                true
            } else false
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

### TrustlyWebChromeClientRedirectActivity and TrustlyWebViewClientRedirectActivity

When the application receive some action for example `web-chrome-client-redirect`, or the name that you defined in `urlScheme`, it will call your target Activity with some flags, and reload it.
The example below is from `TrustlyWebChromeClientRedirectActivity`

```kotlin
    Intent(this, WebChromeClientActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }.run { startActivity(this) }
    finish()
```

### AndroidManifest

```xml
    <activity
    android:name=".TrustlyWebChromeClientRedirectActivity"
    android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="web-chrome-client-redirect" />
        </intent-filter>
    </activity>
```

### ProceedToChooseAccount

Finally, in order to support a smooth user experience when an OAuth login authorization is completed and the user returns to the Lightbox, call this function using some code like this:

```kotlin
    webView.loadUrl("javascript:window.Trustly.proceedToChooseAccount();")
```