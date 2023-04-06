package com.example.fiddlingwithfirebase.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fiddlingwithfirebase.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final FirebaseUser currentUser;
    private final AuthRepository repository;

    public LoginViewModel(@NonNull Application application, AuthRepository repository) {
        super(application);

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

    public void signIn(String email, String pass) {
        repository.signIn(email, pass);
    }

/*    public void signInWithGoogle() {
          repository.sigInGoogle();
    }*/
}
