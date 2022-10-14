import SwiftUI
import shared

struct ContentView: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    var body: some View {
        ZStack {
            noctalTheme.surfaceColor.toPlatform()
            Text("Hi")
                .foregroundColor(noctalTheme.onSurfaceColor.toPlatform())
        }
    }
}

struct WithThemes: ViewModifier {
    @Environment(\.colorScheme) var scheme
    public func body(content: Content) -> some View {
        content
            .environment(\.noctalTheme, scheme == .light ? LightTheme() : DarkTheme())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: ContentView(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}



public struct AllPreviewDevices: ViewModifier {
    
    /// Represents all the different device types that can be previewed at a time.
    /// You can use it by making a set of the ones you want to have.
    /// e.g. `[.iPhone, .iPad]`
    /// - Note: You can't use `tv` at the same time as `iPhone` or `iPad`.
    public struct DeviceType: OptionSet {
        public let rawValue: Int
        public init(rawValue: Int) {
            self.rawValue = rawValue
        }
        public static let iPhone = DeviceType(rawValue: 1 << 0)
        public static let iPad = DeviceType(rawValue: 1 << 1)
        public static let tv = DeviceType(rawValue: 1 << 2)
    }
    
    /// Set the type of devices you want previewed.
    public let deviceTypes: DeviceType
    
    // Maximum number of previews is 15!
    public func body(content: Content) -> some View {
        Group {
            if deviceTypes.contains(.iPhone) {
                // 1
                content
                    .previewDevice(.iPhone11Pro)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("11 Pro + Normal Text Size")
                
                // Color Scheme
                // 2
                content
                    .previewDevice(.smallestiPhone)
                    .environment(\.colorScheme, .dark)
                    .previewDisplayName("Smallest Phone + Dark")
                
                // Text Sizes
                // 3
                content
                    .previewDevice(.smallestiPhone)
                    .environment(\.sizeCategory, .accessibilityExtraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("Smallest Phone + Largest Accessibility")
                // 4, no notch
                content
                    .previewDevice(.iPhone8)
                    .environment(\.sizeCategory, .extraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("No notch + Large Text Size")
                // 5, smallest accessibility
                content
                    .previewDevice(.iPhone8)
                    .environment(\.sizeCategory, .accessibilityMedium)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("No notch + Smallest Accessibility")
                
                // Layout direction + Bold
                // 6
                content
                    .previewDevice(.iPhone11ProMax)
                    .environment(\.colorScheme, .light)
                    .environment(\.layoutDirection, .rightToLeft)
                    .environment(\.legibilityWeight, .bold)
                    .previewDisplayName("11 Pro Max, RTL, Bold")
            }
            
            if deviceTypes.contains(.iPad) {
                // 7
                content
                    .environment(\.colorScheme, .light)
                    .previewLayout(.iPadPro11Landscape)
                    .previewDisplayName("iPad 11\" Landscape")
                
                // Text Sizes
                // 8
                content
                    .previewDevice(.smallestiPad)
                    .environment(\.sizeCategory, .accessibilityExtraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("Smallest iPad + Largest Accessibility")
                // 8
                content
                    .previewDevice(.iPadPro11)
                    .environment(\.sizeCategory, .medium)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("iPad 11\" + Normal Text Size")
                // 9, no notch
                content
                    .previewDevice(.iPadPro9)
                    .environment(\.sizeCategory, .extraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("iPad No notch + Large Text Size")
                
                // Color Scheme
                // 10
                content
                    .previewDevice(.largestiPad)
                    .environment(\.colorScheme, .dark)
                    .previewDisplayName("Largest iPad + Dark")
                
                // Layout direction + Bold
                // 11
                content
                    .previewDevice(.smallestiPad)
                    .environment(\.colorScheme, .light)
                    .environment(\.layoutDirection, .rightToLeft)
                    .environment(\.legibilityWeight, .bold)
                    .previewDisplayName("Smallest iPad, RTL, Bold")
                
                // Multitasking
                // 12
                content
                    .environment(\.colorScheme, .light)
                    .environment(\.horizontalSizeClass, .compact)
                    .previewLayout(.iPadProSplit11Landscape)
                    .previewDisplayName("iPad 11\" Split Landscape")
                // 13
                content
                    .environment(\.colorScheme, .light)
                    .environment(\.horizontalSizeClass, .compact)
                    .previewLayout(.iPadProSplit11Porait)
                    .previewDisplayName("iPad 11\" Split Portrait")
            }
            
            if deviceTypes.contains(.tv) {
                // Text Sizes
                // 1
                content
                    .previewDevice(.regularTV)
                    .environment(\.sizeCategory, .accessibilityExtraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("1080 + Largest Accessibility")
                // 2
                content
                    .previewDevice(.uhdTV)
                    .environment(\.sizeCategory, .medium)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("4K + Normal Text Size")
                // 3
                content
                    .previewDevice(.uhdTV)
                    .environment(\.sizeCategory, .extraExtraExtraLarge)
                    .environment(\.colorScheme, .light)
                    .previewDisplayName("4K + Large Text Size")
                
                // Color Scheme
                // 4
                content
                    .previewDevice(.uhdTV)
                    .environment(\.colorScheme, .dark)
                    .previewDisplayName("4K + Dark")
                
                // Layout direction + Bold
                // 5
                content
                    .previewDevice(.uhdTV)
                    .environment(\.colorScheme, .light)
                    .environment(\.layoutDirection, .rightToLeft)
                    .environment(\.legibilityWeight, .bold)
                    .previewDisplayName("4K, RTL, Bold")
            }
        }
    }
}

extension View {
    
    /// This will make multiple new preview devices to test all different types of edge
    /// cases.
    /// - Note: This should only be used in DEBUG in your PreviewProvider for a view.
    /// - Parameter deviceTypes: The type of devices you wish to test. Make sure to give
    /// at least 1 type. You cannot mix iPhone and iPad with tv.
    /// e.g. `[.iPhone, .iPad]`
    public func allPreviewDevices(deviceTypes: AllPreviewDevices.DeviceType) -> some View {
        ModifiedContent(
            content: self,
            modifier: AllPreviewDevices(deviceTypes: deviceTypes))
    }
    
#if os(iOS)
    /// This will make multiple new preview devices to test all different types of edge
    /// cases.
    /// This defaults to displaying all device types running iOS or iPadOS.
    public func allPreviewDevices() -> some View {
        ModifiedContent(
            content: self,
            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
    }
#elseif os(tvOS)
    /// This will make multiple new preview devices to test all different types of edge
    /// cases.
    /// This defaults to displaying all device types for tvOS.
    public func allPreviewDevices() -> some View {
        ModifiedContent(
            content: self,
            modifier: AllPreviewDevices(deviceTypes: [.tv]))
    }
#endif
}

public extension PreviewDevice {
    
    // iPhone
    static let iPhoneSE = PreviewDevice(rawValue: "iPhone SE")
    static let iPhone8 = PreviewDevice(rawValue: "iPhone 8")
    static let iPhone11 = PreviewDevice(rawValue: "iPhone 11")
    static let iPhone11Pro = PreviewDevice(rawValue: "iPhone 11 Pro")
    static let iPhone8Plus = PreviewDevice(rawValue: "iPhone 8 Plus")
    static let iPhone11ProMax = PreviewDevice(rawValue: "iPhone 11 Pro Max")
    
    static let smallestiPhone = iPhoneSE
    static let largestiPhone = iPhone11ProMax
    
    // iPad
    static let iPadPro9 = PreviewDevice(rawValue: "iPad Pro (9.7-inch)")
    static let iPadPro11 = PreviewDevice(rawValue: "iPad Pro (11-inch)")
    static let iPadPro12 = PreviewDevice(rawValue: "iPad Pro (12.9-inch) (3rd generation)")
    
    static let smallestiPad = iPadPro9
    static let largestiPad = iPadPro12
    
    // TV
    static let regularTV = PreviewDevice(rawValue: "Apple TV")
    static let uhdTV = PreviewDevice(rawValue: "Apple TV 4K")
}

extension PreviewLayout {
    
    // iPad
    static let iPadPro11Landscape = PreviewLayout.fixed(width: 1194, height: 834)
    static let iPadPro12Landscape = PreviewLayout.fixed(width: 1366, height: 1024)
    static let iPadProSplit11Landscape = PreviewLayout.fixed(width: 592, height: 834)
    static let iPadProSplit11Porait = PreviewLayout.fixed(width: 504, height: 1194)
}
