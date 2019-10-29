package com.bitm.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private EditText emailET,passwordET;
    private Button signinBTN;

    private  String email,password;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("Sign In");

        init();


        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=emailET.getText().toString();
                password=passwordET.getText().toString();

                signin(email,password);

            }
        });

    }

    private void signin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){


                    startActivity(new Intent(SignInActivity.this,MainActivity.class));


                }

                else {

                    Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void init() {

        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        signinBTN=findViewById(R.id.signinBTN);
        firebaseAuth=FirebaseAuth.getInstance();


    }



    public void signupTV(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
