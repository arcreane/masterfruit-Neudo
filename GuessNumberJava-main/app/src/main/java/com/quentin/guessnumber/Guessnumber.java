package com.quentin.guessnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class Guessnumber extends AppCompatActivity {
    Random random = new Random();
    int min = 1;
    int max = 10;
    int totalCorrect = 0;
    int tryLeft  = 10;
    int randomNumber = random.nextInt(max - min + 1) + min;

    public String selectedDifficulty = "easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessnumber);
    }


    public void startGame(View view) {
        RadioGroup radioGroup = findViewById(R.id.difficulty);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        LinearLayout layoutGame = findViewById(R.id.gameLayout);
        LinearLayout layoutOptions = findViewById(R.id.optionsLayout);

        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Choose a difficulty", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedDifficulty = selectedRadioButton.getText().toString();
            layoutGame.setVisibility(View.VISIBLE);
            layoutOptions.setVisibility(View.GONE);

        }
    }


    public void guess(View view) {
        EditText editText = findViewById(R.id.edit_text);
        TextView tryLeftText = findViewById(R.id.tryLeft);
        String userInput = editText.getText().toString();
        TextView scoreTextView = findViewById(R.id.score);

        if(Objects.equals(selectedDifficulty, "Hard")){
            max = 100;
        } else if (Objects.equals(selectedDifficulty, "Medium")) {
            max = 50;
        } else {
            max = 10;
        }


        if (!userInput.isEmpty()) {
            int userNumber = Integer.parseInt(userInput);

            if (userNumber == randomNumber) {
                Toast.makeText(this, "Congratulations! You guessed the correct number.", Toast.LENGTH_SHORT).show();
                totalCorrect += 1;
                tryLeft = 10;
                scoreTextView.setText("Score: " + totalCorrect);
                tryLeftText.setText("Essais restants : " + tryLeft);
                randomNumber = random.nextInt(max - min + 1) + min;

            } else if(userNumber <= randomNumber) {
                Toast.makeText(this, "Your number is too small. Try again.", Toast.LENGTH_SHORT).show();
                tryLeft -= 1;
                tryLeftText.setText("Essais restants : " + tryLeft);

            } else {
                Toast.makeText(this, "Your number is too big. Try again.", Toast.LENGTH_SHORT).show();
                tryLeft -= 1;
                tryLeftText.setText("Essais restants : " + tryLeft);
            }
            if (tryLeft == 0) {
                totalCorrect = 0;
                tryLeft = 10;
                scoreTextView.setText("Score: " + totalCorrect);
                tryLeftText.setText("Essais restants : " + tryLeft);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GAME OVER")
                        .setMessage("Try again?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                randomNumber = random.nextInt(max - min + 1) + min;
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Guessnumber.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }


        }

    }





}