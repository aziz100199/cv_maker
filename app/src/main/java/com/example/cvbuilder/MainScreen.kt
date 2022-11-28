package com.example.cvbuilder

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.cvbuilder.databinding.ActivityMainScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainScreen : AppCompatActivity() {
    private var binding: ActivityMainScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen)
        lifecycleScope.launch {
            getDelay()
                .onStart {
                    binding?.progressBar?.isVisible = true
                }
                .onCompletion {
                    binding?.progressBar?.isVisible = false
                }
                .collect {
                    startActivity(Intent(this@MainScreen, SetUpActivity::class.java))
                    finish()
                }
        }
        backPressed()
    }

    private fun backPressed() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    finish()
                }
            })
    }
    private fun getDelay(): Flow<Int> {
        return flow {
            delay(5000)
            emit(1)
        }
    }
}