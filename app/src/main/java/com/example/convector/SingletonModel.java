package com.example.convector;

import android.app.Application;

public class SingletonModel extends Application {
    public static SingletonModel instance;

    private ModelComponent modelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        modelComponent = new ModelComponent();
    }

    public static SingletonModel getInstance() {
        return instance;
    }

    public ModelComponent getModel() {
        return modelComponent;
    }
}
