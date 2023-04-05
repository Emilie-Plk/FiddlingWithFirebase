package com.example.fiddlingwithfirebase.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.databinding.ActivityLoginBinding;
import com.example.fiddlingwithfirebase.ui.main.MainActivity;
import com.example.fiddlingwithfirebase.ui.register.RegisterActivity;
import com.example.fiddlingwithfirebase.utils.ViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance())
            .get(LoginViewModel.class);

        loginWithMailAndPassword();

        binding.registerBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginWithMailAndPassword() {
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailET.getText().toString();
            String password = binding.passwordET.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.signIn(email, password);
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }
}