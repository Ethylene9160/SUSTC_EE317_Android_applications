package com.example.myapplication.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class Calculator implements CalculatorTool{

    final String STRING_ZERO = "0";
    protected boolean hasDecimalPoint = false;
    protected TextView displayText, recordText;
    protected String currentInput = "";
    protected String currentOperator = "";
//    protected int currentDecimalPlace;

    protected boolean isOver;

    /**
     * 当<code>onButtonClick()</code>方法中，识别到+、-、*、/、gcd、mod后，
     * 将会调用这个方法。
     * @param operator 运算符
     */
    protected abstract void handleOperatorInput(String operator);
    protected abstract void handleEqualsInput();
    protected abstract void updateResultDisplayText();
    public abstract void handleActualClearInput();
    public abstract void handleScienceInput();
//    @Deprecated
//    protected abstract void resetOperand();
//    @Deprecated
//    protected abstract boolean hasResult();

    public Calculator(TextView displayText, TextView recordText){
        this.displayText = displayText;
        this.recordText = recordText;
    }

    public void delete(){
        if(!currentInput.isEmpty()){
            if(currentInput.length()==1) {
                currentInput = STRING_ZERO;
                displayText.setText(currentInput);
                return;
            }
            if(currentInput.charAt(currentInput.length()-1) == '.') hasDecimalPoint = false;
            currentInput = currentInput.substring(0,currentInput.length()-1);
            displayText.setText(currentInput);
//            displayText.setText(displayText.getText().toString().substring(0,displayText.getText().length()-2));
        }
//        displayText.setText("0000");
    }

    /**
     * 输入。
     * @param digit 输入
     */
    protected void handleDigitInput(String digit){
        if(isOver) handleActualClearInput();

        currentInput += digit;
        displayText.setText(currentInput);
    }

    /**
     * 输入小数点。此处在避免重复输入小数点，也要防止使用“BACK“键后再点击小数点的失效。
     */
    protected void handleDecimalInput() {
        if(isOver){
            handleClearInput();
        }
        if (!hasDecimalPoint) {
            if(currentInput.isEmpty()) currentInput = "0.";
            else currentInput += ".";
            displayText.setText(currentInput);
            hasDecimalPoint = true;
        }
    }

    /**
     * 查看按下了哪个按钮，并对对应的按钮进行操作
     * @param view
     */
    public void onButtonClick(View view){
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
                if(!currentInput.isEmpty() && !currentInput.contains(".") && currentInput.charAt(0)=='0') currentInput = "";
                handleDigitInput(((Button)view).getText().toString());
                break;
            case R.id.buttonE:
                handleScienceInput();
                break;
            case R.id.buttonDecimal:
                handleDecimalInput();
                break;

            case R.id.buttonMultiply:
            case R.id.buttonMinus:
            case R.id.buttonDivide:
            case R.id.buttonPlus:
            case R.id.buttonGetCommonDivisor:
            case R.id.buttonMod:
            case R.id.buttonPow:
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
            case R.id.buttonConvert:
                if(currentInput.isEmpty())return;
                if (currentInput.charAt(0) == '-') {
                    currentInput = currentInput.substring(1);
                }else currentInput = "-"+currentInput;
                displayText.setText(currentInput);
                break;

            case R.id.buttonBack:
                delete();
                break;
        }
    }

    /**
     * 找小数点的位置
     * @param value
     * @return
     */
    protected int getDecimalPlaces(String value){
        String[] parts = value.split("\\.");
        if (parts.length == 1) {
            return 0;
        } else {
            return parts[1].length();
        }
    }

    /**
     * 计算结果
     * @param operator
     */
    protected void calculateResult(String operator){
        switch (operator){
            case "+":
                plus();
                break;

            case "-":
                minus();
                break;

            case "×":
                multiply();
                break;
            case "÷":
                divide();
                break;

            case "MOD":
                mode();
                break;

            case "GCD":
                gcd();
                break;


            case "POW":
                pow();
                break;


            default:
                break;
        }
        updateResultDisplayText();
        isOver = true;
    }

    /**
     * 按钮C的操作：对当前输入进行清空，但是不改变之前的输入。
     */
    protected void handleClearInput(){
        currentInput = "";
//        currentDecimalPlace = 0;
        isOver = false;
        hasDecimalPoint = false;
//        currentOperator = "";
        displayText.setText("0");
    }

    protected void pow(){

    }

    public void mode(){

    }

    public void gcd(){

    }

    /**
     * 判断是否是整数
     * @param bigDecimal
     * @return
     */
//    @Deprecated
    private boolean isInt(BigDecimal bigDecimal){
        return new BigDecimal(bigDecimal.intValue()).compareTo(bigDecimal) == 0;
    }

//    @Deprecated
    private boolean isInputInteger(String input){
        int len = input.length();
        for (int i = 0; i < len; ++i) {
            if(input.charAt(i) == '0') return false;
        }
        return true;
    }

    /**
     * 更新recordText, 在计算出结果后。
     * @param o1 operand1
     * @param o2 operand2
     * @param currentOperator operator
     * @return
     */
    protected static String getRecordOperator(String o1, String o2, String currentOperator){
        switch (currentOperator){
            case "POW":
                return String.format("%s^%s",o1,o2);
            case "GCD":
                return String.format("(%s, %s)", o1, o2);
            case "MOD":
                return String.format("%s|%s", o1, o2);
        }
        return String.format("%s%s%s", o1,currentOperator,o2);
    }
}
