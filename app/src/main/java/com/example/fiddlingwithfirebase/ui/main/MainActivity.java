package com.example.fiddlingwithfirebase.ui.main;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.databinding.ActivityMainBinding;
import com.example.fiddlingwithfirebase.ui.login.LoginActivity;
import com.example.fiddlingwithfirebase.utils.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        binding.logoutBtn.setOnClickListener(v -> {
            viewModel.signOutUser();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getFirebaseUserMutableLiveData().observe(this, currentUser -> {
            if (currentUser != null) {
                binding.messageTv.setText("HELLO " + currentUser.getEmail());
                Log.i(TAG, "User isn't null");
            } else {
                binding.messageTv.setText("USER IS NULL");
                Log.i(TAG, "User is null");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}