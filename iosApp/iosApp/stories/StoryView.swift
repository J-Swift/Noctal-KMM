//
//  StoryView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct StoriesView: View {
    var body: some View {
        List {
            ForEach((1...10), id: \.self) { it in
                VStack(spacing: 0) {
                    StoryCell()
                    
                    Rectangle()
                        .frame(height: 1)
                        .background(Color(UIColor.opaqueSeparator))
                }.listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
            }
        }.listStyle(.plain)
    }
}


struct StoriesView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: StoriesView(),
            modifier: WithThemes()
            //            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
