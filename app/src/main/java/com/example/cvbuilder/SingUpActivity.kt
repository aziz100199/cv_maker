package com.example.cvbuilder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.Utils.showToast
import com.example.cvbuilder.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth

class SingUpActivity : AppCompatActivity() {
    private var binding: ActivitySingUpBinding? = null
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sing_up)

        toolBar()
        clickListeners()
    }

    private fun clickListeners() {
        binding?.apply {
            signUpBtn.setOnClickListener {
                val email = textEmailEdt.text.toString()
                val psd = textPasswordEdt.text.toString()
                if (email.isEmpty() || !email.contains("@")) {
                    showToast("Please Enter Email", this@SingUpActivity)
                } else if (psd.isEmpty()) {
                    showToast("Please Enter Password More", this@SingUpActivity)
                } else if (psd.length < 6) {
                    showToast("Password Must Be More Then Six Character", this@SingUpActivity)
                } else {
                    if (Utils.checkForInternet(this@SingUpActivity)) {
                        showToast("internetAvailable", this@SingUpActivity)
                        binding?.progressBar?.isVisible=true
                        proceedToSingUp(email, psd)
                    } else {
                        showToast("check your internet", this@SingUpActivity)
                    }
                }
            }
        }
    }

    private fun proceedToSingUp(email: String, psd: String) {
        auth.createUserWithEmailAndPassword(email, psd).addOnCompleteListener {
            if (it.isSuccessful) {
                binding?.progressBar?.isVisible=false
                startActivity(Intent(this, SetUpActivity::class.java))
            } else {
                binding?.progressBar?.isVisible=false
                showToast("some thing went to wrong", this)
            }
        }
    }

    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.sing_up)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@SingUpActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}