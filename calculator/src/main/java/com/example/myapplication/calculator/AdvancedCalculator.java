package com.example.myapplication.calculator;


import android.annotation.SuppressLint;
import android.widget.TextView;

//import static java.math.BigInteger.ZERO;
import static java.math.BigDecimal.ZERO;

import java.math.BigInteger;
import java.math.BigDecimal;

public class AdvancedCalculator extends Calculator implements Runnable{
//    private static final BigInteger ZERO = new BigInteger("0");
    private BigDecimal operand1, operand2, result;
//    private boolean isDecimal;
//    private String showString;
//    private boolean hasDecimalPoint = false;

    public AdvancedCalculator(TextView displayView, TextView recordText) {
        super(displayView, recordText);
        this.operand1 = ZERO;
        this.operand2 = ZERO;
        this.result = ZERO;
    }




//    @Override
//    protected void handleDigitInput(String digit) {
//        currentInput += digit;
//        displayText.setText(currentInput);
//    }

//    @Override
//    protected void handleDecimalInput() {
//        if (!hasDecimalPoint) {
//            currentInput += ".";
//            displayText.setText(currentInput);
//            hasDecimalPoint = true;
//        }
//    }

    /**
     * 当使用者输入一个操作符的时候，需要进行的操作。
     * +：加法，或者科学输入的正号
     * -：减法，或者科学输入的负号
     * *、÷：运算符
     * 注意，如果之前的计算结果没有被清楚，即使用者没有按下归零，那么结果将会被赋值给operand1，并进行计算。
     * @param operator 运算符
     */
    @Override
    protected void handleOperatorInput(String operator) {
        if(isOver) isOver = false; //continu to calculate
        //已经有输入
        if (!currentInput.isEmpty()) {//if there esitst input, we need to judge whether to operator, in order to avoid like 1+2+3...(continuous add)
            //先前已经有输入的符号，说明这是继续计算。
            if(operator.charAt(0) == '-' && handleMinusOperator())return;
            if(operator.charAt(0) == '+' && handlePlusOperator())return;
            if (!currentOperator.isEmpty()) {//continuous add
                //calculate the result, and update the result to operand1.
                try {
                    handleEqualsInput();//先假装按了等号
                    handleOperatorInput(operator);//然后按下计算符号
                }catch (StackOverflowError e){
                    displayText.setText("TEL");
                    handleActualClearInput();
                }
                return;
            }else{//否则，给operand1赋值。
                operand1 = new BigDecimal(currentInput);
//                recordText.append(operand1.toString());
//                handleEqualsInput();
//                return;
            }

            currentInput = "";
            currentOperator = operator;//update opetator

            recordText.setText(operand1+currentOperator);

            hasDecimalPoint = false;
        }else{//only to change the operator
            if(!currentOperator.isEmpty()) currentOperator = operator;
            recordText.setText(operand1+currentOperator);
        }
//        currentOperator = operator;
    }

    private boolean handlePlusOperator() {
        return handleBasicOperator("+");
    }

    private boolean handleMinusOperator(){
//        int len = currentInput.length();
//        if(currentInput.charAt(len -1) == 'E'){
//            handleDigitInput("-");
//            return true;
//        }else if(currentInput.charAt(len-1)=='-'){
//            if(len < 2) return false;
//            if(currentInput.charAt(len-2) == 'E'){
//                currentInput = currentInput.substring(0,len-1);
//                displayText.setText(currentInput);
//                return true;
//            }
//        }
//        return false;
        return handleBasicOperator("-");
    }

