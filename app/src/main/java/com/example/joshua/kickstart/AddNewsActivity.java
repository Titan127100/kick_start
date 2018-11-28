package com.example.joshua.kickstart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class AddNewsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private AppCompatButton submitButton;
    private ImageButton imageButton;
    private EditText mTitle, mDescription, mLongDescription;
    private Uri imageUri;

    private DatabaseReference mDatabase;

    private StorageReference mStorage;

    private static final int GALLERY_REQUEST = 1;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("News");

        mTitle = (EditText) findViewById(R.id.titleText);
        mDescription = (EditText) findViewById(R.id.descriptionText);
        mLongDescription = (EditText) findViewById(R.id.longDescriptionText);

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);

        submitButton = (AppCompatButton) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePost();
            }
        });

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(Intent.ACTION_GET_CONTENT);
                image.setType("image/*");
                startActivityForResult(image, GALLERY_REQUEST);
            }
        });
    }

    private void makePost(){
        mProgress.setMessage("Uploading Content");
        mProgress.show();

        final String title_val = mTitle.getText().toString().trim();
        final String description_val = mDescription.getText().toString().trim();
        final String long_description_val = mLongDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(description_val) && !TextUtils.isEmpty(long_description_val) && imageUri != null){
            StorageReference filepath = mStorage.child("News_Content").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    DatabaseReference newPost = mDatabase.push();

                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(description_val);
                    newPost.child("longDescription").setValue(long_description_val);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgress.dismiss();
                    finish();
                }
            });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
    }
}
