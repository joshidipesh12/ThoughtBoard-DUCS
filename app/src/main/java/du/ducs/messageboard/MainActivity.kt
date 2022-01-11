package du.ducs.messageboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val viewModel: MessageViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        // TODO: Switch to using room stored user
        // val user = viewModel.currentUser
        // Log.d(TAG, "Auth user email: ${user?.email}")

        // if (user != null) {
        //     val navHostFragment = supportFragmentManager
        //         .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //     navController = navHostFragment.navController

        //     // FIXME: Decide how to setup navController with the activity's non-existent action bar.
        if (actionBar!=null) {
            setupActionBarWithNavController(navController)
        }
        val auth = Firebase.auth
        Log.d(TAG, "Auth user email: ${auth.currentUser?.email}")
        if (auth.currentUser != null) {
            navController.navigate(R.id.action_loadingScreenFragment_to_homeScreenFragment)
        } else {
            Log.d(TAG, "Starting Sign-in Process")
            navController.navigate(R.id.action_loadingScreenFragment_to_homeSignInFragment)
        }
    }

    //To handle the working of Up Buttons
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}