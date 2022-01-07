package du.ducs.thoughtboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        Log.d(TAG, "Auth user email: ${auth.currentUser?.email}")
        if (auth.currentUser != null) {
            // TODO(Setup Navigation here)
//            startActivity(Intent(this,MainActivity2::class.java))
            finish()
        } else {
            Log.d(TAG, "Starting Sign-in Process")
            startActivity(Intent(this,FirebaseLoginActivity::class.java))
            finish()
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}