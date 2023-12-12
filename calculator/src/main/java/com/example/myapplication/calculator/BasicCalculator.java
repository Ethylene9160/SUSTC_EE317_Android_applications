package com.example.myapplication.calculator;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;


public class BasicCalculator extends Calculator {
    private double operand1 = 0;
    private double operand2 = 0;
    private double result = 0;
//    private boolean hasDecimalPoint = false;
    public BasicCalculator(TextView displayText, TextView recordText) {
        super(displayText, recordText);
    }


//    @Override
//    protected void handleDigitInput(String digit) {
//        currentInput += digit;
//        displayText.setText(currentInput);
//    }

//    @Deprecated
//    protected void handleDecimalInput() {
//        if (!hasDecimalPoint) {
//            currentInput += ".";
//            displayText.setText(currentInput);
//            hasDecimalPoint = true;
//        }
//    }


    @Override
    public void handleActualClearInput() {
        handleClearInput();
        operand1 = 0;
        operand2 = 0;
        result = 0;
//        hasDecimalPoint = false;
        displayText.setText("0");
    }

    @Override
    public void handleScienceInput() {

    }

//    @Override
//    protected void resetOperand() {
//        this.operand1 = result;
//    }

    @Override
    protected void handleOperatorInput(String operator) {
        if(isOver) isOver = false;
        if (!currentInput.isEmpty()) {//if there esitst input, we need to judge whether to operator, in order to avoid like 1+2+3...(continuous add)
            if (!currentOperator.isEmpty()) {//continuous add
//                if(currentInput.equals("-")) {
//                    currentInput = "";
//                    displayText.setText("");
//                }
                //calculate the result, and update the result to operand1.
                operand2 = Double.parseDouble(currentInput);

                calculateResult(currentOperator);
                operand2 = 0;
                operand1 = result;
            }
//            else if(operator.equals("-")){
//                super.handleDigitInput(operator);
//                operand1 = -Double.parseDouble(currentInput);
//            }
            else{
                operand1 = Double.parseDouble(currentInput);
            }
//            currentDecimalPlace = Math.max(currentDecimalPlace, getDecimalPlaces(currentInput));
            currentInput = "";//refresh the input edit-text
            currentOperator = operator;//update opetator
//            hasDecimalPoint = false;
        }else{//only to change the operator
//            if(!currentInput.isEmpty() && operator.equals("-")){
//                if (currentInput.charAt(0) == '-') {
//                    currentInput = currentInput.substring(1);
//                }else currentInput = "-"+currentInput;
//                displayText.setText(currentInput);
//            }
            currentOperator = operator;
        }
    }
//    private void enhance(){
//        operand1 =
//    }

    @Override
    protected void handleEqualsInput() {
        if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
            operand2 = Double.parseDouble(currentInput);
            calculateResult(currentOperator);
            currentInput = "";
            currentOperator = "";
//            hasDecimalPoint = Double.toString(result).contains("\\.");
            operand1 = result;
            operand2 = 0;
        }
    }

    @Override
    protected void updateResultDisplayText() {
        if (Double.isNaN(result)) {
            displayText.setText("Error");
        } else if (Double.isInfinite(result)) {
            displayText.setText("Infinity");
        } else {
            if (isInteger(result)) {
                displayText.setText(String.valueOf((int)result));
            } else {
//                if(!hasDecimalPoint){
//                    displayText.setText(String.valueOf(result));
//                    return;
//                }
                String formatString;
                if(hasDecimalPoint) formatString = "%."+Math.max(getDecimalPlaces(operand1), getDecimalPlaces(operand2)) + "f";
                else formatString = "%.6f";
//                refineDecimal();

                displayText.setText(String.format(formatString, result));
//                displayText.setText(String.valueOf(result));
            }
        }
    }

    private int getDecimalPlaces(double value) {
//        String[] parts = Double.toString(Math.abs(value)).split("\\.");
        return getDecimalPlaces(String.valueOf(value));
    }


    private boolean isInteger(double number) {
        return Math.ceil(number) == Math.floor(number);
    }

    @Override
    public void plus() {
        result = operand1+operand2;
    }

    @Override
    public void minus() {
        result = operand1 - operand2;
    }

    @Override
    public void multiply() {
        result = operand1 * operand2;
    }

    @Override
    public void divide() {
        if (Math.abs(operand2) != 0) { //todo: chang this into small error.
            result = operand1 / operand2;
        } else {
            result = Double.NaN;
        }
    }

    //todo:this is incorrect!
//    @Override
//    protected boolean hasResult(){
//        return result == 0;
//    }
}
