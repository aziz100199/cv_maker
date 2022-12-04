package com.example.cvbuilder.cvbuild;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cvbuilder.R;
import com.example.cvbuilder.Utils.Utils;
import com.example.cvbuilder.databinding.ActivitySignnUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignnUpActivity extends AppCompatActivity {
    private ActivitySignnUpBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignnUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        clickListeners();
    }


    private void clickListeners() {
        binding.signUpBtn.setOnClickListener(v -> {
            String email = binding.textEmailEdt.getText().toString();
            String psd = binding.textPasswordEdt.getText().toString();
            if (email.isEmpty() || !email.contains("@")) {
                Utils.INSTANCE.showToast("Please Enter Email", SignnUpActivity.this);
            } else if (psd.isEmpty()) {
                Utils.INSTANCE.showToast("Please Enter Password More", SignnUpActivity.this);
            } else if (psd.length() < 6) {
                Utils.INSTANCE.showToast("Password Must Be More Then Six Character", SignnUpActivity.this);
            } else {
                if (Utils.INSTANCE.checkForInternet(SignnUpActivity.this)) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    proceedToSingUp(email, psd);
                } else {
                    Utils.INSTANCE.showToast("check your internet", SignnUpActivity.this);

                }
            }
        });

    }

    private void proceedToSingUp(String email, String psd) {

        auth.createUserWithEmailAndPassword(email, psd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SignnUpActivity.this, SignInnActivity.class));
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Utils.INSTANCE.showToast("some thing went to wrong", SignnUpActivity.this);
                }


            }
        });

    }


    private void toolBar() {
        binding.toolBar.setTitle(getString(R.string.sing_up));
        binding.toolBar.setNavigationIcon(R.drawable.back_button);
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(SignnUpActivity.this, R.color.white));
        setSupportActionBar(binding.toolBar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}