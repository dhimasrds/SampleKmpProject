# SampleKmpProject — Kotlin Multiplatform Template

A modern Kotlin Multiplatform (Android + iOS + Desktop) template using:
- Compose Multiplatform UI
- Ktor (OkHttp/Darwin), Koin DI, kotlinx.serialization
- Modular feature structure (core/*, feature/*)

Use this repository as a starting point for new apps. It includes a rename script, configurable BuildKonfig constants, and a sample CI workflow.

## What’s inside
- composeApp: App root, navigation, themes, and platform hosts (MainActivity / MainViewController)
- core: shared building blocks (di, network, model, util)
- feature: example features (home, login) and a template to duplicate (feature-template)
- iosApp: native iOS host that embeds the Compose UI

## Quick start
1) Requirements
- Android Studio, JDK 17+, Xcode (for iOS), Kotlin 2.2+ toolchain

2) Clone and rename
- Run ./rename.sh "com.mycompany.myapp" "MyApp" "com.mycompany.myapp" "https://api.example.com"
  - PACKAGE_NAME: e.g., com.mycompany.myapp
  - APP_NAME: e.g., MyApp
  - BUNDLE_ID: e.g., com.mycompany.myapp (can differ from PACKAGE_NAME)
  - BASE_URL: e.g., https://api.example.com

3) Open in Android Studio and Sync Gradle

4) Run the app
- Android: :composeApp:androidApp (assembleDebug)
- iOS: open iosApp/iosApp.xcodeproj, set your Team, run; if needed run ./gradlew :composeApp:syncFramework first

## BuildKonfig (Configuration)
We centralize configuration using BuildKonfig so values are available across platforms.
- BASE_URL is defined in core/di/build.gradle.kts inside the buildkonfig block.
- Access in code: com.example.samplekmpproject.config.BuildKonfig.BASE_URL
- Adjust per environment by editing the buildkonfig block or via CI variable substitution.

Important iOS note: We set Accept-Encoding: identity on Darwin to avoid the iOS “Content-length mismatch” issue with certain servers.

## Feature template
- feature/feature-template: a minimal, duplicable module with a sample Composable screen and Koin module.
- Duplicate it for new features, update package names, register DI module, and add navigation.

## CI
A sample GitHub Actions workflow is provided under .github/workflows/build.yml to build Android and iOS (Compose framework) on push/PR.

## Scripts
- rename.sh: Renames package, app name, bundle id, and updates BASE_URL across the project structure.

## Using rename.sh
rename.sh helps you quickly rebrand the template when starting a new project.

- Syntax:
  ./rename.sh "<PACKAGE_NAME>" "<APP_NAME>" "<BUNDLE_ID>" "<BASE_URL>"

- Arguments:
  - PACKAGE_NAME (required): e.g., com.mycompany.myapp
  - APP_NAME (optional, default: MyApp): e.g., MyApp
  - BUNDLE_ID (optional, default: PACKAGE_NAME): iOS bundle identifier
  - BASE_URL (optional, default: https://jsonplaceholder.typicode.com): your API base URL

- What the script changes:
  - Replaces the app name (SampleKmpProject) in project files.
  - Replaces the package name (com.example.samplekmpproject) in code and configurations.
  - Moves Kotlin source directories to match the new package path.
  - Updates iOS bundle identifier in the Xcode project and Info.plist.
  - Updates BASE_URL occurrences, including BuildKonfig in .kts (core/di/build.gradle.kts) and any .kt usages.

- Cross-platform notes:
  - Works on macOS and Linux (uses sed with cross-platform inline flags). On Windows, run via Git Bash or WSL.
  - If your app name contains spaces, wrap it in quotes: "My New App".

- After running the script:
  - Sync Gradle in Android Studio.
  - Optionally run ./gradlew clean build to verify.
  - For iOS, open iosApp/iosApp.xcodeproj in Xcode, set your Team, and run. If framework not found, run ./gradlew :composeApp:syncFramework first.

## Structure at a glance
- composeApp/src/commonMain/kotlin: App(), Navigation, shared UI
- core/network: Ktor setup, ApiClient, HttpClientFactory per platform
- core/di: Koin modules; BuildKonfig BASE_URL
- feature/home, feature/login: Examples
- feature/feature-template: starter module to copy

## License
Choose a license (MIT/Apache-2.0) to allow reuse as a template.