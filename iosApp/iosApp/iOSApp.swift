import SwiftUI
import shared

@main
struct iOSApp: App {
	var body: some Scene {
        let databaseDriverFactory = DatabaseDriverFactory()
        let persistenceSdk = PersistenceSDK(databaseDriverFactory: databaseDriverFactory)
        
		WindowGroup {
            ContentView(persistenceSdk: persistenceSdk)
		}
	}
}
