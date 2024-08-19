package ai.rtvi.client.daily

import ai.rtvi.client.VoiceClient
import ai.rtvi.client.VoiceClientOptions
import ai.rtvi.client.VoiceEventCallbacks
import android.content.Context

/**
 * An RTVI client. Connects to a Daily Bots backend and handles bidirectional audio and video
 * streaming.
 *
 * @param context The Android context object
 * @param baseUrl URL of the Daily Bots backend.
 * @param callbacks Callbacks invoked when changes occur in the voice session.
 * @param options Additional options for configuring the client and backend.
 */
class DailyVoiceClient(
    context: Context,
    baseUrl: String,
    callbacks: VoiceEventCallbacks,
    options: VoiceClientOptions = VoiceClientOptions()
) : VoiceClient(
    baseUrl = baseUrl,
    transport = DailyTransport.Factory(context),
    callbacks = callbacks,
    options = options
)