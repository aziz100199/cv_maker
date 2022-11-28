package com.example.cvbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.databinding.ActivityCvScreenBinding

class CvScreenActivity : AppCompatActivity() {
    private var binding: ActivityCvScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cv_screen)
        toolBar()
    }


    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.sing_in)
//            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@CvScreenActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

   /* override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }*/
}