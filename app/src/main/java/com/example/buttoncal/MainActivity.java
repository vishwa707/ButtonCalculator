package com.example.buttoncal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private String input = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorClicked = false;

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

        editTextNumber = findViewById(R.id.editTextNumber);

        setNumericButtonClickListener();
        setOperatorButtonClickListener();
    }

    private void setNumericButtonClickListener() {
        int[] numericButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            if (isOperatorClicked) {
                input = "";
                isOperatorClicked = false;
            }
            input += button.getText().toString();
            editTextNumber.setText(input);
        };

        for (int id : numericButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonClickListener() {
        int[] operatorButtonIds = {
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        };

        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            if (!input.isEmpty()) {
                firstNumber = Double.parseDouble(input);
                operator = button.getText().toString();
                isOperatorClicked = true;
                String a = String.valueOf(button.getText());
                        Toast.makeText(MainActivity.this,"Clicked:"+a, Toast.LENGTH_SHORT).show();

            }
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            if (!input.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(input);
                double result = calculate(firstNumber, secondNumber, operator);
                editTextNumber.setText(String.valueOf(result));
                input = String.valueOf(result);
                operator = "";
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            input = "";
            operator = "";
            firstNumber = 0;
            editTextNumber.setText("");
        });

        findViewById(R.id.btnBackspace).setOnClickListener(v -> {
            if (!input.isEmpty()) {
                input = input.substring(0, input.length() - 1);
                editTextNumber.setText(input);
            }
        });
    }

    private double calculate(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "×":
                return firstNumber * secondNumber;
            case "÷":
                if (secondNumber != 0) {
                    return firstNumber / secondNumber;
                } else {
                    editTextNumber.setText("Error");
                }
            default:
                return 0;
        }
    }
}
