package com.quentin.guessnumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Gameguessnumber extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameguessnumber);
    }

    public void onGoButtonClicked(View view) {
        RadioGroup radioGroup = findViewById(R.id.difficulty);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            String selectedValue = radioButton.getText().toString();
        }
    }

    Random random = new Random();
    int min = 1;
    int max = 10;
    int randomNumber = random.nextInt(max - min + 1) + min;
    int totalCorrect = 0;
    int tryLeft  = 10;

    public void guess(View view) {
        EditText editText = findViewById(R.id.edit_text);
        TextView tryLeftText = findViewById(R.id.tryLeft);
        String userInput = editText.getText().toString();
        TextView scoreTextView = findViewById(R.id.score);


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
                                Intent intent = new Intent(Gameguessnumber.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }


        }

    }







}