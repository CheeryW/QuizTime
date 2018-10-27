package com.example.cheery.quiztime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // Edit current flashcard
        if(MainActivity.editClicked) {
            String passedQ = getIntent().getStringExtra("Question");
            String passedA = getIntent().getStringExtra("Answer");
            ((EditText) findViewById(R.id.enter_Q)).setText(passedQ);
            ((EditText) findViewById(R.id.enter_A)).setText(passedA);
            MainActivity.editClicked = false;
        }

        // Go to MainActivity when add button is pushed
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String question = getIntent().getStringExtra("Question");
                String answer = getIntent().getStringExtra("Answer");
                data.putExtra("Question", question);
                data.putExtra("Answer", answer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        // Save new flashcard and return to MainActivity
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.enter_Q)).getText().toString();
                String answer = ((EditText) findViewById(R.id.enter_A)).getText().toString();

                // Display error message when question or answer is not specified
                if(question.length() == 0 || answer.length() == 0) {
                    String errMsg = "Must enter both Question and Answer!";
                    Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra("Question", question);
                data.putExtra("Answer", answer);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
