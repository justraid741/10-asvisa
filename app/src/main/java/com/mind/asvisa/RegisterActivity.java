package com.mind.asvisa;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private RelativeLayout rlayout;
    private Animation animation;

    FirebaseAuth fAuth;
    EditText mMail,mName,mPas,mPass;
    Button mBt_register;
    TextView mUye_msn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mMail = findViewById(R.id.mail);
        mName = findViewById(R.id.fullname);
        mPas = findViewById(R.id.pas);
        mPass = findViewById(R.id.pass);
        mBt_register = findViewById(R.id.bt_register);
        mUye_msn = findViewById(R.id.uye_msn);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        mBt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mMail.getText().toString().trim();
                String password = mPas.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mMail.setError("Email gerekli");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPas.setError("Şifre gerekli");
                    return;
                }
                if (password.length() < 6) {
                    mPas.setError("Şifre en az 6 karakter uzunluğunda olmalı");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Kayıt Basarili", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Hata!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
                mUye_msn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    }
                });
            }

        });

        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
