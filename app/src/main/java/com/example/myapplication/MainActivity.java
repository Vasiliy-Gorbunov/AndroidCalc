package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView resultText, sign;
    private EditText firstNumber, secondNumber;
    private Button addition, subtraction, multiplication, division, go;

    public enum Operator
    {
        ADDITION("+") {
            @Override public double apply(double x1, double x2) {
                return x1 + x2;
            }
        },
        SUBTRACTION("-") {
            @Override public double apply(double x1, double x2) {
                return x1 - x2;
            }
        },
        MULTIPLICATION("*") {
            @Override public double apply(double x1, double x2) {
                return x1 * x2;
            }
        },
        DIVISION("/") {
            @Override public double apply(double x1, double x2) {
                return x1 / x2;
            }
        };

        private final String text;

        private Operator(String text) {
            this.text = text;
        }

        public abstract double apply(double x1, double x2);

        @Override public String toString() {
            return text;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resultText = findViewById(R.id.resultText);
        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        sign = findViewById(R.id.sign);
        addition = findViewById(R.id.addition);
        subtraction = findViewById(R.id.subtraction);
        multiplication = findViewById(R.id.multiplication);
        division = findViewById(R.id.division);
        go = findViewById(R.id.go);

        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String znak = String.valueOf(addition.getText());
                sign.setText(znak);
            }
        });
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String znak = String.valueOf(subtraction.getText());
                sign.setText(znak);
            }
        });
        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String znak = String.valueOf(multiplication.getText());
                sign.setText(znak);
            }
        });
        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String znak = String.valueOf(division.getText());
                sign.setText(znak);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float num1;
                float num2;
                if (!firstNumber.getText().toString().isEmpty()) {
                    num1 = Float.parseFloat(firstNumber.getText().toString());
                } else {
                    showPopup("Enter first number!");
                    return;
                }
                if (!secondNumber.getText().toString().isEmpty()) {
                    num2 = Float.parseFloat(secondNumber.getText().toString());
                } else {
                    showPopup("Enter second number!");
                    return;
                }
                String op = sign.getText().toString();
                String result;
                switch (op) {
                    case "+":
                        result = String.valueOf(Operator.ADDITION.apply(num1, num2));
                        break;
                    case "-":
                        result = String.valueOf(Operator.SUBTRACTION.apply(num1, num2));
                        break;
                    case "*":
                        result = String.valueOf(Operator.MULTIPLICATION.apply(num1, num2));
                        break;
                    case "/":
                        result = String.valueOf(Operator.DIVISION.apply(num1, num2));
                        break;
                    default:
                        showPopup("Enter operator!");
                        result = "";
                }
                if (secondNumber.getText().toString().equals("0")&&op.equals("/")) {
                    showAlert("Do you really want to divide by zero?");
                } else {
                resultText.setText(result);
                }
            }
        });

    }

    private void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you seriously?!")
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("I'm crazy!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPopup("O_O");
                        resultText.setText("Infinity");
                        dialog.cancel();
                    }
                })
                .setPositiveButton("No-no-no!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        secondNumber.setText(firstNumber.getText());
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPopup(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void goToPicActivity(View view) {
        Intent intent = new Intent(this, PicActivity.class);
        startActivity(intent);
    }
}