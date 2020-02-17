package com.mind.asvisa;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btRegister;
    private TextView tvLogin;
    FirebaseAuth fAuth;
    EditText mMal,mSif;
    Button mBt_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMal = findViewById(R.id.mal);
        mSif = findViewById(R.id.password);
        mBt_Login = findViewById(R.id.bt_login);
        fAuth = FirebaseAuth.getInstance();

        mBt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =mMal.getText().toString().trim();
                String password=mSif.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mMal.setError("Email gerekli");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mSif.setError("Şifre gerekli");
                    return;
                }



                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Giris Basarili", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {

                            Toast.makeText(Login.this, "Hata!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                });


            }
        });









        btRegister  = findViewById(R.id.btRegis);
        tvLogin     = findViewById(R.id.tvLogin);
        btRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent intent   = new Intent(Login.this,RegisterActivity.class);
            Pair[] pairs    = new Pair[1];
            pairs[0] = new Pair<View,String>(tvLogin,"tvLogin");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
            startActivity(intent,activityOptions.toBundle());
        }
    }
}
