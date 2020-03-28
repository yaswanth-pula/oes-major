package com.example.oes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button examinerButton,studentButton,resultButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        examinerButton = findViewById(R.id.examinerButton);
        studentButton = findViewById(R.id.studentButton);
        resultButton = findViewById(R.id.resultButton);

        examinerButton.setOnClickListener(this);
        studentButton.setOnClickListener(this);
        resultButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.examinerButton){
            Intent intent = new Intent(MainActivity.this, ExaminerActivity.class);
            MainActivity.this.startActivity(intent);
        }
        if(v.getId()==R.id.studentButton){
            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
            MainActivity.this.startActivity(intent);
        }
        if(v.getId()==R.id.resultButton){
            Intent intent = new Intent(MainActivity.this, Result.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
