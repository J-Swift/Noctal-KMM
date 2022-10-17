//
//  StoryView.swift
//  iosApp
//
//  Created by James Reichley on 10/14/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension Story: Identifiable {
    
}

struct StoriesView: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    @State var stories = [Story]()
    @State var selectedIds = Set<String>()
    
    var body: some View {
        NavigationView {
            List(stories, selection: $selectedIds) { it in
                let idx = stories.firstIndex(of: it)!
                let isSelected = selectedIds.contains(it.id)
                
                ZStack {
                    VStack(spacing: 0) {
                        StoryCell(story: it, index: idx + 1, isSelected: isSelected)
                        
                        Divider()
                    }
                    
                    NavigationLink(destination: Text(it.title)) {
                        EmptyView()
                    }.frame(width: 0).opacity(0)
                }
                .listRowSeparator(.hidden)
                .listRowInsets(EdgeInsets())
            }
            .listStyle(.plain)
            .task {
                stories = HNApiMock.companion.stories
            }
        }
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
