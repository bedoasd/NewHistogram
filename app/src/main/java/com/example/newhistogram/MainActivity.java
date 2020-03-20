package com.example.newhistogram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
        private static final int PICK_IMAGE_REQUEST = 1;

        private Button mButtonChooseImage;
        private Button mButtonUpload;
        private TextView mTextViewShowUploads;
        private EditText mEditTextFileName;
        private ImageView mImageView;
        private ProgressBar mProgressBar;

        private Uri mImageUri;

        private StorageReference mStorageRef;
        private DatabaseReference mDatabaseRef;
        private FirebaseStorage storage;


        private StorageTask mUploadTask;
         private  String mimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonChooseImage = findViewById(R.id.btn_choose_file);
        mButtonUpload = findViewById(R.id.btn_upload);
        mTextViewShowUploads = findViewById(R.id.show_uploads);
        mEditTextFileName = findViewById(R.id.enter_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progrees_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowUploads();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getApplicationContext()).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));


            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                             mImageUri =uri;
                            Log.d("IMAGESAPP uri", "" + uri);
                            Log.d("IMAGESAPP 2uri",  "" + mImageUri);


                            // nta grbt ?
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Upload upload=new Upload(mEditTextFileName.getText().toString().trim()
                                    , mImageUri.toString());

                            //  wait for me I get the code
                            // let's describe what happen here
                            // here you take an image from gallery and upload it to "Storage References" in firebase
                            // then you take the uri of the Uploaded image on firebase and save it in new child in firebase data
                            // untill now all the process is correctly
                            String uploadid=mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadid).setValue(upload);
                            Log.d("IMAGESAPP name",  "" + upload.getName());
                            Log.d("IMAGESAPP link",  "" + upload.getImageUrl());

                            // run keda b2a
                            // e4t8aal ?
                            //3awz akollak haga
                            //hwa by run 3ady bs elmoshkela iny lma ba2fl w aft7 elswar
                            // mana 34ar f ..b l2 anat a2zsod hb2ra  lma ebllt2yf lo msbh bty4t8zlh brrd o ?
                              //name bs  tmam ?
                            //wa5d ballak mn el elmo4kella ?
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int)progress);

                }
            });


        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }


    }
    private void ShowUploads(){
        startActivity(new Intent(getApplicationContext(),ImagesActivity.class));
    }
}