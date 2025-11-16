package com.example.registration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Gravity;
import android.graphics.Bitmap;

public class WelcomeActivity extends AppCompatActivity {

    public static Bitmap capturedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.WHITE);
        layout.setPadding(50, 100, 50, 100);

        String name = getIntent().getStringExtra("name");
        if (name == null) name = "User";

        TextView message = new TextView(this);
        message.setText("WELCOME\n" + name + "!");
        message.setTypeface(null, Typeface.BOLD);
        message.setTextSize(28);
        message.setTextColor(Color.BLACK);
        message.setGravity(Gravity.CENTER);
        layout.addView(message);

        if (capturedBitmap != null) {
            ImageView img = new ImageView(this);
            img.setImageBitmap(capturedBitmap);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(600, 600);
            params.topMargin = 60;
            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layout.addView(img);
        } else {
            TextView noPhoto = new TextView(this);
            noPhoto.setText("\n(No photo available)");
            noPhoto.setTextSize(18);
            noPhoto.setTextColor(Color.GRAY);
            layout.addView(noPhoto);
        }
        setContentView(layout);
    }
}
