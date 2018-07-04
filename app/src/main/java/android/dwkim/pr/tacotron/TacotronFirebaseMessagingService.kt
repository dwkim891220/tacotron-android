package android.dwkim.pr.tacotron

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TacotronFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.notification?.body?.run {
            val message = this
            PlayerUtil().run {
                getSoundFileAndPlay(
                    applicationContext,
                    message
                )
            }
        }
    }
}