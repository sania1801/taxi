package com.example.calculator.common.util;

import com.example.calculator.common.constant.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CalculationUtils {

    public static float calculateResult(String equation) {
        try {
            String[] parts = equation.split("\\)");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].replace("(", "");
            }
            return processPartsEquation(parts);
        } catch (Exception ex) {
            return Constants.DEFAULT_RESULT;
        }
    }

    private static float processPartsEquation(String[] parts) {
        float[] sumParts = new float[parts.length];
        List<String> lastOperators = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty())
                break;
            StringBuilder part = new StringBuilder(parts[i]);
            if (!Character.isDigit(part.charAt(0))) {
                lastOperators.add(String.valueOf(part.charAt(0)));
                part.delete(0, 1);
            }
            sumParts[i] = getPartResult(part);
        }
        int partIndex = 0;
        float result = sumParts[partIndex];
        for (String operator : lastOperators) {
            partIndex++;
            if (partIndex < sumParts.length) {
                result = getOperationResult(result, sumParts[partIndex], operator);
            }
        }
        return result;
    }

    private static float getPartResult(StringBuilder part) {
        List<String> operatorList = new ArrayList<>();
        List<String> operandList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(part.toString(), "+-", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("+-".contains(token)) {
                operatorList.add(token);
            } else {
                operandList.add(token);
            }
        }
        List<Float> innerOperands = new ArrayList<>();
        for (int i = 0; i < operandList.size(); i++) {
            innerOperands.add(processMultiplyDivide(operandList.get(i)));
        }
        int operandIndex = 0;
        float result = innerOperands.get(operandIndex);
        for (String operator : operatorList) {
            operandIndex++;
            if (operandIndex < operandList.size()) {
                result = getOperationResult(result, innerOperands.get(operandIndex), operator);
            }
        }
        return result;
    }

    private static float processMultiplyDivide(String text) {
        List<String> operatorList = new ArrayList<>();
        List<String> operandList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(text, "/*", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("/*".contains(token)) {
                operatorList.add(token);
            } else {
                operandList.add(token);

            }
        }
        int operandIndex = 0;
        float result = convertToFloat(operandList.get(operandIndex));
        for (String operator : operatorList) {
            operandIndex++;
            if (operandIndex < operandList.size()) {
                result = getOperationResult(result, convertToFloat(operandList.get(operandIndex)), operator);
            }
        }
        return result;
    }

    private static float convertToFloat(String number) {
        if (number.contains("%")) {
            number = number.replace("%", "");
            return Float.parseFloat(number) / 100;
        } else {
            return Float.parseFloat(number);
        }
    }

    private static float getOperationResult(float first, float second, String operator) {
        float result = first;
        switch (operator) {
            case "*":
                result *= second;
                break;
            case "/":
                result /= second;
                break;
            case "+":
                result += second;
                break;
            case "-":
                result -= second;

        }
        return result;
    }
}
