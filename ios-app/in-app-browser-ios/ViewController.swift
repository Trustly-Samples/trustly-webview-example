//
//  ViewController.swift
//
//  Copyright © 2022 Trustly, Inc. All rights reserved.
//

import Foundation
import UIKit
import AuthenticationServices

class ViewController: UIViewController {
    
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
            "returnUrl": "\(urlScheme)://",
            "cancelUrl": "\(urlScheme)://",
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
        
        if let url = buildUrl(showWidget: true) {
            buildASWebAuthenticationSession(url: url, callbackURL: urlScheme)
        }
    }
    
    
    @IBAction func openLightbox(_ sender: Any) {
        if let url = buildUrl() {
            buildASWebAuthenticationSession(url: url, callbackURL: urlScheme)
        }
    }

    // MARK: oAUTH
    private func buildASWebAuthenticationSession(url: URL, callbackURL: String){
        webSession = ASWebAuthenticationSession(url: url, callbackURLScheme: callbackURL, completionHandler: { (url, error) in

            if url == nil {
                self.showAlertWith(success: false)
                
            } else {
                self.showAlertWith(success: true)
                
            }
        })

        webSession.prefersEphemeralWebBrowserSession = true
        webSession.presentationContextProvider = self
        webSession.start()
    }
    
    // MARK: Helper functions
    private func buildUrl(showWidget: Bool = false) -> URL? {
        let establishDotNotation = EstablishDataUtils.normalizeEstablishWithDotNotation(establish: self.establishData)
        let establishBase64 = JSONUtils.getJsonBase64From(dictionary: establishDotNotation) ?? ""
        
        let baseUrl = "https://sandbox.paywithmybank.com/frontend/mobile/establish?widget=\(showWidget)&token=\(establishBase64)"
        
        return URL(string: baseUrl)
        
    }
    
    private func showAlertWith(success: Bool) {
        
        var message = ""
        
        if success {
            message = "Authorization successful!"
            
        } else {
            message = "Autorization failed!"
            
        }
        
        let alertMessagePopUpBox = UIAlertController(title: "Authorization", message: message, preferredStyle: .alert)
        let okButton = UIAlertAction(title: "OK", style: .default)
        
        alertMessagePopUpBox.addAction(okButton)
        
        self.present(alertMessagePopUpBox, animated: true)
    }

}
