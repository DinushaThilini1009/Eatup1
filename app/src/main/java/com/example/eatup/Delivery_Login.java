package com.example.eatup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Delivery_Login extends AppCompatActivity {
    TextInputLayout email, pass;
    Button Signin, Signinphone;
    TextView Forgotpassword, Signup;
    FirebaseAuth Fauth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__login);

        try{
            email=(TextInputLayout)findViewById(R.id.Demail);
            pass = (TextInputLayout)findViewById(R.id.Dpassword);
            Signin = (Button)findViewById(R.id.Loginbtn);
            Signup = (TextView) findViewById(R.id.Donot);
            Forgotpassword = (TextView)findViewById(R.id.Dforgotpass);
            Signinphone = (Button)findViewById(R.id.Dbtnphone);

            Fauth=FirebaseAuth.getInstance();
            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailid = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();

                    if(isValid()){
                        final ProgressDialog mDialog = new ProgressDialog(Delivery_Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Sign In Please Wait.......");
                        mDialog.show();

                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(Fauth.getCurrentUser().isEmailVerified()){
                                        mDialog.dismiss();
                                        Toast.makeText(Delivery_Login.this,"Congratulation! You Have Successfully Login",Toast.LENGTH_SHORT).show();
                                        Intent Z=new Intent(Delivery_Login.this,DeliveryFoodPanel_BottomNavigation.class);
                                        startActivity(Z);
                                        finish();
                                    }else{
                                        ReusableCodeForAll.ShowAlert(Delivery_Login.this,"Verification Failed","Your Have Not Verified Your Email");


                                    }

                                }else{
                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Delivery_Login.this,"Error",task.getException().getMessage());

                                }
                            }
                        });
                    }

                }
            });
            Signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Delivery_Login.this,Delivery_Registration.class));
                    finish();
                }
            });
            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Delivery_Login.this,Delivery_Forgotpassword.class));
                    finish();
                }
            });
            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Delivery_Login.this,Delivery_Loginphone.class));
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isValid = false, isValidemail = false, isValidpassword = false;
        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;

            } else {
                email.setErrorEnabled(true);
                email.setError("Invaild Email Address");
            }
        }
        if (TextUtils.isEmpty(pwd)) {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");

        } else {
            isValidpassword = true;
        }
        isValid = (isValidemail && isValidpassword) ? true : false;
        return isValid;

    }
}