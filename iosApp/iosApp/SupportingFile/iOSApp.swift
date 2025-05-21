import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            TabView {
                MoviesScreen(viewModel: .init()).tabItem {
                    Label("Movies", systemImage: "film")
                }
                MoviesScreen(viewModel: .init()).tabItem {
                    Label("Favorites", systemImage: "heart")
                }
            }
        }
    }
}
