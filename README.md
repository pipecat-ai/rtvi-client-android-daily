# Real-Time Voice Inference Android Client SDK: Daily Transport

[RTVI](https://github.com/rtvi-ai/) is an open standard for Real-Time Voice (and Video) Inference.

This Android library exposes the `DailyVoiceClient` class, to connect to a Daily Bots backend.

## Usage

Add the following dependency to your `build.gradle` file:

```
ai.rtvi:rtvi-client-android-daily:0.1.0
```

Instantiate from your code:

```kotlin
val callbacks = object : VoiceEventCallbacks() {

    override fun onBackendError(message: String) {
        Log.e(TAG, "Error from backend: $message")
    }

    // ...
}

val client = DailyVoiceClient(context, baseUrl, callbacks)

client.start().withCallback {
    // ...
}
```

`client.start()` (and other APIs) return a `Future`, which can give callbacks, or be awaited
using Kotlin Coroutines (`client.start().await()`).