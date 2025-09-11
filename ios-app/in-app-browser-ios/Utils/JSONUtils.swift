//
//  JsonUtils.swift
//  in-app-browser-ios
//
//  Created by Marcos Rivereto on 26/08/25.
//  Copyright © 2025 Pedro Paulo Abdenor. All rights reserved.
//

import Foundation
import os

struct JSONUtils {
    
    static private func getJsonDataFrom(dictionary: [AnyHashable : Any]) -> Data? {
        
        do {

            let jsonData:Data = try JSONSerialization.data(withJSONObject: dictionary)

            return jsonData
            
        } catch {
            print("Error when try to get json from data: \(error.localizedDescription)")
        }
        
        return nil
    }
    
    static func getJsonBase64From(dictionary: [AnyHashable : Any]) -> String? {
        
        guard let jsonData = getJsonDataFrom(dictionary: dictionary) else { return nil }
        let jsonBase64 = jsonData.base64EncodedString()
        
        return jsonBase64
            
    }
}
