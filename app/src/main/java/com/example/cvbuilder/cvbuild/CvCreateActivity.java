package com.example.cvbuilder.cvbuild;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cvbuilder.R;
import com.example.cvbuilder.Utils.Utils;
import com.example.cvbuilder.databinding.ActivityCvCreateBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CvCreateActivity extends AppCompatActivity {
    private ActivityCvCreateBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    HashMap<String, String> hashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCvCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        clickListeners();
    }

    private void clickListeners() {
        binding.upload.setOnClickListener(v -> upLoadData());
    }

    private void upLoadData() {


        String name = binding.nameEd.getText().toString();
        String father_Name = binding.fatherNameEd.getText().toString();
        String eMail_ID = binding.emailIdEd.getText().toString();
        String mobile = binding.mobileEd.getText().toString();
        String summary = binding.summaryEd.getText().toString();
        String degree_Name = binding.degreeNameEd.getText().toString();
        String institute = binding.instituteEd.getText().toString();
        String start_Year = binding.startYearEd.getText().toString();
        String end_Year = binding.endYearEd.getText().toString();
        String company_Name = binding.companyNameEd.getText().toString();
        String joining_Date_Year = binding.joiningDateYearEd.getText().toString();
        String skills_Hobbies = binding.skillsHobbiesEd.getText().toString();
        binding.upload.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        hashMap.put("name", name);
        hashMap.put("father_Name", father_Name);
        hashMap.put("eMail_ID", eMail_ID);
        hashMap.put("mobile", mobile);
        hashMap.put("summary", summary);
        hashMap.put("degree_Name", degree_Name);
        hashMap.put("institute", institute);
        hashMap.put("start_Year", start_Year);
        hashMap.put("company_Name", company_Name);
        hashMap.put("end_Year", end_Year);
        hashMap.put("joining_Date_Year", joining_Date_Year);
        hashMap.put("skills_Hobbies", skills_Hobbies);


// Add a new document with a generated ID

        db.collection("users").document("document").set(hashMap)
                .addOnCompleteListener(task -> {
                    Utils.INSTANCE.showToast("Uploaded SuccessFully", CvCreateActivity.this);
                    binding.upload.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);

                }).addOnFailureListener(e -> {
                    binding.upload.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);
                });
    }

    private void toolBar() {
        binding.toolBar.setTitle(getString(R.string.MakeCv));
        binding.toolBar.setNavigationIcon(R.drawable.back_button);
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(CvCreateActivity.this, R.color.white));
        setSupportActionBar(binding.toolBar);
    }


}