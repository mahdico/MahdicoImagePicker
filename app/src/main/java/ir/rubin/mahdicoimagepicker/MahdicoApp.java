package ir.rubin.mahdicoimagepicker;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MahdicoApp extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }
}
