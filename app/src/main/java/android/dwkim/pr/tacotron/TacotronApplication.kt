package android.dwkim.pr.tacotron

import android.app.Application
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

class TacotronApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val token = FirebaseInstanceId.getInstance().token
        Log.d("debug", "launch token = "+token)
    }
}