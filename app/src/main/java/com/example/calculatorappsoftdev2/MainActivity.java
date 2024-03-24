package com.example.calculatorappsoftdev2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultInterface, solutionInterface;
    MaterialButton btnC, btnBackspace, btnPercentage, btnDiv;
    MaterialButton btn7, btn8, btn9, btnAdd;
    MaterialButton btn4, btn5, btn6, btnSub;
    MaterialButton btn1, btn2, btn3, btnMul;
    MaterialButton btnDec, btn0, btnEqu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultInterface = findViewById(R.id.result_interface);
        solutionInterface = findViewById(R.id.soln_interface);

        // Initialize buttons
        btnC = findViewById(R.id.button_c);
        btnBackspace = findViewById(R.id.button_backspace);
        btnPercentage = findViewById(R.id.button_percent);
        btnDiv = findViewById(R.id.button_division);
        btnAdd = findViewById(R.id.button_addition);
        btnSub = findViewById(R.id.button_subtraction);
        btnMul = findViewById(R.id.button_multiplication);
        btnEqu = findViewById(R.id.button_equal);
        btnDec = findViewById(R.id.button_decimal);
        btn9 = findViewById(R.id.button_9);
        btn8 = findViewById(R.id.button_8);
        btn7 = findViewById(R.id.button_7);
        btn6 = findViewById(R.id.button_6);
        btn5 = findViewById(R.id.button_5);
        btn4 = findViewById(R.id.button_4);
        btn3 = findViewById(R.id.button_3);
        btn2 = findViewById(R.id.button_2);
        btn1 = findViewById(R.id.button_1);
        btn0 = findViewById(R.id.button_0);

        // Set onClickListener for buttons
        btnC.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnPercentage.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnEqu.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn0.setOnClickListener(this);

        // Apply edge-to-edge display
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btnTxt = btn.getText().toString();
        String calc = solutionInterface.getText().toString();

        if (btnTxt.equals("C")) {
            solutionInterface.setText("");
            resultInterface.setText("0");
            return;
        }

        if (btnTxt.equals("=")) {
            solutionInterface.setText(resultInterface.getText());
            return;
        }

        if (btnTxt.equals("⌫")) {
            if (!calc.isEmpty() && resultInterface.getText().length() > 1) {
                calc = calc.substring(0, calc.length() - 1);
            } else if (resultInterface.getText().length() == 1) {
                solutionInterface.setText("");
                resultInterface.setText("0");
                return;
            }
        } else {
            if (isOperator(btnTxt)) {
                // Check if the last character in calc is an operator
                if (!calc.isEmpty() && isOperator(calc.substring(calc.length() - 1))) {
                    return; // Do nothing if the last character is already an operator
                }
            }
            calc = calc + btnTxt;
        }
        solutionInterface.setText(calc);

        String finalResult = getResult(calc);

        if (!finalResult.equals("Error")) {
            resultInterface.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }

    private boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("×") || str.equals("÷");
    }
}
