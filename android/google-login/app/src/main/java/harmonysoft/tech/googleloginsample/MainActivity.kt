package harmonysoft.tech.googleloginsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import com.google.android.gms.common.api.ApiException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val clientId = "908734912557-qju23sussct1brar6rsqgr7bgn1qism4.apps.googleusercontent.com"
        button.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(clientId)
                .requestServerAuthCode(clientId)
                .build()
            val client = GoogleSignIn.getClient(this, gso)
            val signInIntent = client.getSignInIntent()
            startActivityForResult(signInIntent, 42)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = GoogleSignIn.getSignedInAccountFromIntent(data);
        val result = task.getResult(ApiException::class.java)
        println("server auth code: " + result?.serverAuthCode)
        println("id token: " + result?.idToken)
        println("auth code: " + result?.idToken)
    }
}
