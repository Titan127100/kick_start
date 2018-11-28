package com.example.joshua.kickstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

public class LoadingScreenActivity extends AppCompatActivity {

    private TextView title;
    Animation topPart;
    Animation bottomPart;
    SpinKitView spinner1;
    ConstraintLayout top, bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // initialise the variables
        top = (ConstraintLayout) findViewById(R.id.topPart);
        bottom = (ConstraintLayout) findViewById(R.id.bottomPart);
        title =(TextView) findViewById(R.id.appName);
        spinner1 = (SpinKitView) findViewById(R.id.spin_kit);

        // bringing external font using Typeface
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/dry_brush.ttf");
        title.setTypeface(custom_font);
        TextView appName = (TextView) findViewById(R.id.clubName);
        appName.setTypeface(custom_font);

        // setting up animations
        topPart = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        top.setAnimation(topPart);
        bottomPart = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        bottom.setAnimation(bottomPart);

        //setting handlers for spinner
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner1.setVisibility(View.VISIBLE);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner1.setVisibility(View.GONE);
            }
        }, 5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyOpened = prefs.getBoolean("First Time", false);
                if(!previouslyOpened){
                    Intent intent = new Intent(LoadingScreenActivity.this, SelectPersonActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoadingScreenActivity.this, NewsLetterPageActivity.class);
                    startActivity(intent);
                }
                customType(LoadingScreenActivity.this, "fadein-to-fadeout");
            }
        },6000);
    }

    public static void customType(Context context, String animtype){
        Activity act = (Activity) context;
        switch(animtype){
            case "fadein-to-fadeout":
                act.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            default:
                break;
        }
    }
}
