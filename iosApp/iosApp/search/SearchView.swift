//
//  SearchView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct SearchView: View {
    var body: some View {
        Text("Search Page")
    }
}


struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: SearchView(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
