package com.example.datepicker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// OutputActivity.java
public class OutputActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        textViewResult = findViewById(R.id.textViewResult);

        // Lấy kết quả từ Intent và hiển thị lên TextView
        String result = getIntent().getStringExtra("result");
        textViewResult.setText(result);
    }
}
