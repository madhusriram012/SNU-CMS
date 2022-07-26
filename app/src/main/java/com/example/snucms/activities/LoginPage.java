package com.example.snucms.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;
import com.example.snucms.jsonHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    private final Map<String, String[]> students = new HashMap<String, String[]>() {{
        put("tt001", new String[]{"test1", "0001", "pass01"});
        put("tt002", new String[]{"test2", "0002", "pass02"});
        put("tt003", new String[]{"test3", "0003", "pass03"});
        put("tt004", new String[]{"test4", "0004", "pass04"});
        put("tt005", new String[]{"test5", "0005", "pass05"});
        put("tt006", new String[]{"test6", "0006", "pass06"});
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE);
        String netid = sharedPref.getString("netid", "");
        if(!netid.equals("")) {
            firebaseHelper.netid = netid;
            firebaseHelper.name = sharedPref.getString("name", "");
            firebaseHelper.rollno = sharedPref.getString("rollno", "");
            createNotificationChannel();
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        //System.out.println("Fine before this");
        try {
            new jsonHelper(getApplicationContext()).readJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.login_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView editTextNetId = findViewById(R.id.editTextNetId);
        TextView editTextPassword = findViewById(R.id.editTextPassword);

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(students.containsKey(editTextNetId.getText().toString())) {
                    if(editTextPassword.getText().toString().equals(students.get(editTextNetId.getText().toString())[2])){
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("netid", editTextNetId.getText().toString());
                        editor.putString("name", students.get(editTextNetId.getText().toString())[0]);
                        editor.putString("rollno", students.get(editTextNetId.getText().toString())[1]);
                        firebaseHelper.netid = editTextNetId.getText().toString();
                        firebaseHelper.name = students.get(editTextNetId.getText().toString())[0];
                        firebaseHelper.rollno = students.get(editTextNetId.getText().toString())[1];
                        editor.apply();
                        //System.out.println(sharedPref.getAll());
                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    } else {
                        Toast.makeText(LoginPage.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}