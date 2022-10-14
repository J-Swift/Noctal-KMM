import SwiftUI
import shared

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            AppWrapper()
        }
    }
}

struct AppWrapper : View {
    @Environment(\.colorScheme) var scheme
    
    var body: some View {
        ContentView()
            .environment(\.noctalTheme, scheme == .light ? LightTheme() : DarkTheme())
    }
}

typealias NoctalColor = shared.Color


extension NoctalColor {
    func toPlatform() -> SwiftUI.Color {
        let v = self.longValue
        
        let r = (Double((v >> 16 & 0xFF))/255.0)
        let g = (Double((v >> 8 & 0xFF))/255.0)
        let b = (Double((v >> 0 & 0xFF))/255.0)
        let a = (Double((v >> 24 & 0xFF))/255.0)
        
        return SwiftUI.Color(red: r, green: g, blue: b, opacity: a)
    }
}
private struct NoctalThemeKey: EnvironmentKey {
    static let defaultValue = DarkTheme() as ITheme
}

extension EnvironmentValues {
    var noctalTheme: ITheme {
        get { self[NoctalThemeKey.self] }
        set { self[NoctalThemeKey.self] = newValue }
    }
}
