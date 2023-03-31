package com.example.myapplication;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.calculator.BasicCalculator;
import com.example.myapplication.calculator.Calculator;
import com.example.myapplication.calculator.AdvancedCalculator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayText, recordText;
    private String currentInput = "";
    private String currentOperator = "";
    private double operand1 = 0;
    private double operand2 = 0;
    private double result = 0;
    private boolean hasDecimalPoint = false;

    private Calculator basicCalculator;
    private Calculator advancedCalculator;
    private Calculator calculator;
    private Button switchButton, gcdButton, modButton;

//    private int[] ints = {
//            R.id.button0,
//            R.id.button1,
//            R.id.button2,
//            R.id.button3,
//            R.id.button4,
//            R.id.button5,
//            R.id.button6,
//            R.id.button7,
//            R.id.button8,
//            R.id.button9,
//    };

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayText = findViewById(R.id.display_text);
        recordText = findViewById(R.id.record_text);
        this.basicCalculator = new BasicCalculator(displayText, recordText);
        this.advancedCalculator = new AdvancedCalculator(displayText, recordText);
        calculator = advancedCalculator;
        displayText.setGravity(Gravity.END);//todo: whether this method can move the text.
        displayText.setText("0");





        Button[] buts = {
                (Button)findViewById(R.id.button0),
                (Button)findViewById(R.id.button1),
                (Button)findViewById(R.id.button2),
                (Button)findViewById(R.id.button3),
                (Button)findViewById(R.id.button4),
                (Button)findViewById(R.id.button5),
                (Button)findViewById(R.id.button6),
                (Button)findViewById(R.id.button7),
                (Button)findViewById(R.id.button8),
                (Button)findViewById(R.id.button9),
                (Button)findViewById(R.id.buttonDecimal),
                (Button)findViewById(R.id.buttonDivide),
                (Button)findViewById(R.id.buttonEquals),
                (Button)findViewById(R.id.buttonMinus),
                (Button)findViewById(R.id.buttonMultiply),
                (Button)findViewById(R.id.buttonPlus),
                findViewById(R.id.buttonAC),
                findViewById(R.id.buttonC),
                findViewById(R.id.buttonConvert),
                findViewById(R.id.buttonGetCommonDivisor),
                findViewById(R.id.buttonMod),
                findViewById(R.id.buttonBack),
                findViewById(R.id.buttonE),
                findViewById(R.id.buttonPow)
        };
        for (Button but : buts) {
            but.setOnClickListener(this);
        }

//        initialOtherBbutton();




    }

    private void initialOtherBbutton(){
//        this.switchButton = findViewById(R.id.buttonPow);
//        switchButton.setText("Com");
//        switchButton.setOnClickListener(v -> {


//            if(calculator == basicCalculator){
//                calculator = advancedCalculator;
//                switchButton.setText("Big");
//            }else{
//                calculator = basicCalculator;
//                switchButton.setText("Com");
//            }
//            calculator.handleActualClearInput();
//        });

//        this.gcdButton = findViewById(R.id.buttonGetCommonDivisor);
//        this.gcdButton.setOnClickListener(v->{
////            basicCalculator.
//            bigCalculator.
//        });
    }

//    @Deprecated
    @SuppressLint("NonConstantResourceId")
    public void onButtonClick(View view) {
//        Button button = (Button) view;
//        String buttonText = button.getText().toString();

        switch (view.getId()){
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                handleDigitInput(((Button)view).getText().toString());
                break;
            case R.id.buttonDecimal:
                handleDecimalInput();
                break;

            case R.id.buttonMultiply:
            case R.id.buttonMinus:
            case R.id.buttonDivide:
            case R.id.buttonPlus:
                handleOperatorInput(((Button) view).getText().toString());
                break;

            case R.id.buttonEquals:
                handleEqualsInput();
                break;
            case R.id.buttonC:
                handleClearInput();
                break;
            case R.id.buttonAC:
                handleActualClearInput();
                break;
            case R.id.buttonPow:
//                displayText.setText("aaaa");
                if(this.calculator instanceof BasicCalculator){
                    this.calculator = this.advancedCalculator;
                    switchButton.setText("Big");
                }else{
                    this.calculator = basicCalculator;
                    switchButton.setText("Common");
                }
                this.calculator = (this.calculator == this.basicCalculator? advancedCalculator :basicCalculator);
                break;
        }
    }

//    @Deprecated
    private void handleDigitInput(String digit) {
        currentInput += digit;
        displayText.setText(currentInput);
    }

//    @Deprecated
    private void handleDecimalInput() {
        if (!hasDecimalPoint) {
            currentInput += ".";
            displayText.setText(currentInput);
            hasDecimalPoint = true;
        }
    }

//    @Deprecated
    private void handleActualClearInput() {
        currentInput = "";
        currentOperator = "";
        operand1 = 0;
        operand2 = 0;
        result = 0;
        hasDecimalPoint = false;
        displayText.setText("0");
    }

//    @Deprecated
    private void handleClearInput(){
        currentInput = "";
        displayText.setText("0");
    }

//    @Deprecated
    private void handleOperatorInput(String operator) {
        if (!currentInput.isEmpty()) {
            if (!currentOperator.isEmpty()) {
                calculateResult();
            }
            operand1 = Double.parseDouble(currentInput);
            currentInput = "";
            currentOperator = operator;
            hasDecimalPoint = false;
        }
    }

//    @Deprecated
    private void handleEqualsInput() {
        if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
            operand2 = Double.parseDouble(currentInput);
            calculateResult();
            currentInput = Double.toString(result);
            currentOperator = "";
            hasDecimalPoint = currentInput.contains(".");
            operand1 = result;
            operand2 = 0;
        }
    }

//    @Deprecated
    private void calculateResult() {
        switch (currentOperator) {
            case "+":
                result = operand1 + operand2;
                break;

            case "-":
                result = operand1 - operand2;
                break;

            case "*":
                result = operand1 * operand2;
                break;

            case "/":
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    result = Double.NaN;
                }
                break;

            default:
                break;
        }
        updateDisplayText();
    }

//    @Deprecated
    private void updateDisplayText() {
        if (Double.isNaN(result)) {
            displayText.setText("Error");
        } else if (Double.isInfinite(result)) {
            displayText.setText("Infinity");
        } else {
            if (isInteger(result)) {
                displayText.setText(String.valueOf((int)result));
            } else {
                displayText.setText(String.valueOf(result));
            }
        }
    }


    String log_tag = "app";
    @Override
    public void onClick(View v) {
//        onButtonClick(v);
        try {
            calculator.onButtonClick(v);
        }catch (Exception e){
            this.advancedCalculator = new AdvancedCalculator(displayText, recordText);
            this.basicCalculator = new BasicCalculator(displayText, recordText);
            this.calculator = this.calculator instanceof AdvancedCalculator ? this.advancedCalculator :this.basicCalculator;
            displayText.setText("ERROr!");
            e.printStackTrace();
            Log.d(log_tag, e.getMessage());
            onClick(v);
        }
    }

//    @Deprecated
    private boolean isInteger(double number) {
        return Math.ceil(number) == Math.floor(number);
    }
}