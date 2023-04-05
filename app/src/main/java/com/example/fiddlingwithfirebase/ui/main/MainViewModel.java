package com.example.fiddlingwithfirebase.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fiddlingwithfirebase.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {


    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final FirebaseUser currentUser;

    private final AuthRepository repository;


    public MainViewModel(@NonNull AuthRepository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
    }


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void signOutUser() {
        repository.signOut();
    }
}
