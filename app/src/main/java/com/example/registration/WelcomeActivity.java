package com.example.registration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Gravity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);

        String name = getIntent().getStringExtra("name");

        TextView message = new TextView(this);
        message.setText("WELCOME\n" + name + "!");
        message.setTypeface(null, Typeface.BOLD);
        message.setTextSize(40);
        message.setTextColor(Color.BLACK);
        message.setGravity(Gravity.CENTER);
        layout.addView(message);

        setContentView(layout);
    }
}
