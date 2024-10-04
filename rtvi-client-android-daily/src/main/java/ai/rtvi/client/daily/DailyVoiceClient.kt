package ai.rtvi.client.daily

import ai.rtvi.client.RTVIClient
import ai.rtvi.client.RTVIClientOptions
import ai.rtvi.client.RTVIEventCallbacks
import android.content.Context

/**
 * An RTVI client. Connects to a Daily Bots backend and handles bidirectional audio and video
 * streaming.
 *
 * @param context The Android context object
 * @param callbacks Callbacks invoked when changes occur in the voice session.
 * @param options Additional options for configuring the client and backend.
 */
class DailyVoiceClient(
    context: Context,
    callbacks: RTVIEventCallbacks,
    options: RTVIClientOptions
) : RTVIClient(
    transport = DailyTransport.Factory(context),
    callbacks = callbacks,
    options = options
)