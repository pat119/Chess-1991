package com.example.myapplication.customize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.databinding.ActivityCustomizeMenuBinding;
import com.example.myapplication.main_menu.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomizeActivity extends AppCompatActivity {

    private View decorView;
    EditText usernameInput;
    EditText passwordInput;
    DatabaseReference dbref;
    protected ArrayList<User> myUsers;
    SharedPreferences pref;
    User user;

    DatabaseReference ref;
    boolean unlockable1;
    boolean unlockable2;
    boolean unlockable3;
    boolean unlockable4;
    boolean unlockable5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myapplication.databinding.ActivityCustomizeMenuBinding binding = ActivityCustomizeMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
        user = (User) getIntent().getSerializableExtra("profile");
        Toast confirm = Toast.makeText(this, "SETTING SAVED", Toast.LENGTH_SHORT);

        ref = FirebaseDatabase.getInstance().getReference();
        pref = getSharedPreferences("User", Context.MODE_PRIVATE);

        Button backButton = findViewById(R.id.customizeBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent returnIntent = new Intent();
                returnIntent.putExtra("profile", user);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();


                 */

                // Temporary fix, bad for stack management
                Intent intent = new Intent(CustomizeActivity.this, MainActivity.class);
                intent.putExtra("profile", user);
                startActivity(intent);

            }
        });

        Button themeOne = findViewById(R.id.boardThemeOne);
        Button themeTwo = findViewById(R.id.boardThemeTwo);
        Button themeThree = findViewById(R.id.boardThemeThree);

        themeOne.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               user.setTheme(0);
               confirm.show();
           }
        });

        themeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setTheme(1);
                confirm.show();
            }
        });

        themeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setTheme(2);
                confirm.show();
            }
        });

        ImageButton playerPieceOne = findViewById(R.id.pieceOne);
        ImageButton playerPieceTwo = findViewById(R.id.pieceTwo);
        ImageButton playerPieceThree = findViewById(R.id.pieceThree);
        ImageButton playerPieceFour = findViewById(R.id.pieceFour);
        ImageButton playerPieceFive = findViewById(R.id.pieceFive);
        ImageButton playerPieceSix = findViewById(R.id.pieceSix);

        playerPieceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPiece(0);
                confirm.show();
            }
        });

        unlockable1 = user.isUnlock1();
        unlockable2 = user.isUnlock2();
        unlockable3 = user.isUnlock3();
        unlockable4 = user.isUnlock4();
        unlockable5 = user.isUnlock5();

        ref.child("logins").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> clients = snapshot.getChildren();
                for (DataSnapshot pair : clients) {
                    User test = pair.getValue(User.class);
                    if (user.getKey().equals(test.getKey())) {
                        unlockable1 = test.isUnlock1();
                        unlockable2 = test.isUnlock2();
                        unlockable3 = test.isUnlock3();
                        unlockable4 = test.isUnlock4();
                        unlockable5 = test.isUnlock5();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

        playerPieceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockable1 && !user.getKey().equals("guest")) {
                    user.setPiece(1);
                    confirm.show();
                } else if (user.getKey().equals("guest")) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to unlock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Reach wave 5 in any difficulty to unlock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerPieceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockable2 && !user.getKey().equals("guest")) {
                    user.setPiece(2);
                    confirm.show();
                } else if (user.getKey().equals("guest")) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to unlock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Reach wave 10 in any difficulty to unlock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerPieceFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockable3 && !user.getKey().equals("guest")) {
                    user.setPiece(3);
                    confirm.show();
                } else if (user.getKey().equals("guest")) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to unlock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Reach wave 5 in normal difficulty to unlock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerPieceFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockable4 && !user.getKey().equals("guest")) {
                    user.setPiece(4);
                    confirm.show();
                } else if (user.getKey().equals("guest")) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to unlock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Reach wave 10 in normal difficulty to unlock", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerPieceSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockable5 && !user.getKey().equals("guest")) {
                    user.setPiece(5);
                    confirm.show();
                } else if (user.getKey().equals("guest")) {
                    Toast.makeText(getApplicationContext(), "Must be signed in to unlock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Reach wave 5 in hard difficulty to unlock", Toast.LENGTH_SHORT).show();
                }
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
