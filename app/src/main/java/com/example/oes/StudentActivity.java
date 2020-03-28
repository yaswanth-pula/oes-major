package com.example.oes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText studentExamCode, studentRollEditText;
    private Button takeExamButton, submitExamButton;

    private static final int RADIO_BASE_ID = 2131230875;
    private FirebaseFirestore db;
    private CollectionReference rootDir,scoresDir;

    RecyclerView recyclerView;
    private static int examId = 0;
    private static String examCode;
    private static String studentRoll;
    List<MCQ_CLASS> myList1;
    List<BLANKS_CLASS> myList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        rootDir = db.collection("root");
        scoresDir = db.collection("results");

        studentExamCode = findViewById(R.id.studentExamCode);
        takeExamButton = findViewById(R.id.takeExamButton);
        submitExamButton = findViewById(R.id.studentExamSubmit);
        recyclerView = findViewById(R.id.studentExamLayout);
        studentRollEditText = findViewById(R.id.studentRoll);

        takeExamButton.setOnClickListener(this);
        submitExamButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.takeExamButton) {

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            examCode = studentExamCode.getText().toString().toUpperCase();
            studentRoll = studentRollEditText.getText().toString().toUpperCase();

            if (examCode.length() < 8)
                return;

            if (examCode.charAt(3) == '1') {
                examId = 1;
                myList1 = readFireBaseForMcq(examCode);
                recyclerView.setAdapter(new McqAdapter(myList1));
            } else {
                examId = 2;
                myList2 = readFireBaseForBlanks(examCode);
                recyclerView.setAdapter(new BlanksAdapter(myList2));
            }

        }
        if (v.getId() == R.id.studentExamSubmit) {
            int marks = 0;
            int questionsCount=0;
            if (examId == 1) {
                questionsCount = myList1.size();
                for (int i = 0; i < questionsCount; i++) {
                    final View rowView = recyclerView.getChildAt(i);

                    RadioGroup radioGroup = rowView.findViewById(R.id.optionsRadioGroup);

                    int ansId = radioGroup.getCheckedRadioButtonId();
                    System.out.println("E0xxF"+radioGroup.getId()+"-->>>"+RADIO_BASE_ID+"--->"+ansId);
                    int k = ansId - RADIO_BASE_ID;
                    int key = Integer.parseInt(myList1.get(i).getAnswerKey());

                    if (k == key)
                            marks++;

                }

            } else if (examId == 2) {
                questionsCount = myList2.size();
                for (int i = 0; i < questionsCount; i++) {
                    final View rowView = recyclerView.getChildAt(i);
                    EditText studentAnswerEditText = rowView.findViewById(R.id.blankStudentAnswer);
                    String studentCurrentAnswer = studentAnswerEditText.getText().toString().toLowerCase().trim();
                    String answerKey = myList2.get(i).getAnswerKey().toLowerCase().trim();
                    if (answerKey.equals(studentCurrentAnswer))
                            marks++;

                }
            }

            String result = marks+"/"+questionsCount;
            studentRoll = studentRollEditText.getText().toString().toUpperCase();

            Score currentStudentScore = new Score(studentRoll,result);
            readForFirebaseScores(examCode,currentStudentScore);

            Toast.makeText(this, "Marks are = " + result, Toast.LENGTH_LONG).show();

        }
    }

    private List<MCQ_CLASS> readFireBaseForMcq(final String examCode) {
        final List<MCQ_CLASS> questionPaper = new ArrayList<>();
        rootDir.document(examCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                takeExamButton.setVisibility(View.INVISIBLE);
                submitExamButton.setVisibility(View.VISIBLE);

                if (documentSnapshot.exists()) {
                    Mcq_Question_Paper mcq_question_paper = documentSnapshot.toObject(Mcq_Question_Paper.class);
                    int k = mcq_question_paper.getQuestionArray().size();

                    while (k-- > 0)
                        questionPaper.add(mcq_question_paper.getQuestionArray().get(k));

                } else {
                    Toast.makeText(StudentActivity.this, "INVALID EXAM CODE", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentActivity.this, "Could not Load", Toast.LENGTH_SHORT).show();
            }
        });
        return questionPaper;
    }

    private List<BLANKS_CLASS> readFireBaseForBlanks(final String examCode) {

        final List<BLANKS_CLASS> questionPaper = new ArrayList<>();

        rootDir.document(examCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                takeExamButton.setVisibility(View.INVISIBLE);
                submitExamButton.setVisibility(View.VISIBLE);

                if (documentSnapshot.exists()) {
                    Blank_Question_Paper blank_question_paper = documentSnapshot.toObject(Blank_Question_Paper.class);
                    int k = blank_question_paper.getQuestionArray().size();
                    while (k-- > 0)
                        questionPaper.add(blank_question_paper.getQuestionArray().get(k));
                } else {
                    Toast.makeText(StudentActivity.this, "INVALID EXAM CODE", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentActivity.this, "Could not Load", Toast.LENGTH_SHORT).show();
            }
        });
        return questionPaper;
    }
   private void readForFirebaseScores(final String examCode, final Score latestScore){
       scoresDir.document(examCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()) {
                   ScoreListClass scoreListClass = documentSnapshot.toObject(ScoreListClass.class);
                   if (scoreListClass.getScoreList() != null) {
                       scoreListClass.getScoreList().add(latestScore);
                       //fbCurrentScoreList.setScoreList(scoreListClass.getScoreList());
                   }
                   else{
                       ArrayList<Score> initialList = new ArrayList<>();
                       initialList.add(latestScore);
                       scoreListClass.setScoreList(initialList);
                       //fbCurrentScoreList.setScoreList(initialList);
                   }
                   writeFireBaseScores(scoreListClass);
               } else {
                   Toast.makeText(StudentActivity.this, "INVALID EXAM CODE", Toast.LENGTH_SHORT).show();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(StudentActivity.this, "Could not Load", Toast.LENGTH_SHORT).show();
           }
       });

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

   }
   private void writeFireBaseScores(ScoreListClass latestScoreListClassObj){
       scoresDir.document(examCode).set(latestScoreListClassObj).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Toast.makeText(StudentActivity.this,"content stored",Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(StudentActivity.this, "Could not store", Toast.LENGTH_SHORT).show();
           }
       });
   }


}
