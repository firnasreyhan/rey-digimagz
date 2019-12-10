package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.R;
import com.project.digimagz.api.InitRetrofit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateProfileActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private InitRetrofit initRetrofit;

    private EditText editTextDate;
    private MaterialButton materialButtonUpdate;
    private RadioGroup radioGroup;
    private String date, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initRetrofit = new InitRetrofit();

        editTextDate = findViewById(R.id.editTextDate);
        radioGroup = findViewById(R.id.radioGroupGender);
        materialButtonUpdate = findViewById(R.id.materialButtonUpdate);
        gender = "L";

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonMan) {
                    gender = "L";
                } else if (checkedId == R.id.radioButtonWoman) {
                    gender = "P";
                }
            }
        });

        materialButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRetrofit.putUserToApi(firebaseUser.getEmail(), firebaseUser.getDisplayName(), String.valueOf(firebaseUser.getPhotoUrl()), date, gender);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));
                SimpleDateFormat dateFormatterText = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
                //buttonToken.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
                editTextDate.setText(dateFormatterText.format(newDate.getTime()));
                date = dateFormatter.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
