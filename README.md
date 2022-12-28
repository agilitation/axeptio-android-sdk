# Axeptio SDK @ 0.4.1

## Introduction

User consent is not only limited to the Web but applies to all platforms collecting user data. Mobile devices are part of it.

## Requirements

- Minimum Android version: **Lollipop (5.x)**
- Android Studio Dolphin

#### **note**

_we previously wrote in this readme_

_Nougat , 7.x that is API Level 24_

_The source code was using actually still using API Level 21, and, to work on React Native compatibility, it appears that we need API level 21 that is Android 5 lollipop so..._

_Though, in future releases , we will synchronise better this in between all possible clients the SDK needs to support and google requirements for android OS version support_


## Author

Axeptio

## License

AxeptioSDK is available under the MIT license. See the LICENSE file for more info.

## Improvements

#### **0.4.1**

- with this version we sync version for both android an iOS version
- Minimum Android version: **Lollipop (5.x)**
- Api Level 21
- target SDK 33
- Build Tools 33.0.0
- tools:
  - Gradle 7.3.0 and more
  - AGP 7.4  and more
  - Android Studio Dolphin 
 - test app in 0.4.2
- fixes

## What's Changed
* [DEV-2395] - Layout management by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/1
* [DEV-2428] - Rerere by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/2
* [fixes DEV-2320]-info layout options management bug by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/3
* [fixes DEV-2587] - info layout external width and shift by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/4
* [DEV-2589] - Code patrol from ANdroid Studio Inspect code by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/5
* [fixes DEV-2588] - increase button size to improve touchability by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/6
* [fixes DEV-2594] - increase text  and icons sizes for vendors and but… by @pplaquette in https://github.com/axeptio/axeptio-android-sdk-sources/pull/7

 

#### **0.4.0**

- with this version we sync version for both android an iOS version
- Minimum Android version: **Lollipop (5.x)**
- Api Level 21
- target SDK 33
- Build Tools 33.0.0
- tools:
  - Gradle 7.3.0 and more
  - AGP 7.4  and more
  - Android Studio Dolphin 
  fixes
  - add support for  info layout


####  **0.2.2**
- Minimum Android version: **Lollipop (5.x)**
- Api Level 21
- **tools**
  - Gradle 7.2.2 and more
  - AGP 7.3.3  and more
  - Android Studio Chipmunk 
- **fixes**
  - [UX Improvements] - reduce left and right horizontal insets to provide a better width
  - [fixes AXE-1601] - in cookie, vendor, the domain turns out to be optional and not mandatory
  
####  **0.2.1**
- Minimum Android version: **Lollipop (5.x)**
- Api Level 21



## Installation

