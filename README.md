# METAR
Android METAR is a format for reporting weather information. A METAR weather report is predominantly used by aircraft pilots, and by meteorologists,
who use aggregated METAR information to assist in weather forecasting using the US NOAA service.

**Introduction**
This application uses pure Java, Android Jetpack and Google official libraries only.

# Getting Started
This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.

# Application Architecture
Application implemented based on MVVM pattern and repository pattern.
[MVVM](https://developer.android.com/jetpack/guide#recommended-app-arch): (Model-View-ViewModel) pattern helps to completely separate the business and presentation logic from the UI, and the business logic and UI can be clearly separated for easier testing and easier maintenance.

<img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/architecture.png">

# Used Libraries
[Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.

 - [View Binding](https://developer.android.com/topic/libraries/view-binding)-  is a new view access mechanism that was released in conjunction with Android Studio, In most cases, view binding replaces `findViewById`.
 - [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle)  - Create a UI that automatically responds to lifecycle events.
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - Build data objects that notify views when the underlying database changes.
 - [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)  - Handle everything needed for in-app navigation.
 - [Room](https://developer.android.com/topic/libraries/architecture/room)  - Access your app's SQLite database with in-app objects and compile-time checks.
 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
 - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)  - Manage your Android background jobs.

[UI](): User Interface
 - [activity](https://developer.android.com/jetpack/androidx/releases/activity)- Access composable APIs built on top of Activity.
 - [appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat) -Allows access to new APIs on older API versions of the platform (many using Material Design).
 - [Fragment](https://developer.android.com/guide/components/fragments)  - A basic unit of composable UI.
 - [cardview](https://developer.android.com/jetpack/androidx/releases/cardview) - Implement the Material Design card pattern with round corners and drop shadows.

[Dependency injection](https://developer.android.com/training/dependency-injection) - is a technique in which an object receives other objects that it depends on.

 - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)- Hilt is a dependency injection library for Android that extends the functionality of Dagger Hilt to enable dependency injection of certain classes from the androidx libraries, reduces the boilerplate of doing manual dependency injection in the project.
 
 
# Screenshots
<img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/1.png"> <img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/2.png"> <img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/3.png"> <img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/4.png"> <img src="https://github.com/MahmoudShawky/NOAA_METAR/blob/master/screenshots/5.png">

# APK
  [**Download APK**](https://github.com/MahmoudShawky/METAR/blob/master/screenshots/MS_METAR_1.0.0.apk)

# License
Copyright [2020] [Mahmoud Shawky] Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
