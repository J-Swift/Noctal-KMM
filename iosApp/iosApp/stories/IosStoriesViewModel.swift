//
//  IosStoriesViewModel.swift
//  iosApp
//
//  Created by James Reichley on 10/23/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ObservableViewModel {
    private var jobs = Array<Closeable>() // List of Kotlin Coroutine Jobs

    func addObserver(observer: Closeable) {
        jobs.append(observer)
    }
    
    func stop() {
        jobs.forEach { job in job.close() }
    }
}

class IosStoriesViewModel: ObservableViewModel, ObservableObject {
    @Published var stories: [StoryWithMeta] = []

    private let vm: SharedStoriesViewModel = SharedStoriesViewModel(storiesRepository: StoriesRepository(db: Database(), api: HNApiMock(), metaFetcher: MetaFetcher()))
        
    func start() {
        addObserver(observer: vm.observeStories().watch { stories in
            self.stories = stories as! [StoryWithMeta]
        })
    }
    
    override func stop() {
        super.stop()
        vm.close()
    }
}
