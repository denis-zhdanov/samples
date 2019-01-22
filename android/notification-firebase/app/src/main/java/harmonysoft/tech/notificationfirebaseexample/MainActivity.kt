package harmonysoft.tech.notificationfirebaseexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.text)
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            it.result?.token?.let { token ->
                Log.i("logger", "FCM token is $token")
                text.append(token)
                text.append("\n")
            }
        }
    }
}
