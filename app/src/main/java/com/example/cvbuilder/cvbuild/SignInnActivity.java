package com.example.cvbuilder.cvbuild;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cvbuilder.R;
import com.example.cvbuilder.Utils.Utils;
import com.example.cvbuilder.databinding.ActivitySignInnBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInnActivity extends AppCompatActivity {

    private ActivitySignInnBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        backPressCallBack();
        clickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(SignInnActivity.this, CvOptionActivity.class));
        }
    }

    private void clickListeners() {

        binding.singUpClick.setOnClickListener(v ->
                startActivity(new Intent(SignInnActivity.this, SignnUpActivity.class)));

        binding.singInBtn.setOnClickListener(v -> {
            String email = binding.textEmailEdt.getText().toString();
            String psd = binding.textPasswordEdt.getText().toString();

            if (email.isEmpty() || !email.contains("@")) {
                Utils.INSTANCE.showToast("Please Enter Email", SignInnActivity.this);
            } else if (psd.isEmpty()) {
                Utils.INSTANCE.showToast("Please Enter Password More", SignInnActivity.this);
            } else if (psd.length() < 6) {
                Utils.INSTANCE.showToast("Password Must Be More Then Six Character", SignInnActivity.this);
            } else {
                if (Utils.INSTANCE.checkForInternet(SignInnActivity.this)) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    proceedToSingIn(email, psd);
                } else {
                    Utils.INSTANCE.showToast("check your internet", SignInnActivity.this);

                }
            }
        });
    }

    private void proceedToSingIn(String email, String password) {


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                startActivity(new Intent(SignInnActivity.this, CvOptionActivity.class));
                Utils.INSTANCE.showToast("Login SuccessFully", SignInnActivity.this);
                binding.progressBar.setVisibility(View.GONE);
            } /*else if (it.result.user?.isEmailVerified == false) {
            showToast("Please Check Your Email")
        }*/ else {
                binding.progressBar.setVisibility(View.GONE);
                Utils.INSTANCE.showToast("some thing went to wrong", SignInnActivity.this);
            }

        });


    }


    private void backPressCallBack() {
        getOnBackPressedDispatcher().addCallback(SignInnActivity.this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                killApp();
            }
        });
    }

    private void killApp() {
        Intent appKill = new Intent(Intent.ACTION_MAIN);
        appKill.addCategory(Intent.CATEGORY_HOME);
        appKill.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(appKill);
    }

    private void toolBar() {
        binding.toolBar.setTitle(getString(R.string.sing_in));
        binding.toolBar.setNavigationIcon(R.drawable.back_button);
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(SignInnActivity.this, R.color.white));
        setSupportActionBar(binding.toolBar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        killApp();
        return super.onSupportNavigateUp();
    }

}