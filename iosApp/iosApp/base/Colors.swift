//
//  Colors.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

typealias NoctalColor = shared.Color

extension NoctalColor {
    func toPlatform() -> SwiftUI.Color {
        let v = self.argbLongValue
        
        let r = (Double((v >> 16 & 0xFF))/255.0)
        let g = (Double((v >> 8 & 0xFF))/255.0)
        let b = (Double((v >> 0 & 0xFF))/255.0)
        let a = (Double((v >> 24 & 0xFF))/255.0)
        
        return SwiftUI.Color(red: r, green: g, blue: b, opacity: a)
    }
    
    static func DEBUG_placeholder() -> SwiftUI.Color {
        return NoctalColor(rgb: "#4CFF0000").toPlatform()
    }
}
