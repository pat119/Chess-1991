package com.example.myapplication.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.main_menu.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private View decorView;
    EditText usernameInput;
    EditText passwordInput;
    DatabaseReference dbref;
    protected ArrayList<User> myUsers;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User passed = (User) getIntent().getSerializableExtra("profile");

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        myUsers = new ArrayList<>();
        dbref = FirebaseDatabase.getInstance().getReference();
        dbref.child("logins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> clients = snapshot.getChildren();
                for (DataSnapshot pair : clients) {
                    User test = pair.getValue(User.class);
                    myUsers.add(test);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

        // Use settings to update various elements
        pref = getSharedPreferences("User", Context.MODE_PRIVATE);

        usernameInput = findViewById(R.id.userLoginInput);
        passwordInput = findViewById(R.id.passLoginInput);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Check that user has entered a username and password
                if (username.isEmpty() || password.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Empty input field", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                User prof = null;

                // Check that username exists
                String rightPassword = "";
                for (User user : myUsers) {
                    if (user.getUsername().equals(username)) {
                        rightPassword = user.getPassword();
                        prof = user;
                        break;
                    }
                }
                if (rightPassword.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No existing user with that name", Toast.LENGTH_SHORT);
                    toast.show();
                    prof = null;
                    return;
                }

                // Check that password is correct
                if (!password.equals(rightPassword)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Incorrect password", Toast.LENGTH_SHORT);
                    toast.show();
                    prof = null;
                    return;
                }

                /* Don't think this is needed anymore, but leaving it just in case
                // Update pref to reflect that a user is logged in
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("profile", username);
                editor.apply();

                 */

                if (passed != null) {
                    prof.merge(passed);
                }
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Logged in!", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("profile", prof);
                startActivity(intent);
                finish();
            }
        });

        // Set up back button in the upper-left corner
        Button backButton = (Button) findViewById(R.id.loginBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}