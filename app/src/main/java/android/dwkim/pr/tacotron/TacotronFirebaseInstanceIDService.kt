package android.dwkim.pr.tacotron

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class TacotronFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()

        Log.d("debug", "refreshed Token = " + FirebaseInstanceId.getInstance().token)
    }
}