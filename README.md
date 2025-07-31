# Trustly Mobile Webview Example App

<img src="https://user-images.githubusercontent.com/13320316/255275758-068fbcd2-b681-4fdb-a914-15ba1c875c5a.png" alt="screenshot of example app" width="250">

This project is provided as an example of integration between a web app serving the Trustly UI and mobile apps that render that web app from a webview. Specifically, this example focuses on successfully handling OAuth and App-to-App bank authorization user flows. The repository can be used alongside Trustly's [Mobile App Webview tutorial](https://amer.developers.trustly.com/payments/docs/oauth-for-mobile-apps).

## The Web App

The `react-web-app` directory contains a simple React app that displays a "Pay with Trustly" button which launches the Lightbox. Instructions for running this app can be found in that directory's [ReadMe](./react-web-app/README.md).

## The iOS App

The `ios-app` directory contains a simple Swift app that renders a Webview of a given URL. With the above React app running, the iOS app can be pointed to the resulting URL to demonstrate the webview and secure-in-app-browser functionality required to successfully integrate the two applications. Additional instructions can be found in that directory's [ReadMe](./ios-app/README.md).

## The Android App

The `android-app` directory contains a simple Kotlin app that renders a Webview of a given URL. With the above React app running, the Android app can be pointed to the resulting URL to demonstrate the webview and secure-in-app-browser functionality required to successfully integrate the two applications. Additional instructions can be found in that directory's [ReadMe](./android-app/README.md).

# Contributing

You can participate in this project by submitting bugs and feature requests in the [Issues](https://github.com/TrustlyInc/trustly-webview-example/issues) tab.

If you are interested in fixing issues and contributing directly to the code base, feel free to open a Pull Request with your changes. Please, make sure to fulfill our [Pull Request Template](https://github.com/TrustlyInc/trustly-webview-example/blob/main/.github/pull_request_template.md).
