package com.example.cvbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.Utils.showToast
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

        binding?.apply {
            progressBar.isVisible = true
            db.collection("users").document("document").get().addOnSuccessListener {
                nameTitle.text = it.data?.get("name").toString()
                nameTv.text = it.data?.get("name").toString()
                fatherNameTv.text = it.data?.get("father_Name").toString()
                emailIDTv.text = it.data?.get("eMail_ID").toString()
                mobileTv.text = it.data?.get("mobile").toString()
                summaryTv.text = it.data?.get("summary").toString()
                degreeNameTv.text = it.data?.get("degree_Name").toString()
                instituteTv.text = it.data?.get("institute").toString()
                startYearTv.text = it.data?.get("start_Year").toString()
                endYearTv.text = it.data?.get("end_Year").toString()
                companyNameTv.text = it.data?.get("company_Name").toString()
                joiningDateYearTv.text = it.data?.get("joining_Date_Year").toString()
                skillsHobbiesTv.text = it.data?.get("skills_Hobbies").toString()
                progressBar.isVisible = false
            }.addOnFailureListener {
                showToast("some thing went to wrong", this@GetCvActivity)
                progressBar.isVisible = false
            }


        }
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