# Nasa APOD (Astronomy Picture of the Day)
A mobile app that helps with displaying NASA’s Astronomy picture of the day


Features:
* Search for the astronomy pictures or videos for a date of your choice
* Create & manage list of your favorite astronomy pictures
* Cached information in case of network unavailability.
* Dark mode support
* Support for different screen sizes & orientations


Introduction:

The application uses Clean Architecture based on MVVM and Repository patterns. Implemented
Architecture principles follow Google recommended [Guide to app architecture](https://developer.android.com/jetpack/docs/guide).

![Guide to app architecture](guide-to-app-architecture.png "Guide to app architecture")

The application is written entirely in Kotlin.

Android Jetpack is used as an Architecture glue including but not limited to ViewModel, LiveData,
Lifecycles, Room and Data Binding.

The application does network HTTP requests via Retrofit, OkHttp and GSON. Loaded data is saved to
Retrofit chaching.

Kotlin Coroutines manage background threads with simplified code and reducing needs for callbacks.

Frescro is used for image loading.



#APIs used to fetch data: 

https://api.nasa.gov/

In this application, Nasa's open source api is used to display astronomy pictures and videos.

Detailed documentation for APIs click here: https://api.nasa.gov/#browseAPI
