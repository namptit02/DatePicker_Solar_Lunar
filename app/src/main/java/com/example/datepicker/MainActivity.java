package com.example.datepicker;


import static com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils.getDaysOfLunarMonth;
import static com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils.getLunarDateOf;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.icu.util.ChineseCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;
import com.nlf.calendar.SolarMonth;

// MainActivity.java
public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Switch switchLunar;
    private Button buttonShowOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.datePicker);
        switchLunar = findViewById(R.id.switchLunar);
        buttonShowOutput = findViewById(R.id.buttonShowOutput);

        // Set the initial text for the switch based on the default mode
        switchLunar.setText(switchLunar.isChecked() ? "Chuyển ngày dương sang ngày âm" : "Chuyển ngày âm sang ngày dương");

        // Add a listener to the switch to update its text when the state changes
        switchLunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchLunar.setText(isChecked ? "Chuyển ngày dương sang ngày âm" : "Chuyển ngày âm sang ngày dương");
            }
        });

        buttonShowOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1; // Month is zero-based
                int day = datePicker.getDayOfMonth();
                boolean isLunar = switchLunar.isChecked();

                String result = isLunar ? convertToLunar(year, month, day) : convertToSolar(year, month, day);

                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }



// ...





    public String convertToLunar(int year, int month, int day) {
        String x = getLunarDateOf(year, month, day).toString();
        Pattern pattern = Pattern.compile("lunYear=(\\d+).*?lunMonth=(\\d+).*?lunDay=(\\d+)");
        Matcher matcher = pattern.matcher(x);
        String lunYear = null;
        if (matcher.find()) {
            lunYear = matcher.group(1);
            String lunMonth = matcher.group(2);
            String lunDay = matcher.group(3);
            String thang="", ngay ="";
             if(lunMonth.length()==1){
                 thang = "0"+ lunMonth;
             }else thang = lunMonth;
             if(lunDay.length()==1){
                 ngay ="0"+lunDay;
             }else ngay = lunDay;
            return "Lịch âm là: " + ngay +"/" + thang +  "/"  + lunYear;
        }
    return "Failed";

    }


    private String convertToSolar(int year, int month, int day) {

        Lunar date = new Lunar(year,month,day);
        String x = date.getSolar().toFullString().substring(0,10);
        String[] arr = x.split("-");
        return "Lịch dương là: "+arr[2]+"/"+arr[1]+"/"+arr[0];
    }
}
