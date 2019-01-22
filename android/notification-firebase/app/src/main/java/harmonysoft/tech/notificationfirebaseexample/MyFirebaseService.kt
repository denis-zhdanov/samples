package harmonysoft.tech.notificationfirebaseexample

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * @author Denis Zhdanov
 * @since 22/1/19
 */
class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(this, message.data.toString(), Toast.LENGTH_LONG).show()
        }
    }
}