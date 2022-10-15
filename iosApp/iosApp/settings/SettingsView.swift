//
//  SettingsView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct SettingsView: View {
    var body: some View {
        Text("Settings Page")
    }
}


struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: SettingsView(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
