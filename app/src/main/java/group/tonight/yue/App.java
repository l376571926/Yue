package group.tonight.yue;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserDatabase.getInstance(this);
        Stetho.initializeWithDefaults(this);
    }
}
