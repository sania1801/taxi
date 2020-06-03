package com.example.calculator.mvp.view;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {
    void setResult(String result);

    void setInsertText(String text);

    void insertCursorPosition(int position);
}
