package com.example.oes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamCreationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addQuestion;
    private LinearLayout examLayout;
    private int examTypeId;
    private String examCode;
    private TextView examCodeTextView;
    private Button submitButton;
    private FirebaseFirestore db;
    private CollectionReference rootDir,scoresDir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_creation);

        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        rootDir = db.collection("root");
        scoresDir = db.collection("results");


        Intent intent = getIntent();
        examTypeId = intent.getIntExtra(ExaminerActivity.EXAM_TYPE_ID, 0);
        addQuestion = findViewById(R.id.addQuestion);
        submitButton = findViewById(R.id.submitExam);

        examCodeTextView = findViewById(R.id.examCode);
        examCode = this.getExamCode();
        examCodeTextView.setText(examCode);

        addQuestion.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        examLayout = findViewById(R.id.questionContentLayout);
        if (examTypeId == 1)
            createQuestion(R.layout.mcq_question_layout);
        else
            createQuestion(R.layout.blanks_question_layout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addQuestion) {
            if (examTypeId == 1)
                createQuestion(R.layout.mcq_question_layout);
            else
                createQuestion(R.layout.blanks_question_layout);
        }
        if (v.getId() == R.id.submitExam) {
            // System.out.println(examLayout.getChildCount());
            int questionViews = examLayout.getChildCount();

            if (examTypeId == 1) {
                ArrayList<MCQ_CLASS> questionPaper = new ArrayList<>();
                while (questionViews-- > 0) {
                    View questionView = examLayout.getChildAt(questionViews);
                    //System.out.println(questionView.getId());
                    EditText questionEditText = questionView.findViewById(R.id.questionEntry);
                    EditText option1EditText = questionView.findViewById(R.id.Option1);
                    EditText option2EditText = questionView.findViewById(R.id.Option2);
                    EditText option3EditText = questionView.findViewById(R.id.Option3);
                    EditText option4EditText = questionView.findViewById(R.id.Option4);
                    EditText keyEditText = questionView.findViewById(R.id.answerKey);

                    MCQ_CLASS currentQuestion = new MCQ_CLASS();

                    currentQuestion.setQuestion(questionEditText.getText().toString());
                    currentQuestion.setAnswerKey(keyEditText.getText().toString());

                    currentQuestion.setOption1(option1EditText.getText().toString());
                    currentQuestion.setOption2(option2EditText.getText().toString());
                    currentQuestion.setOption3(option3EditText.getText().toString());
                    currentQuestion.setOption4(option4EditText.getText().toString());

                    currentQuestion.setQuestionTag("Question " + (questionViews + 1));
                    currentQuestion.printQuestion();

                    questionPaper.add(currentQuestion);
                }
                //   Mcq_Question_Paper obj = new Mcq_Question_Paper(questionPaper,examTypeId);
                writeToFireBase(new Mcq_Question_Paper(questionPaper, examTypeId));
            } else {
                ArrayList<BLANKS_CLASS> questionPaper = new ArrayList<>();
                while (questionViews-- > 0) {
                    View questionView = examLayout.getChildAt(questionViews);
                    //System.out.println(questionView.getId());
                    EditText questionEditText = questionView.findViewById(R.id.questionEntry);
                    EditText keyEditText = questionView.findViewById(R.id.answerKey);

                    BLANKS_CLASS currentQuestion = new BLANKS_CLASS();

                    currentQuestion.setQuestion(questionEditText.getText().toString());
                    currentQuestion.setAnswerKey(keyEditText.getText().toString());

                    currentQuestion.setQuestionTag("Question " + (questionViews + 1));
                 //   currentQuestion.printQuestion();
                    questionPaper.add(currentQuestion);
                    //Blank_Question_Paper obj = new Blank_Question_Paper(questionPaper,examTypeId);
                    writeToFireBase(new Blank_Question_Paper(questionPaper, examTypeId));
                }
            }
           createResultExamDoc(examCode);

            Intent intent = new Intent(ExamCreationActivity.this, MainActivity.class);
            startActivity(intent);

        }

    }



    private void createQuestion(int examLayoutId) throws NullPointerException {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(examLayoutId, null);
        // Add the new row before the add field button.
        if (examLayout.getChildCount() < 0)
            examLayout.addView(rowView, examLayout.getChildCount() - 1);
        else
            examLayout.addView(rowView, examLayout.getChildCount());
    }

    private void writeToFireBase(Blank_Question_Paper obj) {

        rootDir.document(examCode).set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ExamCreationActivity.this, "It Happened", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ExamCreationActivity.this, "It Doesn't Happened", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeToFireBase(Mcq_Question_Paper obj) {

        rootDir.document(examCode).set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ExamCreationActivity.this, "It Happened", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ExamCreationActivity.this, "It Doesn't Happened", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createResultExamDoc(String examCode) {
        ArrayList<Score> dummyList= new ArrayList<>();
        dummyList.add(new Score("ROll NO.","MARKS"));
        ScoreListClass dummyScoreListClass = new ScoreListClass(dummyList);
        scoresDir.document(examCode).set(dummyScoreListClass);
    }
    //returns a random code
    private String getExamCode() {
        Random rand = new Random();
        return "OES" +examTypeId+""+rand.nextInt(10000);

    }
}
