package com.bitm.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    
    private EditText nameET,emailET,passwordET;
    private Button signupBTN;
    private String  name,email,password;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        
        init();
        
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameET.getText().toString();
                email=emailET.getText().toString();
                password=passwordET.getText().toString();
                
                signup(name,email,password);
                
                
            }
        });
        
        
    }

    private void signup(final String name, final String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    String id = firebaseAuth.getCurrentUser().getUid();

                    DatabaseReference dataRef=databaseReference.child("users").child(id);

                    HashMap<String,Object> userInfo=new HashMap<>();
                    userInfo.put("name",name);
                    userInfo.put("email",email);

                    dataRef.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                              startActivity(new Intent(SignUpActivity.this,SignInActivity.class));

                            }

                            else {

                                Toast.makeText(SignUpActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                }

                else {

                    Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void init() {
        
        nameET=findViewById(R.id.nameET);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        signupBTN=findViewById(R.id.signupBTN);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }

    public void signinTV(View view) {
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
    }
}
