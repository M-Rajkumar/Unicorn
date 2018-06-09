package com.unicornwings.unicornwings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    AppCompatActivity mActivty = null;
    String TAG = "SplashActivity";
    Handler handler = null;
    ImageView iv_unicorn_wings;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_unicorn_wings = (ImageView) findViewById(R.id.iv_unicorn_wings);
        mActivty = this;
        loadsplash();
    }


    public void loadsplash() {
        iv_unicorn_wings.getLayoutParams().width = getscreenwidth();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

           /*     if (pref.getString(""+Constants.ACCESS_TOKEN, null) != null&&pref.getInt(""+Constants.Profile_not_Completed, 0)==3&&pref.getInt(""+Constants.Add_Vehicle_Not_Completed, 0)==3 ) {
                    Intent intent = new Intent(mActivty, LauncherActivity.class);
                    //Intent intent = new Intent(mActivty, ProfileActivity.class);
                    startActivity(intent);
                    mActivty.finish();
                } else {
                    Intent intent = new Intent(mActivty, LoginActivity.class);
                    //Intent intent = new Intent(mActivty, ProfileActivity.class);
                    startActivity(intent);
                    mActivty.finish();
                }*/

                Intent intent = new Intent(mActivty, MainActivity.class);
                //Intent intent = new Intent(mActivty, ProfileActivity.class);
                startActivity(intent);
                mActivty.finish();

            }
        }, 3000);
    }

    private int getscreenwidth() {

        int width = 0, Screen_width = 0;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int Screen_height = displayMetrics.heightPixels;
            Screen_width = displayMetrics.widthPixels;
            width = (int) Math.round(Screen_width / 1.2);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;

    }
}
