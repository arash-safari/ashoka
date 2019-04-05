package database;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.objectbox.BoxStore;

public class App extends Application {
    private BoxStore boxStore;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
