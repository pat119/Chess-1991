package com.example.myapplication.welcome;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.databinding.ActivityStartBinding;
import com.example.myapplication.main_menu.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    private View decorView;
    EditText usernameInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    DatabaseReference dbref;
    protected ArrayList<User> myUsers;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = (User) getIntent().getSerializableExtra("profile");

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

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        Button signupConfirmButton = (Button)findViewById(R.id.SignupConfirmButton);
        signupConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get new username/password
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                // Check that user has entered a username and password
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Empty input field", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // Check that password is confirmed correctly
                if (!password.equals(confirmPassword)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Passwords must match", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (password.length() < 8) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Password must be at least 8 characters", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (!isStrongPassword(password)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Password must include uppercase, lowercase, and numerical characters" , Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                // Add username/password to the database
//                dbref.child("logins").child(username).setValue(password);
                String hashedPassword = hashPassword(password);


                String key = dbref.child("logins").push().getKey();
                User myUser = new User(username, hashedPassword, key);


                for (User user : myUsers) {
                    if (user.getUsername().equals(username)) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Duplicate username", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }

                if (user != null) {
                    myUser.merge(user);
                }
                myUsers.add(myUser);
                dbref.child("logins").child(key).setValue(myUser);
                Toast.makeText(getApplicationContext(), "Added account!", LENGTH_SHORT).show();

                /*  Maybe not necessary anymore
                // Update pref to reflect that a user is logged in
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("profile", username);
                editor.apply();

                 */

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                intent.putExtra("profile", myUser);
                startActivity(intent);
                finish();
            }
        });

        // Set up back button in the upper-left corner
        Button backButton = (Button) findViewById(R.id.signupBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isStrongPassword(String password) {
        // Define password strength criteria
        int minLength = 8;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialChars = "!@#$%^&*()-_+=<>?";

        // Check password length
        if (password.length() < minLength) {
            return false;
        }

        // Check for uppercase, lowercase, digits, and special characters
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.contains(Character.toString(c))) {
                hasSpecialChar = true;
            }
        }

        // Password is strong if it meets all criteria
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
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

    private String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Apply the digest function to the input password
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return password;
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