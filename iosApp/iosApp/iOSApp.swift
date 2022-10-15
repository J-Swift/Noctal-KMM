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
        AppLayout()
            .environment(\.noctalTheme, scheme == .light ? LightTheme() : DarkTheme())
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
