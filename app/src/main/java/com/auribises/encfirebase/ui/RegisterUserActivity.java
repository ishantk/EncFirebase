package com.auribises.encfirebase.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.auribises.encfirebase.R;
import com.auribises.encfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.editTextName)
    EditText eTxtName;

    @BindView(R.id.editTextEmail)
    EditText eTxtEmail;

    @BindView(R.id.editTextPassword)
    EditText eTxtPassword;

    @BindView(R.id.buttonRegister)
    Button btnRegister;

    FirebaseAuth auth;
    User user;

    FirebaseFirestore firestore;
    CollectionReference userCollection;

    ProgressDialog progressDialog;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        ButterKnife.bind(this);

        btnRegister.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userCollection = firestore.collection("users");

        user = new User();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        //btnRegister.setText("Login");
    }

    void saveUser(){

        userCollection.document(uid).set(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RegisterUserActivity.this,"User Saved in DB",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterUserActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterUserActivity.this,"Error while saving User",Toast.LENGTH_LONG).show();
            }
        });

        /*userCollection.add(user).addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterUserActivity.this,"User Saved in DB",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterUserActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterUserActivity.this,"Error while saving User",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    void registerUser(){

        progressDialog.show();

        auth.createUserWithEmailAndPassword(user.email,user.password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    uid = task.getResult().getUser().getUid();
                    // uid can be saved in the Firebase Database along with other details of user

                    Toast.makeText(RegisterUserActivity.this,user.name+" Registered Successfully !!",Toast.LENGTH_LONG).show();
                    Log.i("User","User Registered: "+uid);

                    saveUser();
                }

                progressDialog.dismiss();
            }
        }).addOnFailureListener(this,new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("User","User Registration Failed: "+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });

    }

    void loginUser(){
        progressDialog.show();
        auth.signInWithEmailAndPassword(user.email,user.password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterUserActivity.this, user.name + " Login Success !!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterUserActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }
            }
        })
        .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("User","User Login Failed: "+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View view) {

        user.name = eTxtName.getText().toString().trim();
        user.email = eTxtEmail.getText().toString().trim();
        user.password = eTxtPassword.getText().toString().trim();

        registerUser();
        //loginUser();

    }
}
