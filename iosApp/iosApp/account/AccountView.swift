//
//  AccountView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct AccountView: View {
    var body: some View {
        Text("Account Page")
    }
}


struct AccountView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: AccountView(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
