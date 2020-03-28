package com.example.oes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity implements View.OnClickListener {
    private EditText scoreResultEditText;
    private Button getResultButton;
    private FirebaseFirestore db;
    private CollectionReference scoresDir;
    private RecyclerView resultRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreResultEditText = findViewById(R.id.scoreResultEditText);
        getResultButton = findViewById(R.id.getResultButton);
        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        scoresDir = db.collection("results");

        resultRecyclerView = findViewById(R.id.resultStatsLayout);

        getResultButton.setOnClickListener(Result.this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.getResultButton){
            String resultExamCode = scoreResultEditText.getText().toString().toUpperCase().trim();
            ArrayList<Score> requestedResultList = retriveFromFireBase(resultExamCode);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            resultRecyclerView.setLayoutManager(mLayoutManager);
            resultRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            resultRecyclerView.setAdapter(new ResultAdapter(requestedResultList));
        }
    }

    private ArrayList<Score> retriveFromFireBase(final String resultExamCode) {
        final ArrayList<Score> resultList = new ArrayList<>();
        scoresDir.document(resultExamCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ScoreListClass scoreListClass = documentSnapshot.toObject(ScoreListClass.class);
                    resultList.addAll(scoreListClass.getScoreList());

                } else {
                    Toast.makeText(Result.this, "INVALID EXAM CODE", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Result.this, "Could not Load", Toast.LENGTH_SHORT).show();
            }
        });
        return resultList;
    }

}
