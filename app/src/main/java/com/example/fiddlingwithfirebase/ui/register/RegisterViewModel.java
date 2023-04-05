package com.example.fiddlingwithfirebase.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fiddlingwithfirebase.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;


    // TODO: is this safe??
    private final MutableLiveData<String> passwordMutableLiveData;
    private final MutableLiveData<String> confirmPassWordMutableLiveData;

    private final MediatorLiveData<Boolean> isPasswordAndConfirmPasswordSame = new MediatorLiveData<>();
    private final AuthRepository repository;

    public RegisterViewModel(@NonNull AuthRepository repository) {
        this.repository = repository;
        this.firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
        passwordMutableLiveData = new MutableLiveData<>();
        confirmPassWordMutableLiveData = new MutableLiveData<>();

        isPasswordAndConfirmPasswordSame.addSource(passwordMutableLiveData, password -> combine(password, confirmPassWordMutableLiveData.getValue()));
        isPasswordAndConfirmPasswordSame.addSource(confirmPassWordMutableLiveData, confirmPassword -> combine(passwordMutableLiveData.getValue(), confirmPassword));
    }

    private void combine(@Nullable String password, @Nullable String confirmPassword) {
        if (password == null) {
            return;
        }
        isPasswordAndConfirmPasswordSame.setValue(password.equals(confirmPassword));
    }


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public LiveData<Boolean> getIsPasswordAndConfirmPasswordSame() {
        return isPasswordAndConfirmPasswordSame;
    }

    public void register(String email, String pass) {
        repository.signUp(email, pass);
    }

    public void setPasswordMutableLiveData(String password) {
        passwordMutableLiveData.setValue(password);
    }

    public void setConfirmationPasswordMutableLiveData(String confirmationPassword) {
        confirmPassWordMutableLiveData.setValue(confirmationPassword);
    }
}
