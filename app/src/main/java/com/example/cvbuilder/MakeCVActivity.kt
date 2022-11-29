package com.example.cvbuilder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cvbuilder.Utils.showToast
import com.example.cvbuilder.databinding.ActivityMakeCvactivityBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MakeCVActivity : AppCompatActivity() {
    private var binding: ActivityMakeCvactivityBinding? = null
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_cvactivity)

        clickListeners()
        toolBar()
    }

    private fun clickListeners() {
        binding?.upload?.setOnClickListener {
            upLoadData()
        }
    }

    private fun toolBar() {
        binding?.toolBar?.apply {
            title = getString(R.string.MakeCv)
            setNavigationIcon(R.drawable.back_button)
            setTitleTextColor(ContextCompat.getColor(this@MakeCVActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun upLoadData() {

        binding?.apply {
            val name = nameEd.text.toString()
            val father_Name = fatherNameEd.text.toString()
            val eMail_ID = emailIdEd.text.toString()
            val mobile = mobileEd.text.toString()
            val summary = summaryEd.text.toString()
            val degree_Name = degreeNameEd.text.toString()
            val institute = instituteEd.text.toString()
            val start_Year = startYearEd.text.toString()
            val end_Year = endYearEd.text.toString()
            val company_Name = companyNameEd.text.toString()
            val joining_Date_Year = joiningDateYearEd.text.toString()
            val skills_Hobbies = skillsHobbiesEd.text.toString()
            upload.isEnabled = false
            progressBar.isVisible = true
            val user = hashMapOf(
                "name" to name,
                "father_Name" to father_Name,
                "eMail_ID" to eMail_ID,
                "mobile" to mobile,
                "summary" to summary,
                "degree_Name" to degree_Name,
                "institute" to institute,
                "start_Year" to start_Year,
                "end_Year" to end_Year,
                "company_Name" to company_Name,
                "joining_Date_Year" to joining_Date_Year,
                "skills_Hobbies" to skills_Hobbies,
            )
// Add a new document with a generated ID

            db.collection("users").document("document").set(user)
                .addOnSuccessListener { documentReference ->
                    showToast("Uploaded SuccessFully", this@MakeCVActivity);
                    upload.isEnabled = true
                    progressBar.isVisible = false
                    Log.d(
                        "dataBaseStorage",
                        "DocumentSnapshot added with ID: $documentReference"
                    )
                }
                .addOnFailureListener { e ->
                    upload.isEnabled = true
                    progressBar.isVisible = false
                    Log.w("dataBaseStorage", "Error adding document", e)
                }


            /*  db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        showToast("Uploaded SuccessFully", this@MakeCVActivity);
                        upload.isEnabled=true
                        progressBar.isVisible=false
                        Log.d(
                            "dataBaseStorage",
                            "DocumentSnapshot added with ID: ${documentReference.id}"
                        )
                    }
                    .addOnFailureListener { e ->
                        upload.isEnabled=true
                        progressBar.isVisible=false
                        Log.w("dataBaseStorage", "Error adding document", e)
                    }
            }*/
        }
    }
}