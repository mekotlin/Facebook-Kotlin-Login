package mekotlinapps.dnyaneshwar.`in`.facebooklogin

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton.setReadPermissions("public_profile")
        generateHashKey()

        if (AccessToken.getCurrentAccessToken() == null) {
            tvMsg.setText("Welcome to the facebook login demo by Dnyaneshwar")
        } else {

            /*if your logged in then you will get access token here*/
            val accessToken = AccessToken.getCurrentAccessToken()
        }

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onError(error: FacebookException?) {
            }

            override fun onSuccess(result: LoginResult?) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    val profile = Profile.getCurrentProfile()
                    if (profile != null) {
                        val name = profile.name
                        tvMsg?.setText("Thank you $name")
                        toast("Thank you $name")
                    }

                }
            }

            override fun onCancel() {
            }
        })
    }

    /*generateHashKey gives you hash key to save facebook dev side*/
    fun generateHashKey() {
        val info = packageManager.getPackageInfo("Your Package Name", PackageManager.GET_SIGNATURES)

        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.i("HashKey :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
