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

extension Story: Identifiable { }
extension StoryWithMeta: Identifiable {
    public var id: String {
        get {
            story.id
        }
    }
}

struct StoriesView: View {
    @ObservedObject var vm = IosStoriesViewModel()
    
    var body: some View {
        StoriesView_Content(stories: vm.stories)
            .onAppear {
                vm.start()
            }
            .onDisappear {
                vm.stop()
            }
    }
}

struct StoriesView_Content: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    @State var selectedId: String?
    var stories: [StoryWithMeta] = [StoryWithMeta]()
    
    var body: some View {
        NavigationView {
            
            List(stories, selection: $selectedId) { it in
                let idx = stories.firstIndex(of: it)!
                let isSelected = it.story.id == selectedId
                
                ZStack {
                    VStack(spacing: 0) {
                        StoryCell(data: it, index: idx + 1, isSelected: isSelected)
                        
                        Divider()
                    }
                    
                    NavigationLink(destination: Text(it.story.title)) {
                        EmptyView()
                    }.frame(width: 0).opacity(0)
                }
                .listRowInsets(EdgeInsets())
            }
            .listStyle(.plain)
            .onAppear {
                withAnimation { selectedId = nil }
            }
        }
    }
}

struct StoriesView_Previews: PreviewProvider {
    static var previews: some View {
        let stories = StoriesRepositoryKt.previewStories.map {
            StoryWithMeta(story: $0, meta: nil)
        }
        ModifiedContent(
            content: StoriesView_Content(selectedId: "", stories: stories),
            modifier: WithThemes()
            //            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
