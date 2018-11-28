package com.example.joshua.kickstart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Type;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class SelectPersonActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingTextButton committeeButton;
    private FloatingTextButton memberButton;
    private TextView statement;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);

        committeeButton = (FloatingTextButton) findViewById(R.id.committee);
        memberButton = (FloatingTextButton) findViewById(R.id.member);
        statement = (TextView) findViewById(R.id.selectionText);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/dry_brush.ttf");
        statement.setTypeface(custom_font);

        committeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SelectPersonActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyOpened = prefs.getBoolean("First Time", false);
                SharedPreferences.Editor changes = prefs.edit();
                changes.putBoolean("First Time", true);
                changes.commit();

                progressDialog.setMessage("Logging in... ");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword("studymate74@gmail.com", "10081008")
                        .addOnCompleteListener(SelectPersonActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                    boolean previouslyOpened = prefs.getBoolean("First Time", false);
                                    SharedPreferences.Editor changes = prefs.edit();
                                    changes.putBoolean("First Time", true);
                                    changes.commit();
                                    Intent intent = new Intent(SelectPersonActivity.this, NewsLetterPageActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

                Intent newsLetter = new Intent(SelectPersonActivity.this, NewsLetterPageActivity.class);
                startActivity(newsLetter);
                customType(SelectPersonActivity.this, "fadein-to-fadeout");
            }
        });
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

    @Override
    public void onBackPressed(){
    }
}
