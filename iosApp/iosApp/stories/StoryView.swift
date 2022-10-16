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

struct StoriesView: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    @State var stories = [Story]()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(stories, id: \.id) { it in
                    ZStack {
                        VStack(spacing: 0) {
                            StoryCell(story: it)
                            
                            Divider()
                        }
                        
                        NavigationLink(destination: Text(it.title)) {
                            EmptyView()
                        }.frame(width: 0).opacity(0)
                    }
                }.listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
            }.listStyle(.plain)
                .task {
                    stories = HNApiMock.companion.stories
//                    stories = await withCheckedContinuation({(context: CheckedContinuation<[Story], Never>) in
//                        HNApiMock().getStoriesAsync { stories, err in
////                            if let err = err {
////                                context.resume(with: .failure())
////                            } else {
//                                context.resume(with: .success(stories ?? []))
////                            }
//                        }
//                    })
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
