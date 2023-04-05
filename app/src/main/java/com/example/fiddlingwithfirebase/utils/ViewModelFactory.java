package com.example.fiddlingwithfirebase.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fiddlingwithfirebase.MainApplication;
import com.example.fiddlingwithfirebase.repository.AuthRepository;
import com.example.fiddlingwithfirebase.ui.login.LoginViewModel;
import com.example.fiddlingwithfirebase.ui.main.MainViewModel;
import com.example.fiddlingwithfirebase.ui.register.RegisterActivity;
import com.example.fiddlingwithfirebase.ui.register.RegisterViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final AuthRepository repository;

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }


    public ViewModelFactory() {
        repository = new AuthRepository(MainApplication.getApplication());
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(MainApplication.getApplication(), repository);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(repository);
        } else
            throw new IllegalArgumentException("Unknown model class!");
    }
}
