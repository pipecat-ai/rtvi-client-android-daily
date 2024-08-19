package ai.rtvi.client.daily

import ai.rtvi.client.utils.ThreadRef

internal class AudioLevelProcessor(
    private val thread: ThreadRef,
    private val onIsSpeaking: (Boolean) -> Unit,
    private val threshold: Float = 0.05f,
    private val silenceDelayMs: Long = 750,
) {
    private var speaking = false

    private val silenceAction = Runnable {
        speaking = false
        silencePending = null
        onIsSpeaking(false)
    }

    private var silencePending: Runnable? = null

    fun onLevelChanged(level: Float) {
        thread.assertCurrent()

        if (level > threshold) {
            if (silencePending != null) {
                thread.handler.removeCallbacks(silenceAction)
                silencePending = null
            }
            if (!speaking) {
                speaking = true;
                onIsSpeaking(true)
            }
        } else if (speaking && silencePending == null) {
            silencePending = silenceAction
            thread.handler.postDelayed(silenceAction, silenceDelayMs)
        }
    }
}