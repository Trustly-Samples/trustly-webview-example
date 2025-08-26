//
//  ViewController.swift
//
//  Copyright © 2022 Trustly, Inc. All rights reserved.
//

import Foundation
import UIKit
import WebKit
import AuthenticationServices
// necessary for compatibility with iOS 12 and under
import SafariServices


class ViewController: UIViewController, WKNavigationDelegate, WKUIDelegate {
    
    private var establishData: [String: String] = [:]
    private let urlScheme = "in-app-browser-ios"
    private var webSession: ASWebAuthenticationSession!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.establishData = [
            "accessId": "A48B73F694C4C8EE6306",
            "merchantId" : "110005514",
            "currency" : "USD",
            "amount" : "1.00",
            "merchantReference" : "cac73df7-52b4-47d7-89d3-9628d4cfb65e",
            "paymentType" : "Retrieval",
            "returnUrl": "/returnUrl",
            "cancelUrl": "/cancelUrl",
            "requestSignature": "HT5mVOqBXa8ZlvgX2USmPeLns5o=",
            "customer.name": "John",
            "customer.address.country": "US",
            "theme": "dark",
            "metadata.theme": "dark",
            "metadata.urlScheme": "\(urlScheme)://",
            "metadata.integrationContext": "InAppBrowser",
            "description": "First Data Mobile Test",
            "flowType": "",
            "env": "sandbox",
            "envHost": "192.168.0.13"
        ]
    }
    
    // MARK: Actions
    @IBAction func openWidget(_ sender: Any) {
    }
    
    
    @IBAction func openLightbox(_ sender: Any) {
        }
    }

    private func buildASWebAuthenticationSession(url: URL, callbackURL: String){
        webSession = ASWebAuthenticationSession(url: url, callbackURLScheme: callbackURL, completionHandler: { (url, error) in

            self.proceedToChooseAccount()

        })

        webSession.prefersEphemeralWebBrowserSession = true
        webSession.presentationContextProvider = self
        webSession.start()
    }
    
        
    }

}
