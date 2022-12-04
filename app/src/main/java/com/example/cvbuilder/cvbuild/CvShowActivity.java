package com.example.cvbuilder.cvbuild;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cvbuilder.R;
import com.example.cvbuilder.Utils.Utils;
import com.example.cvbuilder.databinding.ActivityCvShowBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CvShowActivity extends AppCompatActivity {


    private ActivityCvShowBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCvShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        gettingValue();
    }

    private void gettingValue() {


        binding.progressBar.setVisibility(View.VISIBLE);
        db.collection("users").document("document").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot it) {

                binding.nameTitle.setText(it.getData().get("name").toString());
                binding.nameTv.setText(it.getData().get("name").toString());
                binding.fatherNameTv.setText(it.getData().get("father_Name").toString());
                binding.emailIDTv.setText(it.getData().get("eMail_ID").toString());
                binding.mobileTv.setText(it.getData().get("mobile").toString());
                binding.summaryTv.setText(it.getData().get("summary").toString());
                binding.degreeNameTv.setText(it.getData().get("degree_Name").toString());
                binding.instituteTv.setText(it.getData().get("institute").toString());
                binding.startYearTv.setText(it.getData().get("start_Year").toString());
                binding.endYearTv.setText(it.getData().get("end_Year").toString());
                binding.companyNameTv.setText(it.getData().get("company_Name").toString());
                binding.joiningDateYearTv.setText(it.getData().get("joining_Date_Year").toString());
                binding.skillsHobbiesTv.setText(it.getData().get("skills_Hobbies").toString());
                binding.progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(e -> {
            Utils.INSTANCE.showToast("some thing went to wrong", CvShowActivity.this);
            binding.progressBar.setVisibility(View.GONE);
        });


    }

    private void toolBar() {
        binding.toolBar.setTitle(getString(R.string.Cv));
        binding.toolBar.setNavigationIcon(R.drawable.back_button);
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(CvShowActivity.this, R.color.white));
        setSupportActionBar(binding.toolBar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}