//
//  ViewController.swift
//
//  Copyright © 2022 Trustly, Inc. All rights reserved.
//

import Foundation
import UIKit
import AuthenticationServices
import SafariServices

class ViewController: UIViewController, SFSafariViewControllerDelegate {
    
    private var establishData: [String: String] = [:]
    private let urlScheme = "in-app-browser-ios" // YOUR APP URL SCHEME
    private var webSession: ASWebAuthenticationSession!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Retrieve stored trustlyContext
        let storedTrustlyContext = getStoredTrustlyContext()

        self.establishData = [
            "accessId": "A48B73F694C4C8EE6306",
            "merchantId" : "110005514",
            "currency" : "USD",
            "amount" : "1.00",
            "merchantReference" : "MERCHANT_REFERENCE",
            "paymentType" : "Retrieval",
            "returnUrl": "\(urlScheme)://success",
            "cancelUrl": "\(urlScheme)://cancel",
            "requestSignature": "REQUEST_SIGNATURE",
            "customer.name": "John",
            "customer.address.country": "US",
            "metadata.urlScheme": "\(urlScheme)://",
            "metadata.trustlyContext": storedTrustlyContext,
            "description": "First Data Mobile Test",
            "flowType": "",
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

    private func buildASWebAuthenticationSession(url: URL, callbackURL: String){
        webSession = ASWebAuthenticationSession(url: url, callbackURLScheme: callbackURL, completionHandler: { (url, error) in

            if let urlString = url?.absoluteString {
                // Extract and save trustlyContext from URL
                self.extractAndSaveTrustlyContext(from: urlString)
                
                if urlString.contains("success") {
                    self.showAlertWith(success: true)
                } else if urlString.contains("cancel") {
                    self.showAlertWith(success: false)
                } else {
                    self.showAlertWith(success: false)
                }
            } else {
                self.showAlertWith(success: false)
            }
        })

        webSession.prefersEphemeralWebBrowserSession = true
        webSession.presentationContextProvider = self
        webSession.start()
    }
    
    
    // MARK: Helper functions
    
    /// Update establishData with current trustlyContext
    private func updateEstablishData() {
        let storedTrustlyContext = getStoredTrustlyContext()
        self.establishData["metadata.trustlyContext"] = storedTrustlyContext
        print("EstablishData updated with trustlyContext: \(storedTrustlyContext)")
    }
    
    /// Extract trustlyContext parameter from URL and save to UserDefaults
    private func extractAndSaveTrustlyContext(from urlString: String) {
        guard let url = URL(string: urlString),
              let components = URLComponents(url: url, resolvingAgainstBaseURL: false),
              let queryItems = components.queryItems else {
            return
        }
        
        // Look for trustlyContext parameter
        if let trustlyContextItem = queryItems.first(where: { $0.name == "trustlyContext" }),
           let trustlyContextValue = trustlyContextItem.value {
            
            // Save to UserDefaults
            UserDefaults.standard.set(trustlyContextValue, forKey: "trustlyContext")
            print("TrustlyContext saved: \(trustlyContextValue)")
            
            // Update establishData with the new trustlyContext
            self.establishData["metadata.trustlyContext"] = trustlyContextValue
        }
    }
    
    /// Retrieve trustlyContext from UserDefaults
    private func getStoredTrustlyContext() -> String {
        return UserDefaults.standard.string(forKey: "trustlyContext") ?? "new"
    }
    
    private func buildUrl(showWidget: Bool = false) -> URL? {
        // Update establishData with current trustlyContext before building URL
        updateEstablishData()
        
        let establishDotNotation = EstablishDataUtils.normalizeEstablishWithDotNotation(establish: self.establishData)
        let establishBase64 = JSONUtils.getJsonBase64From(dictionary: establishDotNotation) ?? ""
        
        // CHOOSE BETWEEN THESE ENVIRONMENTS
        // PROD: https://trustly.one
        // SANDBOX: https://sandbox.trustly.one
        
        let baseUrl = "http://122.132.142.28:10000/frontend/mobile/establish?widget=\(showWidget)&token=\(establishBase64)"
        
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
