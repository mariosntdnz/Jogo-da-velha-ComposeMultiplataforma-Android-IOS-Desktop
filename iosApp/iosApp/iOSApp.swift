import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        MainViewControllerKt.doInitKoinIos()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}