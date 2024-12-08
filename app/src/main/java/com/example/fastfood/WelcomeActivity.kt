package com.example.fastfood

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class WelcomeActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var buttonStartWithEmail : Button
    private lateinit var textviewSignIn :TextView
    private lateinit var buttonFb:Button

    private lateinit var callbackManager: CallbackManager

    private lateinit var googleSignInButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        FacebookSdk.sdkInitialize(getApplicationContext());

        auth = Firebase.auth

        // Initialize Facebook SDK
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this.application)

        googleSignInButton=findViewById(R.id.buttongoogle)
        buttonStartWithEmail=findViewById(R.id.buttonStartWithEmail)
        textviewSignIn=findViewById(R.id.textViewLogin)
        buttonFb=findViewById(R.id.buttonFB)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        buttonStartWithEmail.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity,SignUpActivity::class.java))
        }

        textviewSignIn.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity,LoginActivity::class.java))
        }

        callbackManager = CallbackManager.Factory.create()

        buttonFb.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this, listOf("email", "public_profile"))
        }
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Toast.makeText(this@WelcomeActivity, "Facebook Login Cancelled", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@WelcomeActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Facebook Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun signInWithGoogle()
    {
        val signInClient = googleSignInClient.signInIntent
        launcher.launch(signInClient)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result->
        if(result.resultCode== Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }
    private fun handleResult(task: Task<GoogleSignInAccount>)
    {
        val account : GoogleSignInAccount? = task.result
        if(account!=null)
        {
            updateUI(account)
        }
        else{
            Toast.makeText(this , "Sign In failed Try Again Later",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {

        val credential =GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
               startActivity(Intent(this, MainActivity::class.java))
               finish()
            }
            else{
                 Toast.makeText(this,"Can't Login currently. Try after sometime",Toast.LENGTH_SHORT).show()
            }
        }
    }

}