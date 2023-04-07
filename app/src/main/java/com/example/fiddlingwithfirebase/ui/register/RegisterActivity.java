package com.example.fiddlingwithfirebase.ui.register;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.databinding.ActivityRegisterBinding;
import com.example.fiddlingwithfirebase.ui.login.LoginActivity;
import com.example.fiddlingwithfirebase.ui.main.MainActivity;
import com.example.fiddlingwithfirebase.utils.ViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Consumer;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private RegisterViewModel viewModel;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

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
        mAuth.createUserWithEmailAndPassword(binding.emailET.getText().toString(), binding.passwordET.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser newUser = mAuth.getCurrentUser();
                        String userEmail = newUser.getEmail();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        binding.passwordET.setError(task.getException().getMessage());
                    }
                }
            });

    }
}