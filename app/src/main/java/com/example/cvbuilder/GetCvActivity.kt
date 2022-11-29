package com.example.cvbuilder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.databinding.ActivityGetCvBinding
import com.google.firebase.firestore.FirebaseFirestore

class GetCvActivity : AppCompatActivity() {
    private var binding: ActivityGetCvBinding? = null
    private var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_cv)

        toolBar()
        gettingValue()
    }

    private fun gettingValue() {
        db.collection("users").get().addOnSuccessListener {
            it.forEach { document ->
                Log.d("getData", "${document.id} => ${document.data}")
            }
        }
//        binding?.getDataTv?.text = ""
    }


    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.Cv)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@GetCvActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}