package du.ducs.thoughtboard

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeSignInFragment : Fragment() {

    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null
    private val webClientId: String = BuildConfig.ONE_TAP_WEB_CLIENT_ID

    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
                val idToken = credential?.googleIdToken
                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        val msg = "idToken: $idToken"

                        // Verify token and check if domain is @cs.du.ac.in
                        if (AuthDecoder.decoded(idToken)) {
                            Toast.makeText(
                                activity,
                                "Welcome To DUCS",
                                Toast.LENGTH_SHORT
                            ).show()
                            // send Token to firebase and complete signIn
                            firebaseAuthWithGoogle(idToken)
                        } else {
                            signOut()
                            Toast.makeText(
                                activity,
                                "Please login with University Email-ID",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        Log.d(TAG, msg)
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token!")
                        Toast.makeText(
                            activity,
                            "ID Token Error!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Log.d(TAG, "One-tap dialog was closed.")
                        // Don't re-prompt the user.
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        Log.d(TAG, "One-tap encountered a network error.")
                        // Try again or just ignore.
                        Toast.makeText(
                            activity,
                            "Encountered a Network Error.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Log.d(
                            TAG, "Couldn't get credential from result." +
                                    " (${e.localizedMessage})"
                        )
                        Toast.makeText(
                            activity,
                            "Couldn't get credentials.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private fun signOut() {
        activity?.let {
            oneTapClient?.signOut()
                ?.addOnCompleteListener(it) {
                    Log.d(TAG, "User logged out")
                    Toast.makeText(
                        activity,
                        "Logged Out!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient = activity?.let { Identity.getSignInClient(it) }
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(webClientId)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(webClientId)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()

        val signInBtn: SignInButton? = activity?.findViewById(R.id.signin_btn)
        signInBtn?.setOnClickListener { displaySignIn() }
    }

    private fun displaySignIn() {
        activity?.let {
            oneTapClient?.beginSignIn(signInRequest!!)
                ?.addOnSuccessListener(it) { result ->
                    try {
                        val ib =
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        oneTapResult.launch(ib)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                        Toast.makeText(
                            activity,
                            "Couldn't start One Tap UI.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                ?.addOnFailureListener(it) { e ->
                    // No Google Accounts found. Just continue presenting the signed-out UI.
                    Log.d(TAG, e.localizedMessage!!)
                    val snack = Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "No Valid DUCS Email Found", Snackbar.LENGTH_LONG)
                    snack.setAction("SignUp") {
                        displaySignUp()
                    }
                    snack.show()
                }
        }
    }

    private fun displaySignUp() {
        activity?.let{
            oneTapClient?.beginSignIn(signUpRequest!!)
                ?.addOnSuccessListener(it) { result ->
                    try {
                        val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        oneTapResult.launch(ib)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                        Toast.makeText(
                            activity,
                            "Couldn't start One Tap UI.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                ?.addOnFailureListener(it) { e ->
                    // No Google Accounts found. Just continue presenting the signed-out UI.
                    Toast.makeText(
                        activity,
                        "Failed To Sign Up!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("btn click", e.localizedMessage!!)
                }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val auth = Firebase.auth
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            findNavController().navigate(R.id.action_homeSignInFragment_to_homeScreenFragment)
        } else {
            Toast.makeText(
                activity,
                "Sign In Failed!!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val TAG = "HomeSignInFragment"
    }

}