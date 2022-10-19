//
//  StoryCell.swift
//  iosApp
//
//  Created by James Reichley on 10/15/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

fileprivate let debugColor = NoctalColor.DEBUG_placeholder()
fileprivate let dims = StoryCellConfig.Dims.shared
fileprivate let styles = StoryCellConfig.Styling.shared

struct StoryCell: View {
    @Environment(\.noctalTheme) var noctalTheme
    @Environment(\.colorScheme) var colorScheme
    
    var story: Story
    var index: Int
    var isSelected: Bool = false
    
    var body: some View {
        let highlightColor = colorScheme == .light ? styles.CellHighlightLt : styles.CellHighlightDk
        let placeholderColor = styles.PlaceholderColors[index % styles.PlaceholderColors.count]
        
        HStack(spacing: 0) {
            ZStack {
                RoundedRectangle(cornerRadius: dims.DimImgRadius)
                    .frame(width: dims.DimImg, height: dims.DimImg)
                    .padding(.horizontal, dims.DimHPadding)
                    .foregroundColor(placeholderColor.toPlatform())
                
                Text(story.placeholderLetter ?? "Y")
                    .foregroundColor(Color.white)
                    .font(.system(size: styles.FontSizePlaceholder))
            }
            
            VStack(alignment: .leading, spacing: dims.DimVPadding) {
                HStack(spacing: dims.DimHPaddingRow) {
                    StoryLabel(text: "\(index).")
                    RoundedRectangle(cornerRadius: dims.DimImgFavicon/2.0)
                        .frame(width: dims.DimImgFavicon, height: dims.DimImgFavicon)
                        .foregroundColor(debugColor)
                    StoryLabel(text: story.displayUrl ?? " ", textColor: noctalTheme.primaryColor.toPlatform())
                        .minimumScaleFactor(0.2)
                }
                
                StoryLabel(text: story.title, fontSize: styles.FontSizeTitle, lineLimit: nil)
                
                HStack(spacing: dims.DimHPaddingRow) {
                    StoryLabel(text: story.author)
                    StoryLabel(text: "•")
                    StoryLabel(text: "4h ago")
                }
                
                HStack(spacing: dims.DimHPaddingRow) {
                    StoryLabel(text: "↑")
                    StoryLabel(text: String(story.score))
                    StoryLabel(text: "•")
                    StoryLabel(text: "\(story.numComments) comments")
                }
            }.frame(maxWidth: .infinity, alignment: .leading)
                .padding(EdgeInsets(top: dims.DimVPadding, leading: 0, bottom: dims.DimVPadding, trailing: dims.DimHPadding))
                .layoutPriority(1)
            
        }
        .background((isSelected ? highlightColor : noctalTheme.backgroundColor).toPlatform())
    }
}

fileprivate struct StoryLabel: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    var text: String
    var textColor: SwiftUI.Color?
    var fontSize = styles.FontSizeDefault
    var lineLimit: Int? = 1
    
    var body: some View {
        Text(text)
            .foregroundColor(textColor ?? noctalTheme.onBackgroundColor.toPlatform())
            .font(.system(size: fontSize))
            .lineLimit(lineLimit)
    }
}

struct StoryCell_Previews: PreviewProvider {
    static var previews: some View {
        let configs = [
            (StoryCell(story: HNApiMock.companion.stories[0], index: 1, isSelected: false), "Default"),
            (StoryCell(story: HNApiMock.companion.stories[0], index: 1, isSelected: true), "Selected")
        ]
        
        ForEach(configs, id: \.1) { (config, title) in
            ModifiedContent(
                content: config.previewLayout(PreviewLayout.sizeThatFits),
                modifier: WithThemes()
            ).previewDisplayName(title)
        }
    }
}
