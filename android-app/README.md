# About this demo

This app demo has a propose to demonstrate how to implement the oauth authentication in Android apps.


**IMPORTANT:**

`Chrome Custom Tabs` has no method to close itself, and this implementation is based on redirecting to previous Activity, by Intent flags, finishing the called Activity with registered scheme.

## Introduction

These are some example how to implement a sign-in on OAuth flow to use Trustly JavaScript SDK.
The code is using Kotlin language implementation.

To open Trustly widget or lightbox we need to build an url with a `token`. This `token` is the `establish data` in `base 64` format.

### Building url
The base url is `https://sandbox.paywithmybank.com/frontend/mobile/establish`.

`Establish data` is a dictionary with all parameters to create a transaction, to transform `establish data` to `base 64` format we should convert the dicitionary in a `json string` and after that create the `base 64` value.

```kotlin
val establishData = JSONUtils.getJsonFromParameters(getEstablishDataValues(this))
val establishDataBase64 = JSONUtils.encodeStringToBase64(establishData)
```

The `JSONUtils` class, is a custom class created by Trustly, you can check the content in the file `JSONUtils.kt`.

In the url, you can send the `widget` parameter, to ask to open the `widget` if the `true`, or the lightbox, if `false`.

The complete url with `base 64` `token` and `widget` parameter as `true`.

```kotlin
https://sandbox.paywithmybank.com/frontend/mobile/establish?widget=true&token=eyJhY2Nlc3NJZCI6IkE0OEI3M0Y2OTRDNEM4RUU2MzA2IiwicGF5bWVudFR5cGUiOiJSZXRyaWV2YWwiLCJmbG93VHlwZSI6IiIsIm1ldGFkYXRhIjp7InNka0lPU1ZlcnNpb24iOiIzLjMuMCIsInVybFNjaGVtZSI6ImRlbW9hcHA6XC9cLyIsImNpZCI6IjEwRjYtNENFQy1NRVQwWERIUSIsInRoZW1lIjoiZGFyayJ9LCJzZXNzaW9uQ2lkIjoiMTBGNi00NDFBLU1FVDBCV0c0IiwicmV0dXJuVXJsIjoiZGVtb2FwcDpcL1wvIiwicmVxdWVzdFNpZ25hdHVyZSI6IkhUNW1WT3FCWGE4Wmx2Z1gyVVNtUGVMbnM1bz0iLCJ0aGVtZSI6ImRhcmsiLCJ2ZXJzaW9uIjoiMiIsImVudiI6InNhbmRib3giLCJjdXJyZW5jeSI6IlVTRCIsIm1lcmNoYW50SWQiOiIxMTAwMDU1MTQiLCJjYW5jZWxVcmwiOiJkZW1vYXBwOlwvXC8iLCJtZXJjaGFudFJlZmVyZW5jZSI6ImNhYzczZGY3LTUyYjQtNDdkNy04OWQzLTk2MjhkNGNmYjY1ZSIsImVudkhvc3QiOiIxOTIuMTY4LjAuMTMiLCJwYXltZW50UHJvdmlkZXJJZCI6IjIwMDAwNTUwMSIsImdycCI6IjE4Iiwid2lkZ2V0TG9hZGVkIjoidHJ1ZSIsImN1c3RvbWVyIjp7ImFkZHJlc3MiOnsiY291bnRyeSI6IlVTIn0sIm5hbWUiOiJKb2huIn0sImRldmljZVR5cGUiOiJtb2JpbGU6aW9zOm5hdGl2ZSIsImFtb3VudCI6IjEwLjAwIiwiZGVzY3JpcHRpb24iOiJGaXJzdCBEYXRhIE1vYmlsZSBUZXN0In0=
```

After built Trustly url, now, you can open widget or lightbox with oAuth component.

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

### RedirectActivity

When the application receive some action for example `in-app-browser-android`, or the name that you defined in `urlScheme`, it will call your target Activity with some flags, and reload it.
The example below is from `RedirectActivity`

```kotlin
    Intent(this, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }.run { startActivity(this) }
    finish()
```

### AndroidManifest

```xml
    <activity
    android:name=".RedirectActivity"
    android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="@string/url_scheme" />
        </intent-filter>
    </activity>
```