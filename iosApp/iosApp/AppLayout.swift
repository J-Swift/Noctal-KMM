import SwiftUI
import shared

struct AppLayout: View {
    @Environment(\.noctalTheme) var noctalTheme
    
    var body: some View {
        TabView {
            StoriesView()
                .tabItem({Label(NavRoute.Stories.shared.displayName, systemImage: "plus")})
            SearchView()
                .tabItem({Label(NavRoute.Search.shared.displayName, systemImage: "plus")})
            AccountView()
                .tabItem({Label(NavRoute.Account.shared.displayName, systemImage: "plus")})
            SettingsView()
                .tabItem({Label(NavRoute.Settings.shared.displayName, systemImage: "plus")})
        }
    }
}

struct AppLayout_Previews: PreviewProvider {
    static var previews: some View {
        ModifiedContent(
            content: AppLayout(),
            modifier: WithThemes()
//            modifier: AllPreviewDevices(deviceTypes: [.iPhone, .iPad]))
        )
    }
}
