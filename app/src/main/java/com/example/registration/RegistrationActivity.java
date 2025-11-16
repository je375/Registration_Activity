package com.example.registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.ArrayList;
import java.util.Calendar;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.net.Uri;
import android.content.SharedPreferences;
import android.content.Context;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword, etFirstName, etLastName,
            etEmail, etBirthdate, etAddress, etContact;
    RadioGroup genderGroup;
    CheckBox cbHobby1, cbHobby2, cbHobby3, cbHobby4, cbHobby5,
            cbHobby6, cbHobby7, cbHobby8, cbHobby9, cbHobby10;
    Spinner spinnerQuestion1, spinnerQuestion2, spinnerQuestion3;
    Button btnSubmit;
    ImageView imgProfile;
    Button btnCamera;
    Uri imageUri;
    Bitmap capturedImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etBirthdate = findViewById(R.id.etBirthdate);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        genderGroup = findViewById(R.id.genderGroup);
        cbHobby1 = findViewById(R.id.cbHobby1);
        cbHobby2 = findViewById(R.id.cbHobby2);
        cbHobby3 = findViewById(R.id.cbHobby3);
        cbHobby4 = findViewById(R.id.cbHobby4);
        cbHobby5 = findViewById(R.id.cbHobby5);
        cbHobby6 = findViewById(R.id.cbHobby6);
        cbHobby7 = findViewById(R.id.cbHobby7);
        cbHobby8 = findViewById(R.id.cbHobby8);
        cbHobby9 = findViewById(R.id.cbHobby9);
        cbHobby10 = findViewById(R.id.cbHobby10);
        spinnerQuestion1 = findViewById(R.id.spinnerQuestion1);
        spinnerQuestion2 = findViewById(R.id.spinnerQuestion2);
        spinnerQuestion3 = findViewById(R.id.spinnerQuestion3);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgProfile = findViewById(R.id.imgProfile);
        btnCamera = findViewById(R.id.btnCamera);

        etBirthdate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegistrationActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        etBirthdate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.security_questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestion1.setAdapter(adapter);
        spinnerQuestion2.setAdapter(adapter);
        spinnerQuestion3.setAdapter(adapter);

        btnCamera.setOnClickListener(v -> openCamera());
        btnSubmit.setOnClickListener(v -> validateInputs());
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            capturedImage = (Bitmap) extras.get("data");
            imgProfile.setImageBitmap(capturedImage);
        }
    }

    private void validateInputs() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String birthdate = etBirthdate.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        int genderId = genderGroup.getCheckedRadioButtonId();

        ArrayList<String> hobbies = new ArrayList<>();
        CheckBox[] hobbyBoxes = {cbHobby1, cbHobby2, cbHobby3, cbHobby4, cbHobby5,
                cbHobby6, cbHobby7, cbHobby8, cbHobby9, cbHobby10};
        for (CheckBox cb : hobbyBoxes) if (cb.isChecked()) hobbies.add(cb.getText().toString());

        String q1 = spinnerQuestion1.getSelectedItem() != null ? spinnerQuestion1.getSelectedItem().toString() : "";
        String q2 = spinnerQuestion2.getSelectedItem() != null ? spinnerQuestion2.getSelectedItem().toString() : "";
        String q3 = spinnerQuestion3.getSelectedItem() != null ? spinnerQuestion3.getSelectedItem().toString() : "";

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                birthdate.isEmpty() || address.isEmpty() || contact.isEmpty() ||
                genderId == -1 || hobbies.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("All fields are required")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("Password did not match.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (capturedImage == null) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("There is no Photo Taken.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("Please enter a valid email address.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (contact.length() != 11 || !contact.matches("\\d+")) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("Contact number must be 11 digits.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (q1.isEmpty() || q2.isEmpty() || q3.isEmpty() ||
                q1.equalsIgnoreCase("Select a question") ||
                q2.equalsIgnoreCase("Select a question") ||
                q3.equalsIgnoreCase("Select a question")) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("Please select three security questions.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (q1.equals(q2) || q1.equals(q3) || q2.equals(q3)) {
            new AlertDialog.Builder(this)
                    .setTitle("ATTENTION!")
                    .setMessage("Please select another questions.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        RadioButton selectedGender = findViewById(genderId);
        StringBuilder sb = new StringBuilder();
        for (String h : hobbies) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(h);
        }

        String accdetails = "Username: " + username +
                "\nPassword: " + password +
                "\nName: " + firstName + " " + lastName +
                "\nEmail: " + email +
                "\nBirthdate: " + birthdate +
                "\nGender: " + selectedGender.getText() +
                "\nAddress: " + address +
                "\nContact: " + contact +
                "\nHobbies: " + sb.toString() +
                "\nSecurity Questions:\n1) " + q1 + "\n2) " + q2 + "\n3) " + q3;

        new AlertDialog.Builder(this)
                .setTitle("Account Details")
                .setMessage(accdetails)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {

                    SharedPreferences sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("firstname", firstName);
                    editor.putString("lastname", lastName);

                    if (capturedImage != null) {
                        editor.putString("hasPhoto", "yes");
                        WelcomeActivity.capturedBitmap = capturedImage;
                    } else {
                        editor.putString("hasPhoto", "no");
                    }
                    editor.apply();

                    Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.putExtra("name", firstName + " " + lastName);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
