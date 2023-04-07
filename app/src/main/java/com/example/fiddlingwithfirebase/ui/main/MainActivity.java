package com.example.fiddlingwithfirebase.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.R;
import com.example.fiddlingwithfirebase.databinding.ActivityMainBinding;
import com.example.fiddlingwithfirebase.ui.login.LoginActivity;
import com.example.fiddlingwithfirebase.utils.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            binding.messageTv.setText("HELLO " + currentUser.getEmail());
        } else  {
            binding.messageTv.setText("HELLO STRANGER");
        }

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        binding.logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (currentUser != null) {
          //  binding.messageTv.setText(getString(R.string.welcome_tv_main, currentUser.getEmail()));
        } else  binding.messageTv.setText("HELLO STRANGER");
    }
}