### Axeptio SDK is available on [Maven Central](https://search.maven.org/artifact/eu.axeptio/android-sdk)
[![Maven Central](https://img.shields.io/maven-central/v/eu.axeptio/android-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22eu.axeptio%22%20AND%20a:%22android-sdk%22)

1. Add the Maven Central repository to your project **root build.gradle** file:

*/build.gradle*
```gradle
allprojects {
    repositories {
        ...
        mavenCentral()
    }
}
```

2. Add the dependency to the **app build.gradle** file:

*/app/build.gradle*
```gradle
dependencies {
    implementation 'eu.axeptio:android-sdk:0.4.0'
}
```

## Getting started

### Kotlin

In the main `Activity` of your app, initialize the SDK by calling the `initialize` method providing a `clientId` and a `version`. Once the initialization is completed, you can make the widget appear by calling the `showConsentView` method.

*MainActivity.kt*
```kotlin
import eu.axeptio.sdk.Axeptio

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)
        val rootView = findViewById(R.id.rootView)
        
        // Set a custom token
        // axeptio.token = "auto-generated-token-xxx"

        axeptio.initialize("<Client ID>", "<Version>") { initError ->
            // Handle error
            // You could try to initialize again after some delay for example
            axeptio.showConsentView(view = this.rootView) { consentError ->
                // User has made his choices
                // We can now enable or disable the collection of metrics of the analytics library
                if (axeptio.getUserConsent("<Vendor name>")!!) {
                    // Disable collection
                } else {
                    // Enable collection
                }
            }
        }
    }
}
```

If your app supports multiple languages you probably have created a different version for each of the language in Axeptio's [admin web page](https://admin.axeptio.eu). In this case you can store the version for each language in `strings.xml` file and use `Context.getString()` to get the appropriate version for the user.

### Java

*MainActivity.java*
```java
import eu.axeptio.sdk.Axeptio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        View rootView = (View) findViewById(R.id.rootView);

        Axeptio axeptio = Axeptio.instance(this);
        
        // Set a custom token
        // axeptio.setToken("auto-generated-token-xxx");
        
        axeptio.initialize("<Client ID>", "<Version>", initError -> {
            // Handle error
            // You could try to initialize again after some delay for example
            axeptio.showConsentView(rootView, consentError -> {
                    // User has made his choices
                    // We can now enable or disable the collection of metrics of the analytics library
                    if (axeptio.getUserConsent("<Vendor name>")) {
                        // Disable collection
                    } else {
                        // Enable collection
                    }
            });
        });
    }
}
```

## API Reference

### Properties

#### token (Kotlin only)

The `token` property can be used to set a custom token. By default, a random identifier is set.

This property is particularly useful for apps using webviews. By opening the webview while passing the token in the `axeptio_token` querystring parameter, the consent previously given in the app will be reused on the website if it uses the web SDK.

### Methods

#### getToken (Java only)

The `getToken` method return the value of the token.

#### getUserConsent

The `getUserConsent` function returns an optional boolean indicating if the user has made his choice for given vendor and whether or not he gave his consent. If the returned value is `null` it either means the vendor was not present in the configuration or the widget has not been presented to the user yet.

```kotlin
fun getUserConsent(vendorName: String): Boolean?
```

#### initialize

The `initialize` function initializes the SDK by fetching the configuration and calling the completion handler when finished. 

If this fails, because of the network for example, it is possible to call the `initialize` function again, unless the error is Already Initialized.

If you need to reset the Axeptio SDK for a different project id for the same client id or change both you should call the revere function

```kotlin
fun initialize(clientId: String, version: String, completionHandler: Axeptio.CompletionHandler)
```

#### rerere (reset)

The `rerere` function resets the SDK by fetching the configuration and calling the completion handler when finished in the same way as `initialize` function described above.

The main difference is that the call to `rerere` first releases everything that has been loaded for the current Client Id and Project Id, then reloads the data for the new Client Id and Project Id. 

Most of the time this should be related to using another version of the project for the same Client Id - both can change.

The name `rerere` comes from the `git rerere` function involved in resolving the same conflicts over and over again until the subject branches are done.

```kotlin
fun rerere(clientId: String, version: String, completionHandler: Axeptio.CompletionHandler)
```

#### setToken (Java only)

The `setToken` method allows to set a custom value for the token.

#### showConsentView

The `showConsentView` function shows Axeptio's widget to the user in a given `View` and calls the completion handler when the user has made his choices. If `onlyFirstTime` is true and the user has already made his choices in a previous call the widget is not shown and the completion is called immediately. However if the configuration includes new vendors then the widget is shown again. You can specify an `initialStepIndex` greater than 0 to show a different step directly.

Axeptio will try and find a parent view to hold Axeptio view from the value given to view. Axeptio will walk up the view tree trying to find a suitable parent, which is defined as a `CoordinatorLayout` or the window decor's content view, whichever comes first.

`singleTop` can be used to insure that the last call to `showConsentView` will hide (remove from parent) every previously displayed `consentView`.

```kotlin
fun showConsentView(initialStepIndex: Int = 0, onlyFirstTime: Boolean = true, singleTop: Boolean = true, view: View, completionHandler: Axeptio.CompletionHandler): (() → Unit)?
```

If the widget is shown the function returns a dismiss handler that you can call to hide the widget should you need it. Otherwise returns null.

