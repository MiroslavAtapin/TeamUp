package com.example.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPassword = findViewById(R.id.edPassword);
        TextView tvEror = findViewById(R.id.tvEror);
        Button btnGoToRegisterProfile = findViewById(R.id.btnGoToRegisterProfile);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnGoToRegisterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edEmail.getText().toString().isEmpty() && !edPassword.getText().toString().isEmpty()
                        && edEmail.getText().toString().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
                    ParseUser user = new ParseUser();
                    user.setUsername(edEmail.getText().toString());
                    user.setEmail(edEmail.getText().toString());
                    user.setPassword(edPassword.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null){
                                Toast.makeText(getApplicationContext(), "Успех!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, RegisterProfileActivity.class));
                            } else {
                                ParseUser.logOut();
                                String errorMessage = e.getMessage();
                                Toast.makeText(getApplicationContext(), "Ошибка! Попробуйте еще раз!", Toast.LENGTH_LONG).show();
                                System.out.println(errorMessage);
                                Log.e("RegistrationError", "Ошибка регистрации: " + errorMessage);
                            }
                        }
                    });
                } else {
                    tvEror.setText("Вы неправильно ввели email или пароль.\nПопробуйте еще раз.");
                }
            }
        });

    }
}