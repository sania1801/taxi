package com.example.calculator.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.calculator.common.util.CalculationUtils;
import com.example.calculator.mvp.view.MainView;

import static com.example.calculator.common.constant.Constants.DEFAULT_RESULT;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private StringBuilder insertText = new StringBuilder();
    private Float result = DEFAULT_RESULT;
    private int positionStart = 0;
    private int positionEnd = 0;

    public void clickZero() {
        addSymbol("0");
    }

    public void clickOne() {
        addSymbol("1");
    }

    public void clickTwo() {
        addSymbol("2");
    }

    public void clickThree() {
        addSymbol("3");
    }

    public void clickFour() {
        addSymbol("4");
    }

    public void clickFive() {
        addSymbol("5");
    }

    public void clickSix() {
        addSymbol("6");
    }

    public void clickSeven() {
        addSymbol("7");
    }

    public void clickEight() {
        addSymbol("8");
    }

    public void clickNine() {
        addSymbol("9");
    }

    public void clickDot() {
        addSymbol(".");
    }

    public void clickPlus() {
        addSymbol("+");
    }

    public void clickMinus() {
        addSymbol("-");
    }

    public void clickMultiply() {
        addSymbol("*");
    }

    public void clickDivide() {
        addSymbol("/");
    }

    public void clickResult() {
        insertText = new StringBuilder(String.valueOf(result));
        getViewState().setInsertText(insertText.toString());
        result = DEFAULT_RESULT;
        setResult();
        getViewState().insertCursorPosition(insertText.length());
    }

    public void clickClear() {
        result = DEFAULT_RESULT;
        insertText = new StringBuilder();
        setText();
    }

    public void clickParentheses() {
        if (isFirst()) {
            replaceSymbol("(");
        } else {
            if (!isPrevDigit()) {
                if (isPrevEndParentheses()) {
                    positionEnd++;
                    replaceSymbol("+(");
                } else {
                    replaceSymbol("(");
                }
            } else {
                int start = insertText.lastIndexOf("(");
                int end = insertText.lastIndexOf(")");
                if (start < 0) {
                    if (isNextDigit()) {
                        insertText.insert(positionStart, "+(");
                        setText();
                        getViewState().insertCursorPosition(positionStart + 3);
                    } else {
                        positionEnd++;
                        replaceSymbol("+(");
                    }
                } else {
                    if (start > end) {
                        replaceSymbol(")");
                    } else {
                        replaceSymbol("+(");
                    }
                }
            }
        }
    }

    public void clickDelete() {
        if (positionStart != 0)
            positionStart--;
        insertText.delete(positionStart, positionEnd);
        setText();
        getViewState().insertCursorPosition(positionStart);
    }

    public void clickPercent() {
        if (positionStart != 0) {
            if (isPrevDigit()) {
                if (!isLast() && isNextDigit())
                    replaceSymbol("%*");
                else
                    replaceSymbol("%");
            }
        }
    }

    public void setPositions(int positionStart, int positionEnd) {
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
    }

    private void addSymbol(String symbol) {
        if (isDigit(symbol) || symbol.equals(".")) {
            if (positionStart != 0 && isPrevPercent()) {
                symbol = "*" + symbol;
                positionEnd++;
            }
            replaceSymbol(symbol);
        } else {
            insertSymbol(symbol);
        }
    }

    private void insertSymbol(String symbol) {
        if (!isFirst()) {
            if (!isPrevDigit()) {
                positionStart--;
            }
            if (!isCurrentDigit())
                if (!isNextDigit()) {
                    positionEnd++;
                }
            replaceSymbol(symbol);
        }
    }

    private void replaceSymbol(String symbol) {
        insertText.replace(positionStart, positionEnd, symbol);
        setText();
        if (!isLast())
            positionEnd++;
        getViewState().insertCursorPosition(positionEnd);
    }

    private void setResult() {
        if (result == DEFAULT_RESULT)
            getViewState().setResult("");
        else {
            getViewState().setResult(String.valueOf(result));
        }
    }

    private void setText() {
        String text = insertText.toString();
        getViewState().setInsertText(text);
        if (!text.isEmpty())
            result = CalculationUtils.calculateResult(text);
        setResult();
    }

    private boolean isDigit(String symbol) {
        return Character.isDigit(symbol.charAt(0));
    }

    private boolean isPrevDigit() {
        if (isPrevPercent())
            return true;
        else 
            return Character.isDigit(insertText.charAt(positionStart - 1));
    }

    private boolean isNextDigit() {
        if (isLast())
            return true;
        else if (positionEnd == insertText.length() - 1)
            return true;
        else
            return Character.isDigit(insertText.charAt(positionEnd + 1));
    }

    private boolean isPrevEndParentheses() {
        return insertText.charAt(positionStart - 1) == ')';
    }

    private boolean isPrevPercent() {
        return insertText.charAt(positionStart - 1) == '%';
    }

    private boolean isCurrentDigit() {
        if (isLast())
            return true;
        else
            return Character.isDigit(insertText.charAt(positionEnd));
    }

    private boolean isLast() {
        return positionEnd == insertText.length();
    }

    private boolean isFirst() {
        return positionStart == 0;
    }
}
