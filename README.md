# GithubApiDemo

This project is an android whitch provides some information about github repositories.

## Demo videos

Search Repository	|
:------:|
![](demo/demo.gif)  | 


## Architecture

The architecture of the application is based, apply and strictly complies with each of the following 5 points:
-   A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage fragment operations.
-   [Android architecture components](https://developer.android.com/jetpack/guide), part of Android Jetpack for give to project a robust design, testable and maintainable.
-   Pattern  [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)  (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface.
-   [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID)  design principles intended to make software designs more understandable, flexible and maintainable.

## Third-party libraries
Min API level is set to 21, so the presented approach is suitable for over 94% of devices running Android. This project takes advantage of many popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version unless there is a good reason to use non-stable dependency.
-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html)  - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx)  - major improvement to the original Android  [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [View Binding](https://developer.android.com/topic/libraries/view-binding)  - allows you to more easily write code that interacts with views/
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)  - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Navigation](https://developer.android.com/guide/navigation/)  - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Splash screens](https://developer.android.com/guide/topics/ui/splash-screen) - This API enables a new app launch animation for all apps when running on a device with Android 12 or higher
    -   [Room](https://developer.android.com/topic/libraries/architecture/room)  - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - managing background threads with simplified code and reducing needs for callbacks.
-    [Coroutines Flow](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - cold asynchronous data stream that sequentially emits values and completes normally or with an exception
-   [Dagger2](https://dagger.dev/)  - dependency injector for replacement all Factory classes.
-   [Retrofit](https://square.github.io/retrofit/)  - type-safe HTTP client.
-   [Glide](https://github.com/bumptech/glide)  - image loading and caching library

## Environment Setup
First off, you require the latest Android Studio 4.0.1 (or newer) to be able to build the app.

Application uses Github API. To use Application you should add base URL in the `gradle.properties` project root file:

```properties
#Github Base URL
GITHUB_BASE_URL = "base URL"
```
For API You need to supply API key to displayed repositories in the app. You can find information about how to gain access via this link - [Github API](https://docs.github.com/en/rest/guides/getting-started-with-the-rest-api) 

When you obtain the Github Token, first you should create a new folder, **cpp**, inside **app/src/main**. Then right-click on the cpp folder, click on New → C/C++ Source File, and name your file lib.cpp.

Also don't forget to install **Sdk Tools** : Cmake, NDK

Inside your **lib.cpp**, add the following code:
```c++
#include <jni.h>
#include <string>

using namespace std;
extern "C" JNIEXPORT jstring JNICALL
Java_ge_example_githubapidemo_utils_Keys_githubToken(JNIEnv *env, jobject thiz) {
    std::string github_token = " Github Token ";
    return env->NewStringUTF(github_token.c_str());
}
```
