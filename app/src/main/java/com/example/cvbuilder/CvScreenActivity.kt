package com.example.cvbuilder

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.databinding.ActivityCvScreenBinding
import com.google.firebase.auth.FirebaseAuth

class CvScreenActivity : AppCompatActivity() {
    private var binding: ActivityCvScreenBinding? = null
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cv_screen)
        toolBar()
        clickListeners()
        backPressCallBack()
    }

    private fun backPressCallBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                killApp()
            }
        })
    }

    private fun clickListeners() {
        binding?.apply {
            logOut.setOnClickListener {
                auth.signOut()
                if (auth.currentUser == null) {
                    val intent = Intent(this@CvScreenActivity, SignInActivity::class.java);
                         intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                          Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            }
            makeCv.setOnClickListener {
                startActivity(Intent(this@CvScreenActivity, MakeCVActivity::class.java))
            }
            viewCv.setOnClickListener {
                startActivity(Intent(this@CvScreenActivity, GetCvActivity::class.java))
            }
        }
    }


    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.sing_in)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@CvScreenActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        killApp()
        return super.onSupportNavigateUp()
    }

    fun killApp() {
        val appKill = Intent(Intent.ACTION_MAIN)
        appKill.addCategory(Intent.CATEGORY_HOME)
        appKill.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(appKill)
    }

}