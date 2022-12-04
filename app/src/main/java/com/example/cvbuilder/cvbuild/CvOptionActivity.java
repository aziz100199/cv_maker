package com.example.cvbuilder.cvbuild;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cvbuilder.R;
import com.example.cvbuilder.databinding.ActivityCvOptionBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CvOptionActivity extends AppCompatActivity {


    private ActivityCvOptionBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCvOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        backPressCallBack();
        clickListeners();
    }


    private void clickListeners() {

        binding.logOut.setOnClickListener(v -> {
            auth.signOut();
            if (auth.getCurrentUser() == null) {
                Intent intent = new Intent(CvOptionActivity.this, SignInnActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        binding.makeCv.setOnClickListener(v -> {
            startActivity(new Intent(CvOptionActivity.this, CvCreateActivity.class));

        });
        binding.viewCv.setOnClickListener(v -> {
            startActivity(new Intent(CvOptionActivity.this, CvShowActivity.class));

        });
    }


    private void backPressCallBack() {
        getOnBackPressedDispatcher().addCallback(CvOptionActivity.this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                killApp();
            }
        });
    }

    private void toolBar() {
        binding.toolBar.setTitle(getString(R.string.option));
        binding.toolBar.setNavigationIcon(R.drawable.back_button);
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(CvOptionActivity.this, R.color.white));
        setSupportActionBar(binding.toolBar);
    }

    private void killApp() {
        Intent appKill = new Intent(Intent.ACTION_MAIN);
        appKill.addCategory(Intent.CATEGORY_HOME);
        appKill.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(appKill);
    }

    @Override
    public boolean onSupportNavigateUp() {
        killApp();
        return super.onSupportNavigateUp();
    }
}