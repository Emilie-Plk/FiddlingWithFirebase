package com.example.fiddlingwithfirebase.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fiddlingwithfirebase.databinding.ActivityRegisterBinding;
import com.example.fiddlingwithfirebase.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.signupBtn.setOnClickListener(v -> {
            createUser();
        });

        binding.loginBtnRegisterActivity.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void createUser() {
        String email = binding.emailET.getText().toString();
        String password = binding.passwordET.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.emailET.setError("Email cannot be empty");
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordET.setError("Email cannot be empty");
        } else {
            binding.emailET.setError(null);
            binding.passwordET.setError(null);

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                "User registered successfully!",
                                Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this,
                                "Registration error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        }
    }
}