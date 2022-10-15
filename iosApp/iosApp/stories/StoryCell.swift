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
    var body: some View {
            HStack(spacing: 0) {
                RoundedRectangle(cornerRadius: dims.DimImgRadius)
                    .frame(width: dims.DimImg, height: dims.DimImg)
                    .padding(.horizontal, dims.DimHPadding)
                    .foregroundColor(debugColor)
                
                VStack(alignment: .leading, spacing: dims.DimVPadding) {
                    StoryLabel("1.")
                    StoryLabel("This is the titlez", styles.FontSizeTitle)
                    StoryLabel("JamesSwift")
                    StoryLabel("311")
                }.frame(maxWidth: .infinity, alignment: .leading)
                    .padding(EdgeInsets(top: dims.DimVPadding, leading: 0, bottom: dims.DimVPadding, trailing: dims.DimHPadding))
                    .layoutPriority(1)
                    
            }
    }
}

fileprivate struct StoryLabel: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    private let text: String
    private let fontSize: Double
    
    init(_ text: String, _ fontSize: Double = styles.FontSizeDefault) {
        self.text = text
        self.fontSize = fontSize
    }
    
    var body: some View {
        Text(text)
            .foregroundColor(noctalTheme.onBackgroundColor.toPlatform())
            .font(.system(size: fontSize))
    }
}


struct StoryCell_Previews: PreviewProvider {
    static var previews: some View {
//        StoryCell().previewLayout(PreviewLayout.sizeThatFits)
        
                ModifiedContent(
                    content: StoryCell().previewLayout(PreviewLayout.sizeThatFits),
                    modifier: WithThemes()
//                    modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad])
                )
    }
}

