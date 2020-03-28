package com.example.oes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ExaminerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button newExamButton;
    private Spinner examTypeSpinner;
    private int examTypeId=-1;
    public static final String EXAM_TYPE_ID ="com.example.oes.EXAM_TYPE_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examiner);
        newExamButton = findViewById(R.id.newExamButton);
        examTypeSpinner = findViewById(R.id.examTypeSpinner);
        examTypeSpinner.setOnItemSelectedListener(this);
        newExamButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newExamButton){
            if(examTypeId!=0) {
                System.out.println("done");
                Intent intent = new Intent(this, ExamCreationActivity.class);
                intent.putExtra(EXAM_TYPE_ID,examTypeId);
                startActivity(intent);
            }
            else
                Toast.makeText(ExaminerActivity.this,"Select an Exam Type",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);
        examTypeId = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            examTypeId=-1;
    }
}
