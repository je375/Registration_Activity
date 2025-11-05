package registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister, tvTitle, tvUsername, tvPassword;
    Context context = this;

    String acc1Username = "Jeff", acc1Password = "12345";
    String acc2Username = "Joan", acc2Password = "567890";
    String acc3Username = "Dani", acc3Password = "ASDFGH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        initializeUI();

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeUI() {
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        mainLayout.setPadding(50, 100, 50, 0);

        tvTitle = new TextView(this);
        tvTitle.setText("SIMPLE LOGIN APP");
        tvTitle.setTextSize(24);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setPadding(0, 60, 0, 60);
        mainLayout.addView(tvTitle);

        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        inputParams.setMargins(40, 40, 40, 40);

        tvUsername = new TextView(this);
        tvUsername.setText("USERNAME");
        tvUsername.setTextSize(20);
        tvUsername.setLayoutParams(inputParams);
        mainLayout.addView(tvUsername);

        etUsername = new EditText(this);
        etUsername.setHint("Enter Username");
        etUsername.setLayoutParams(inputParams);
        mainLayout.addView(etUsername);

        tvPassword = new TextView(this);
        tvPassword.setText("PASSWORD");
        tvPassword.setTextSize(20);
        tvPassword.setLayoutParams(inputParams);
        mainLayout.addView(tvPassword);

        etPassword = new EditText(this);
        etPassword.setHint("Enter Password");
        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etPassword.setTypeface(etUsername.getTypeface());
        etPassword.setLayoutParams(inputParams);
        mainLayout.addView(etPassword);

        btnLogin = new Button(this);
        btnLogin.setText("LOGIN");
        btnLogin.setBackgroundColor(Color.BLACK);
        btnLogin.setTextColor(Color.WHITE);
        btnLogin.setLayoutParams(inputParams);
        mainLayout.addView(btnLogin);

        tvRegister = new TextView(this);
        tvRegister.setText("Not yet registered? Click Here.");
        tvRegister.setGravity(Gravity.CENTER);
        tvRegister.setPadding(0, 30, 0, 0);
        mainLayout.addView(tvRegister);

        setContentView(mainLayout);

        btnLogin.setOnClickListener(v -> validateLogin());
        tvRegister.setOnClickListener(v -> showRegisterDialog());
    }

    private void validateLogin() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        boolean match =
                (user.equals(acc1Username) && pass.equals(acc1Password)) ||
                        (user.equals(acc2Username) && pass.equals(acc2Password)) ||
                        (user.equals(acc3Username) && pass.equals(acc3Password));

        if (match) {
            Toast.makeText(context, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
            Log.i("LOGIN", "Login successful for user: " + user);
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Login Failed")
                    .setMessage("Username or Password is incorrect.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void showRegisterDialog() {
        new AlertDialog.Builder(context)
            .setTitle("Register")
            .setMessage("You will be redirected to the registration screen.")
            .setPositiveButton("OK", (dialog, which) -> {
                // Move to Activity4 after clicking OK
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(i);
            })
        .setNegativeButton("Cancel", null)
        .show();
    }
}
