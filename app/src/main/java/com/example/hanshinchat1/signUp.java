package com.example.hanshinchat1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    EditText email_edit,password_edit, name_edit, id_edit, birth_edit, password_check;
    Button register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        email_edit = findViewById(R.id.email_edit);
        name_edit = findViewById(R.id.name_edit);
        id_edit = findViewById(R.id.id_edit);
        birth_edit = findViewById(R.id.birth_edit);
        password_check = findViewById(R.id.password_check);
        password_edit = findViewById(R.id.password_edit);
        register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_edit.getText().toString();
                String password = password_edit.getText().toString();
                String confirmPassword = password_check.getText().toString();
                String id = id_edit.getText().toString();
                String birth = birth_edit.getText().toString();
                String name = name_edit.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    name_edit.setError("Enter name");
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    id_edit.setError("Enter id");
                    return;
                }
                if (TextUtils.isEmpty(birth)) {
                    birth_edit.setError("Enter birth");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    email_edit.setError("Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_edit.setError("Enter Password");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    password_check.setError("Enter confirmPassword");
                    return;
                }if(password.length() < 9){
                    email_edit.setError("Error password < 9");
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDb = mDatabase.child("Users").child(userId);
                                currentUserDb.child("name").setValue(name);
                                currentUserDb.child("id").setValue(id);
                                currentUserDb.child("birth").setValue(birth);
                                Toast.makeText(signUp.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signUp.this, login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(signUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}