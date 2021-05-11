# Axeptio SDK

## Introduction

User consent is not only limited to the Web but applies to all platforms collecting user data. Mobile devices are part of it.

## Installation

### Axeptio SDK is available on [Maven Central](https://search.maven.org/search?q=eu.axeptio:android-sdk)
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
    implementation 'eu.axeptio:android-sdk:0.2.0'
}
```

## Getting started

### Kotlin

In the main `Activity` of your app, initialize the SDK by calling the `initialize` method providing a `clientId` and a `version`. Once the initialization is completed, you can make the widget appear by calling the `showConsentView` method.

*MainActivity.kt*
```kotlin
import eu.axeptio.sdk.Axeptio
import eu.axeptio.sdk.axeptio

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)
        val rootView = findViewById(R.id.rootView)

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

### initialize

The `initialize` function initializes the SDK by fetching the configuration and calling the completion handler when done. If it fails (because of network for example) it is OK to call the `initialize` function again.

```kotlin
fun initialize(clientId: String, version: String, completionHandler: Axeptio.CompletionHandler)
```

### showConsentView

The `showConsentView` function shows Axeptio's widget to the user in a given `View` and calls the completion handler when the user has made his choices. If `onlyFirstTime` is true and the user has already made his choices in a previous call the widget is not shown and the completion is called immediately. However if the configuration includes new vendors then the widget is shown again. You can specify an `initialStepIndex` greater than 0 to show a different step directly.

Axeptio will try and find a parent view to hold Axeptio view from the value given to view. Axeptio will walk up the view tree trying to find a suitable parent, which is defined as a `CoordinatorLayout` or the window decor's content view, whichever comes first.

`singleTop` can be used to insure that the last call to `showConsentView` will hide (remove from parent) every previously displayed `consentView`.

```kotlin
fun showConsentView(initialStepIndex: Int = 0, onlyFirstTime: Boolean = true, singleTop: Boolean = true, view: View, completionHandler: Axeptio.CompletionHandler): (() → Unit)?
```

If the widget is shown the function returns a dismiss handler that you can call to hide the widget should you need it. Otherwise returns null.

### getUserConsent

The `getUserConsent` function returns an optional boolean indicating if the user has made his choice for given vendor and whether or not he gave his consent. If the returned value is `null` it either means the vendor was not present in the configuration or the widget has not been presented to the user yet.

```kotlin
fun getUserConsent(vendorName: String): Boolean?
```

## Author

Axeptio

## License

AxeptioSDK is available under the MIT license. See the LICENSE file for more info.
