package com.example.uploadmultipleimageinfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_BACK = 1;
    Button uploadBtn;
    StorageReference Folder;
    ProgressDialog progressDialog;

  //  private StorageReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadBtn=findViewById(R.id.uploadBtn);
        Folder= FirebaseStorage.getInstance().getReference().child("ImageFolder");
        progressDialog=new ProgressDialog(this);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE_BACK);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_BACK){
            if (resultCode==RESULT_OK){
                Uri ImageData=data.getData();

                progressDialog.setTitle("Data are adding");
                progressDialog.show();

                StorageReference ImageName=Folder.child("image"+ImageData.getLastPathSegment());
                ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                       Toast.makeText(MainActivity.this, "Data are adding", Toast.LENGTH_SHORT).show();
                    }
                });






            }
        }
    }
}
