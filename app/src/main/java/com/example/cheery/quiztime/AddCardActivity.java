package com.example.cheery.quiztime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // Go to MainActivity when add button is pushed
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String question = ((EditText) findViewById(R.id.enter_Q)).getText().toString();
                String answer = ((EditText) findViewById(R.id.enter_A)).getText().toString();
                i.putExtra("Question", question);
                i.putExtra("Answer", answer);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
