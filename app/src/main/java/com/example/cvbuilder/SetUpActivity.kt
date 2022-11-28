package com.example.cvbuilder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.Utils.checkForInternet
import com.example.cvbuilder.Utils.showToast
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

    private fun proceedToSingIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, CvScreenActivity::class.java))
                showToast("Login SuccessFully", this@SetUpActivity)
                binding?.progressBar?.isVisible=false
            } /*else if (it.result.user?.isEmailVerified == false) {
                showToast("Please Check Your Email")
            }*/ else {
                binding?.progressBar?.isVisible=false
                showToast("some thing went to wrong", this@SetUpActivity)
            }
        }

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
                if (email.isEmpty() || !email.contains("@")) {
                    showToast("Please Enter Email", this@SetUpActivity)
                } else if (psd.isEmpty()) {
                    showToast("Please Enter Password More", this@SetUpActivity)
                } else if (psd.length < 6) {
                    showToast("Password Must Be More Then Six Character", this@SetUpActivity)
                } else {
                    if (checkForInternet(this@SetUpActivity)) {
                        showToast("internetAvailable", this@SetUpActivity)
                        progressBar.isVisible=true
                        proceedToSingIn(email.toString(), psd.toString())
                    } else {
                        showToast("check your internet", this@SetUpActivity)

                    }
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}