//
//  StoryView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct StoryView: View {
    var body: some View {
        Text("Story Page")
    }
}


struct StoryView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: StoryView(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
