package com.example.saacpfinal.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saacpfinal.Authentication.MainActivity;
import com.example.saacpfinal.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdminLogin extends AppCompatActivity {
    TextView userLogin,HelloThere,NeedHelp;
    TextInputLayout UsernameField,PasswordField;
    AppCompatButton LoginButton;
    LinearLayout AdminNot;
    Animation top,below,left,right,zoomin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorRedThin));

        HelloThere = findViewById(R.id.hello_there);
        NeedHelp = findViewById(R.id.NeedHelpAdmin);
        UsernameField = findViewById(R.id.AdminUsernameLogin);
        PasswordField = findViewById(R.id.AdminPasswordLogin);
        LoginButton = findViewById(R.id.LoginButonAdmin);
        userLogin = findViewById(R.id.userLoginAdmin);
        AdminNot = findViewById(R.id.not_admin);

        top = AnimationUtils.loadAnimation(this,R.anim.top_transition);
        below = AnimationUtils.loadAnimation(this,R.anim.bottom_transition);
        left = AnimationUtils.loadAnimation(this,R.anim.left_transition);
        right = AnimationUtils.loadAnimation(this,R.anim.right_transition);
        zoomin = AnimationUtils.loadAnimation(this,R.anim.zoom_in);

        HelloThere.setAnimation(below);
        LoginButton.setAnimation(left);
        NeedHelp.setAnimation(right);
        UsernameField.setAnimation(left);
        PasswordField.setAnimation(right);
        AdminNot.setAnimation(top);

        LoginButton.setOnClickListener(view -> {
            if(!UsernameField.getEditText().getText().toString().isEmpty() && !PasswordField.getEditText().getText().toString().isEmpty())
            {
                UsernameField.setHelperText(null);
                PasswordField.setHelperText(null);

                if(UsernameField.getEditText().getText().toString().equals("admin")  && PasswordField.getEditText().getText().toString().equals("admin123"))
                {
                    PasswordField.setHelperText(null);
                    UsernameField.setHelperText(null);
                    startActivity(new Intent(getApplicationContext(),AdminMenu.class));
                }
                else{
                    if(!UsernameField.getEditText().getText().toString().equals("admin")) {
                        UsernameField.setHelperText("Username wrong");
                    }
                    else if(!PasswordField.getEditText().getText().toString().equals("admin123")){
                        PasswordField.setError("Password not match");
                    }
            }

            }else{
                if(UsernameField.getEditText().getText().toString().isEmpty())
                {
                    UsernameField.setError("User name provide by admin");
                } else if (PasswordField.getEditText().getText().toString().isEmpty()) {
                    UsernameField.setError(null);
                    PasswordField.setError("Password provide by admin");
                }

            }
        });




        userLogin.setOnClickListener(view -> {startActivity(new Intent(getApplicationContext(), MainActivity.class));});


    }
}