package com.example.cvbuilder

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.Utils.checkForInternet
import com.example.cvbuilder.Utils.showToast
import com.example.cvbuilder.databinding.ActivitySetUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private var binding: ActivitySetUpBinding? = null
    private var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
        toolBar()
        backPressCallBack()
        clickListeners()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, CvScreenActivity::class.java))
        }
    }

    private fun proceedToSingIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, CvScreenActivity::class.java))
                showToast("Login SuccessFully", this@SignInActivity)
                binding?.progressBar?.isVisible = false
            } /*else if (it.result.user?.isEmailVerified == false) {
                showToast("Please Check Your Email")
            }*/ else {
                binding?.progressBar?.isVisible = false
                showToast("some thing went to wrong", this@SignInActivity)
            }
        }

    }


    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.sing_in)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@SignInActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    private fun clickListeners() {
        binding?.apply {
            singUpClick.setOnClickListener {
                startActivity(Intent(this@SignInActivity, SingUpActivity::class.java))
            }
            singInBtn.setOnClickListener {
                val email = textEmailEdt.text
                val psd = textPasswordEdt.text
                if (email.isEmpty() || !email.contains("@")) {
                    showToast("Please Enter Email", this@SignInActivity)
                } else if (psd.isEmpty()) {
                    showToast("Please Enter Password More", this@SignInActivity)
                } else if (psd.length < 6) {
                    showToast("Password Must Be More Then Six Character", this@SignInActivity)
                } else {
                    if (checkForInternet(this@SignInActivity)) {
                        progressBar.isVisible = true
                        proceedToSingIn(email.toString(), psd.toString())
                    } else {
                        showToast("check your internet", this@SignInActivity)

                    }
                }
            }
        }

    }

    private fun backPressCallBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                killApp()
            }
        })
    }

    fun killApp() {
        val appKill = Intent(Intent.ACTION_MAIN)
        appKill.addCategory(Intent.CATEGORY_HOME)
        appKill.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(appKill)
    }


    override fun onSupportNavigateUp(): Boolean {
        killApp()
        return super.onSupportNavigateUp()
    }
}