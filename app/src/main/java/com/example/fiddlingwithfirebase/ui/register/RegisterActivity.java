package com.example.fiddlingwithfirebase.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.databinding.ActivityRegisterBinding;
import com.example.fiddlingwithfirebase.ui.login.LoginActivity;
import com.example.fiddlingwithfirebase.ui.main.MainActivity;
import com.example.fiddlingwithfirebase.utils.ViewModelFactory;

import java.util.function.Consumer;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(RegisterViewModel.class);

        binding.signupBtn.setOnClickListener(v -> {
            createUser();
        });

        binding.loginBtnRegisterActivity.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        checkForPasswordsMatching();

        viewModel.getIsPasswordAndConfirmPasswordSame().observe(this, isPasswordTheSame -> {
                if (Boolean.FALSE.equals(isPasswordTheSame)) {
                    binding.confirmPasswordET.setError("Please check your password again!");
                } else binding.confirmPasswordET.setError(null);
            }
        );
    }

    private void checkForPasswordsMatching() {
        addTextWatcher(binding.passwordET, viewModel::setPasswordMutableLiveData);
        addTextWatcher(binding.confirmPasswordET, viewModel::setConfirmationPasswordMutableLiveData);
    }

    private void addTextWatcher(EditText editText, Consumer<String> valueSetter) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valueSetter.accept(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createUser() {
        String email = binding.emailET.getText().toString();
        String password = binding.passwordET.getText().toString();
        viewModel.register(email, password);
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }
}