    /**
     *
     * @param o
     * @return
     */
    private boolean handleBasicOperator(String o){
        int len = currentInput.length();
        if(currentInput.charAt(len -1) == 'E'){
            handleDigitInput(o);
            return true;
        }else if(currentInput.charAt(len-1)==o.charAt(0)){
            if(len < 2) return false;
            if(currentInput.charAt(len-2) == 'E'){
                currentInput = currentInput.substring(0,len-1);
                displayText.setText(currentInput);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void handleEqualsInput() {
        if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {
            operand2 = new BigDecimal(currentInput);
            calculateResult(currentOperator);
            //set the record text
            String record = String.format("%s=%s", getRecordOperator(operand1.toString(), operand2.toString(), currentOperator), displayText.getText());
            recordText.setText(record);
            currentInput = result.toString();
            currentOperator = "";
            hasDecimalPoint = new BigDecimal(result.intValue()).compareTo(result) != 0;
            operand1 = result;
            operand2 = ZERO;
        }
    }
    private void setDisplayText(){
        String res = result.toString();
        if(isInt(result)) {
            if (res.length() < 5) {
                if(res.contains("E")) displayText.setText(res);
                else displayText.setText(res.split("\\.")[0]);
            }
            else {



                displayText.setText(toIntegerScienceNumber(res));
//                displayText.setText(result.toEngineeringString());
            }
        } else {
//            displayText.setText(res);
//            if(res.length()<5) displayText.setText(res);
//            else displayText.setText(result.toEngineeringString());
            displayText.setText(toDecimalScienceNumber(res));
        }

//        else if(res.contains(".")) displayText.setText(res);
//        else if(res.contains(".")) displayText.setText(res);

    }


    /**
     * 科学计数输出——小数
     * 将一个长度过长的小数转变为科学输出，避免超过计算器显示范围。
     * @param s
     * @return
     */
    private String toDecimalScienceNumber(String s){

        if(s.contains("E")) {
            return s;
        }

        int len = s.length();
        if(len < 7) return s;

        if(s.charAt(0) !='0') {
            if(len > 7){
                String[] s2 = s.split("\\.");
                int intLen = s2[0].length();
                String withoutDecimal = s2[0] + s2[1];
                BigInteger i1 = new BigInteger(withoutDecimal.substring(0, 3));

                if(withoutDecimal.charAt(4) > '4'){
                    i1 = i1.add(new BigInteger("1"));
                }
                StringBuilder stringBuilder = new StringBuilder(i1.toString());
                stringBuilder.insert(1,'.');
                stringBuilder.append('E').append(intLen-1);
                s = stringBuilder.toString();

            }
            return s;
        }
        s = s.substring(2);
        int l = 0;
        try {
            while (s.charAt(l++) == '0');//得到l的值
        }catch (StackOverflowError e){
            //todo
            e.printStackTrace();
        }

        s = s.substring(l-1);//变成整数
        String res_temp;
        if(s.length() > 4)
            //判断四舍五入
            res_temp = toIntegerScienceNumber(s);
        else
            res_temp = s;
        String[] res = res_temp.split("E");

        StringBuilder sb = new StringBuilder(res[0]);


        if(sb.length() > 1)
            sb.insert(1,'.');
        sb.append('E').append('-').append(l);

        return sb.toString();

    }
    /**
    将一串整数数字设置为科学计数法。请<b>保证输入的是一个仅包含数字的字符串变量</b>
     @param s 输入的字符串
     @return 科学计数法
     <p>
     todo: 对科学计数法进行四舍五入。完成<code>if(sb.charAi(8)>'4')</code>语句。</p>
     */
    @SuppressLint("DefaultLocale")
    private String toIntegerScienceNumber(String s){
        if(s.contains("E")) return s;
        StringBuilder sb = new StringBuilder(s);
        int len = sb.length()-1;
        if(sb.charAt(4) > '4'){
            sb.setLength(4);
            BigDecimal bigDecimal = new BigDecimal(sb.toString());
            bigDecimal = bigDecimal.add(new BigDecimal(1));
            sb = new StringBuilder(bigDecimal.toString());
        }
        else sb.setLength(4);
        sb.insert(1,'.');
        sb.append('E').append('+').append(len);
        return sb.toString();
    }

    @Override
    protected void updateResultDisplayText() {
        if(result == null) displayText.setText("error!");
//        else displayText.setText(String.format("%s",result));
        else setDisplayText();
    }

    @Override
    public void handleActualClearInput() {
        super.handleClearInput();
        this.operand1 = null;
        this.operand2 = null;
        result = ZERO;
        currentOperator = "";
//        hasDecimalPoint = false;
//        isOver = false;
        displayText.setText(STRING_ZERO);
        recordText.setText("");
    }

//    @Override
//    protected void resetOperand() {
//
//    }


    @Override
    public void plus() {
        result = (operand1.add(operand2));
    }

    @Override
    public void minus() {
        result = operand1.subtract(operand2);
//        result = operand1.add(operand2.negate());
    }

    @Override
    public void multiply() {
        if(operand2 == null) recordText.setText("o2");
        if(operand1 == null) recordText.setText(recordText.getText() + " o1");
        result = operand1.multiply(operand2);
    }

    @Override
    public void divide() {
        if(operand2.equals(ZERO)) result = null;
        else {
            divideMini(operand1.toPlainString(), operand2.toPlainString(), isInt(operand1), isInt(operand2));
//            result = operand1.divide(operand2, 2, BigDecimal.ROUND_HALF_UP);
        }

    }

    private void divideMini(String s1, String s2, boolean isInt1, boolean isInt2){
        int a = 0, b = 0;
        if(!isInt1){
            a = s1.split("\\.")[1].length();
        }
        if(!isInt2){
            b = s2.split("\\.")[1].length();
        }
        int l = (Math.max(2, Math.max(a+2,b+2)));
        result = operand1.divide(operand2, l, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void run() {

    }

    @Override
    public void gcd(){
        if(hasDecimalPoint){
            if((!isInt(operand1)) && (!isInt(operand2))) {
                result = ZERO;
                return;
            }
        }
//        if(operand1.toString().contains("E") || operand2.toString().contains("E")) result = ZERO;
        BigInteger integer1 = new BigInteger(operand1.toPlainString().split("\\.")[0]);
        BigInteger integer2 = new BigInteger(operand2.toPlainString().split("\\.")[0]);
        result = new BigDecimal(integer1.gcd(integer2));
    }

    @Override
    public void mode(){
        if(hasDecimalPoint){
            if((!isInt(operand1)) && (!isInt(operand2))) {
                result = ZERO;
                return;
            }
        }

        BigInteger integer1 = new BigInteger(operand1.toPlainString().split("\\.")[0]);
        BigInteger integer2 = new BigInteger(operand2.toPlainString().split("\\.")[0]);
        result = new BigDecimal(integer1.mod(integer2));

    }

    @Override
    protected void pow(){
        BigInteger integer;
        if(operand2.toPlainString().length() > 9){
            result = null;
            return;
        }
        if(isInt(operand2)){
            integer = new BigInteger(operand2.toString());
        }else{
            integer = new BigInteger(operand2.toPlainString().split("\\.")[0]);
        }
        result = operand1.pow(integer.intValue());
    }


    @Override
    public void handleScienceInput(){
        if(currentInput.contains("E")) return;
        handleDigitInput("E");
    }
    private boolean isInt(BigDecimal decimal){
        return new BigDecimal(decimal.toPlainString().split("\\.")[0]).compareTo(decimal) == 0;
    }



}
