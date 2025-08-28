# About this demo

The purpose of this app demo is to demonstrate how to implement the OAuth login flow in iOS apps.


**IMPORTANT:**

 `SFSafari` is not allowed to call a deep link, in other words, the code bellow will not work, unless you utilize the `SFAuthenticationSession` or `ASWebAuthenticationSession` classes, based on the supported iOS version.

```javascript
    window.location.href = "myapp://"
```

One of the parameters that we need in establish data is the `urlScheme`, this parameter is mandatory to help in the navigation.

The `urlScheme` is a required parameter of the `ASWebAuthenticationSession` method. When the authentication process is completed, the `ASWebAuthenticationSession` component will utilize the `urlScheme` to manage the InAppBrowser screen.
In the `App-to-App` user flow where the user is redirected to a bank's mobile app for authentication, the `urlScheme` will be used as the target upon redirect back to your application. In this case, the `AppDelegate` or `SceneDelegate` files in your iOS application will handle the behavior upon redirect.
The same value in the `urlScheme` we need to use in `cancel


## AppDelegate vs SceneDelegate

SceneDelegate was introduced with iOS 13. The SceneDelegate will be responsible for what is shown on the screen (Windows or Scenes). It will also handle and manage the way your app is displayed to the user.

If your application was created before iOS 13 and was not updated to use the SceneDelegate, in this case all functions that will handle with `DeepLinks` or `urlScheme` should be implemented in AppDelegate file.

## Introduction

To open Trustly widget or lightbox we need to build an url with a `token`. This `token` is the `establish data` in `base 64` format.

### Building url
The base url is `https://sandbox.paywithmybank.com/frontend/mobile/establish`.

`Establish data` is a dictionary with all parameters to create a transaction, to transform `establish data` to `base 64` format we should convert the dicitionary in a `json string` and after that create the `base 64` value.

```swift
let establishBase64 = JSONUtils.getJsonBase64From(dictionary: establishDotNotation) ?? ""
```

The `JSONUtils` class, is a custom class created by Trustly, you can check the content in the file `in-app-browser-ios/Utils/JSONUtils.swift`.

In the url, you can send the `widget` parameter, to ask to open the `widget` if the `true`, or the lightbox, if `false`.

The complete url with `base 64` `token` and `widget` parameter as `true`.

```swift
https://sandbox.paywithmybank.com/frontend/mobile/establish?widget=true&token=eyJhY2Nlc3NJZCI6IkE0OEI3M0Y2OTRDNEM4RUU2MzA2IiwicGF5bWVudFR5cGUiOiJSZXRyaWV2YWwiLCJmbG93VHlwZSI6IiIsIm1ldGFkYXRhIjp7InNka0lPU1ZlcnNpb24iOiIzLjMuMCIsInVybFNjaGVtZSI6ImRlbW9hcHA6XC9cLyIsImNpZCI6IjEwRjYtNENFQy1NRVQwWERIUSIsInRoZW1lIjoiZGFyayJ9LCJzZXNzaW9uQ2lkIjoiMTBGNi00NDFBLU1FVDBCV0c0IiwicmV0dXJuVXJsIjoiZGVtb2FwcDpcL1wvIiwicmVxdWVzdFNpZ25hdHVyZSI6IkhUNW1WT3FCWGE4Wmx2Z1gyVVNtUGVMbnM1bz0iLCJ0aGVtZSI6ImRhcmsiLCJ2ZXJzaW9uIjoiMiIsImVudiI6InNhbmRib3giLCJjdXJyZW5jeSI6IlVTRCIsIm1lcmNoYW50SWQiOiIxMTAwMDU1MTQiLCJjYW5jZWxVcmwiOiJkZW1vYXBwOlwvXC8iLCJtZXJjaGFudFJlZmVyZW5jZSI6ImNhYzczZGY3LTUyYjQtNDdkNy04OWQzLTk2MjhkNGNmYjY1ZSIsImVudkhvc3QiOiIxOTIuMTY4LjAuMTMiLCJwYXltZW50UHJvdmlkZXJJZCI6IjIwMDAwNTUwMSIsImdycCI6IjE4Iiwid2lkZ2V0TG9hZGVkIjoidHJ1ZSIsImN1c3RvbWVyIjp7ImFkZHJlc3MiOnsiY291bnRyeSI6IlVTIn0sIm5hbWUiOiJKb2huIn0sImRldmljZVR5cGUiOiJtb2JpbGU6aW9zOm5hdGl2ZSIsImFtb3VudCI6IjEwLjAwIiwiZGVzY3JpcHRpb24iOiJGaXJzdCBEYXRhIE1vYmlsZSBUZXN0In0=
```

After built Trustly url, now, you can open widget or lightbox with oAuth component.

We have two solutions to implement a better experience when the app needs to open a new browser window to complete the OAuth login flow.

- SFAuthenticationSession for **iOS 9 until 12**
- ASWebAuthenticationSession for **iOS 13 or higher**


### SFAuthenticationSession

This solution will work only for iOS version between 9 and 12.

```swift
    let session = SFAuthenticationSession(url: url, callbackURLScheme: calbackURL, completionHandler: { (url, error) in
        //TODO: add your custom behavior here
    })

    session.start()
```


### ASWebAuthenticationSession

This solution will work for iOS version 13 and higher.

First step, you need to import the `AuthenticationServices`.

```swift
    import AuthenticationServices
```

After that will be necessary extends the `ASWebAuthenticationPresentationContextProviding` in your controller.

```swift
    extension ViewController: ASWebAuthenticationPresentationContextProviding {
        func presentationAnchor(for session: ASWebAuthenticationSession) -> ASPresentationAnchor {
            return ASPresentationAnchor()
        }
    }
```

And finally, you can implement the method to create the OAuth behavior.

```swift
    let webSession = ASWebAuthenticationSession(url: url, callbackURLScheme: calbackURL, completionHandler: { (url, error) in
            //TODO: add your custom behavior here
        })
    
    webSession.prefersEphemeralWebBrowserSession = true
    webSession.presentationContextProvider = self
    webSession.start()
```

**IMPORTANT:**

The attribute `prefersEphemeralWebBrowserSession` controls if the `ASWebAuthenticationSession` will show the authorization alert or not.

![Authentication Alert](resources/print_en.png "Authentication Alert")
