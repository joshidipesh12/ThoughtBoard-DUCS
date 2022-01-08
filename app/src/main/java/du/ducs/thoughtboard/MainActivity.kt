package du.ducs.thoughtboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController= navHostFragment.navController
        //This ensures action bar (app bar) buttons
        setupActionBarWithNavController(navController)

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
    //To handle the working of Up Buttons
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        val TAG = "MainActivity"
    }
}