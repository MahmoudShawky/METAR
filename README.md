# NOAA_METAR
Android METAR is a format for reporting weather information. A METAR weather report is predominantly used by aircraft pilots, and by meteorologists,
who use aggregated METAR information to assist in weather forecasting using the US NOAA service.

**Introduction**
This application uses pure Java, Android Jetpack and Google official libraries only.

# Getting Started
This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.

![Application Stracture](https://photos.app.goo.gl/p8vj2qPbQzDYACYJ8)


# Screenshots
![Favorites Screen (empty)](https://photos.app.goo.gl/GWqj2oK3kH6jurPt9)
![Favorites Screen](https://photos.app.goo.gl/KwfZhsqNU3xBLN747)
![Search screen](https://photos.app.goo.gl/8pxWKK1CoPtmrzET6)
![Searching](https://photos.app.goo.gl/fbDxdSPDa4azQmBZ6)
![Details Screen](https://photos.app.goo.gl/aANxkswQU4irTXK49)

# Used Libraries
[Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.

 - [View Binding](https://developer.android.com/topic/libraries/view-binding)-  is a new view access mechanism that was released in conjunction with Android Studio, In most cases, view binding replaces `findViewById`.
 - [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)  - Create a UI that automatically responds to lifecycle events.
-   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - Build data objects that notify views when the underlying database changes.
-   [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)  - Handle everything needed for in-app navigation.
-   [Room](https://developer.android.com/topic/libraries/architecture/room)  - Access your app's SQLite database with in-app objects and compile-time checks.
-   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
-   [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)  - Manage your Android background jobs.

[UI](): 
 - [activity](https://developer.android.com/jetpack/androidx/releases/activity)- Access composable APIs built on top of Activity.
 - [appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat) -Allows access to new APIs on older API versions of the platform (many using Material Design).
-   [Fragment](https://developer.android.com/guide/components/fragments)  - A basic unit of composable UI.
- [cardview](https://developer.android.com/jetpack/androidx/releases/cardview) - Implement the Material Design card pattern with round corners and drop shadows.

[Dependency injection](https://developer.android.com/training/dependency-injection) - is a technique in which an object receives other objects that it depends on.

 - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)- Hilt is a dependency injection library for Android that extends the functionality of Dagger Hilt to enable dependency injection of certain classes from the androidx libraries, reduces the boilerplate of doing manual dependency injection in the project.
