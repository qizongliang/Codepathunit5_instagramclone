package com.example.instagram_clone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LoginActivity  extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        Button btnSignup = findViewById(R.id.btnSignup);
        EditText etSignupUsername = findViewById(R.id.etSignupusername);
        EditText etSignuppassword = findViewById(R.id.etSignuppassword);
        EditText etSignupemail = findViewById(R.id.etSignupemail);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etSignupUsername.getText().toString();
                String password = etSignuppassword.getText().toString();
                String email = etSignupemail.getText().toString();

                etSignupemail.setText("");
                etSignuppassword.setText("");
                etSignupUsername.setText("");

                createNewUser(username,password,email);
            }
        });
    }

    //
    private void loginUser(String username, String password){
        Log.i(TAG,"Attempting to login user "+ username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG,"Issue with login",e);
                    Toast.makeText(LoginActivity.this, "Login Failed",Toast.LENGTH_SHORT);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!",Toast.LENGTH_SHORT);
            }
        });
    }
    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void createNewUser(String username, String password, String email){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "Successful account creation", Toast.LENGTH_SHORT);
                    goMainActivity();
                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(LoginActivity.this, "Unsuccessful account creation", Toast.LENGTH_SHORT);
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
