# MoviesWatchPro

MoviesWatchPro is an Android application that fetches and displays movies from an API, categorized into New, Upcoming, Trending, Now Showing, and Popular. The app is built using modern Android development practices, featuring Kotlin, Jetpack Compose, and the MVI architecture.

## Features

- **Movies Categorized**:
  - Upcoming Movies
  - Now Showing
  - Popular Movies
- **Modern UI**: Designed using Jetpack Compose for a sleek and responsive user experience.
- **Architecture**: Implements the MVI (Model-View-Intent) architecture for a unidirectional data flow.
- **Kotlin First**: Entirely written in Kotlin, leveraging its features for clean and concise code.

## Screenshots

![Home Screen](discover.png)![WatchList](watchlist.png)

![Detail](detail.png)![Sign up](sign_up.png)



- **Home Screen**: Displays categorized movies.
- **Movie Details**: View detailed information about each movie.

## Tech Stack

- **Kotlin**: A modern, concise, and safe programming language for Android development.
- **Jetpack Compose**: Declarative UI framework for building native Android apps.
- **MVI Architecture**: Ensures a predictable and maintainable unidirectional data flow.
- **Coroutines & Flow**: For asynchronous programming and managing data streams.

## Installation

## 🚀 Getting Started

### Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/donMuthiani/MoviesWatchPro.git
    cd MoviesWatchPro
    ```

2.  **Get an API Key:**
    * Visit The Movie Database (TMDb) Documentation.
    * Sign up or log in to obtain your API key.

3.  **Configure API Key:**
    * Open (or create) the `local.properties` file in the project's root directory.
    * Add the following line (replace `your_api_key_here` with the actual key):

        ```properties
        API_TOKEN=your_api_key_here
        ```

4.  **Expose API Key to the App:**
    * Modify `build.gradle.kts` (Module: app) to include:

        ```kotlin
        android {
            buildFeatures {
                buildConfig = true
            }

            defaultConfig {
                buildConfigField("String", "API_TOKEN", "\"${project.findProperty(\"API_TOKEN\")}\"")
            }
        }
        ```

5.  **Sync the Project:**
    * Run the following command:

        ```bash
        ./gradlew sync
        ```

    * Or manually sync from Android Studio (File > Sync Project with Gradle Files).

