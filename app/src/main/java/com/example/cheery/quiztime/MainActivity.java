package com.example.cheery.quiztime;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static boolean editClicked = false;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards; // hold all flashcard objects
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFlashcards.size() != 0) {
                    currentCardDisplayedIndex++; // show the next card by adding index

                    if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                        currentCardDisplayedIndex = 0; // when we reach the end of card list
                    }

                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                    // always keep the question side of the next card up
                    if (findViewById(R.id.flashcard_answer).getVisibility() == View.VISIBLE) {
                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        findViewById(R.id.prev_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFlashcards.size() != 0) {
                    currentCardDisplayedIndex--; // show the previous card by adding index

                    if (currentCardDisplayedIndex < 0) {
                        currentCardDisplayedIndex = allFlashcards.size() - 1; // when we reach the end of card list
                    }

                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                    // always keep the question side of the previous card up
                    if (findViewById(R.id.flashcard_answer).getVisibility() == View.VISIBLE) {
                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // delete a card
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards(); // update new list of cards

                // prompt user to add a card if no card is available
                if(allFlashcards.size() == 0) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a Question!");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText("Add an Answer!");
                    currentCardDisplayedIndex = 0;
                } else {
                    currentCardDisplayedIndex--;
                    if (currentCardDisplayedIndex < 0) {
                        currentCardDisplayedIndex = allFlashcards.size() - 1; // when we reach the end of card list
                    }
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                }
            }
        });

        // flip to show answer
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        // flip to show question
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        // Click answer1 (wrong)
        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getColor(R.color.richRed));
                findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.algaeGreen));
            }
        });

        // Click answer2 (wrong)
        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer2).setBackgroundColor(getColor(R.color.richRed));
                findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.algaeGreen));
            }
        });

        // Click answer3 (right)
        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.algaeGreen));
            }
        });

        // Reset
        findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getColor(R.color.grey));
                findViewById(R.id.answer2).setBackgroundColor(getColor(R.color.grey));
                findViewById(R.id.answer3).setBackgroundColor(getColor(R.color.grey));
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        // Show answer choices
        findViewById(R.id.visible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                findViewById(R.id.invisible).setVisibility(View.VISIBLE);
                findViewById(R.id.visible).setVisibility(View.INVISIBLE);
            }
        });

        // Hide answer choices
        findViewById(R.id.invisible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                findViewById(R.id.visible).setVisibility(View.VISIBLE);
                findViewById(R.id.invisible).setVisibility(View.INVISIBLE);
            }
        });

        // Go to AddCardActivity when add button is pushed
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(MainActivity.this, AddCardActivity.class);
                String oriQ = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String oriA = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
                i.putExtra("Question", oriQ);
                i.putExtra("Answer", oriA);
                MainActivity.this.startActivityForResult(i, 100);
            }
        });

        // Edit current flashcard
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddCardActivity.class);
                String oriQ = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String oriA = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
                i.putExtra("Question", oriQ);
                i.putExtra("Answer", oriA);
                editClicked = true;
                MainActivity.this.startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(requestCode == 100) {
            // Receive new question and answer on the flashcard
            String newQ = i.getExtras().getString("Question");
            String newA = i.getExtras().getString("Answer");
            ((TextView) findViewById(R.id.flashcard_question)).setText(newQ);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(newA);
            flashcardDatabase.insertCard(new Flashcard(newQ, newA));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }

}
