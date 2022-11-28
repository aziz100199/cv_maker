package com.example.cvbuilder

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.databinding.ActivitySetUpBinding
import com.google.firebase.auth.FirebaseAuth

class SetUpActivity : AppCompatActivity() {
    private var binding: ActivitySetUpBinding? = null
    private var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
//        connectionDetector()
        toolBar()
        clickListeners()
    }

    private fun proceedToSingUp(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, SetUpActivity::class.java))
                showToast("Account Created SuccessFully")
            } /*else if (it.result.user?.isEmailVerified == false) {
                showToast("Please Check Your Email")
            }*/ else {
                showToast("some thing went to wrong")
            }
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.sing_in)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@SetUpActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    private fun clickListeners() {
        binding?.apply {
            singUpClick.setOnClickListener {
                startActivity(Intent(this@SetUpActivity, SingUpActivity::class.java))
            }
            singInBtn.setOnClickListener {
                val email = textEmailEdt.text
                val psd = textPasswordEdt.text
                if (email.isEmpty()) {
                    showToast("Please Enter Email")
                } else if (psd.isEmpty()) {
                    showToast("Please Enter Password")
                } else {
                    if (checkForInternet(this@SetUpActivity)) {
                        showToast("internetAvailable")
                        proceedToSingUp(email.toString(), psd.toString())
                    } else {
                        showToast("check your internet")
                    }
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}