package com.example.calculator.presentation.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.calculator.R;
import com.example.calculator.mvp.presenter.MainPresenter;
import com.example.calculator.mvp.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.txtResult)
    AppCompatTextView txtResult;
    @BindView(R.id.edtInsert)
    AppCompatEditText edtInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        edtInsert.setShowSoftInputOnFocus(false);
    }

    @OnClick({R.id.btnZero, R.id.btnDot, R.id.btnParentheses, R.id.btnResult, R.id.btnOne, R.id.btnTwo,
            R.id.btnThree, R.id.btnPlus, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnMinus, R.id.btnSeven,
            R.id.btnEight, R.id.btnNine, R.id.btnMultiply, R.id.btnClear, R.id.btnDelete, R.id.btnPercent,
            R.id.btnDivide})
    public void onViewClicked(View view) {
        presenter.setPositions(getStartPosition(), getEndPosition());
        switch (view.getId()) {
            case R.id.btnZero:
                presenter.clickZero();
                break;
            case R.id.btnDot:
                presenter.clickDot();
                break;
            case R.id.btnParentheses:
                presenter.clickParentheses();
                break;
            case R.id.btnResult:
                presenter.clickResult();
                break;
            case R.id.btnOne:
                presenter.clickOne();
                break;
            case R.id.btnTwo:
                presenter.clickTwo();
                break;
            case R.id.btnThree:
                presenter.clickThree();
                break;
            case R.id.btnPlus:
                presenter.clickPlus();
                break;
            case R.id.btnFour:
                presenter.clickFour();
                break;
            case R.id.btnFive:
                presenter.clickFive();
                break;
            case R.id.btnSix:
                presenter.clickSix();
                break;
            case R.id.btnMinus:
                presenter.clickMinus();
                break;
            case R.id.btnSeven:
                presenter.clickSeven();
                break;
            case R.id.btnEight:
                presenter.clickEight();
                break;
            case R.id.btnNine:
                presenter.clickNine();
                break;
            case R.id.btnMultiply:
                presenter.clickMultiply();
                break;
            case R.id.btnClear:
                presenter.clickClear();
                break;
            case R.id.btnDelete:
                presenter.clickDelete();
                break;
            case R.id.btnPercent:
                presenter.clickPercent();
                break;
            case R.id.btnDivide:
                presenter.clickDivide();
                break;
        }
    }

    @Override
    public void setResult(String result) {
        txtResult.setText(result);
    }

    @Override
    public void setInsertText(String text) {
        edtInsert.setText(text);
    }

    @Override
    public void insertCursorPosition(int position) {
        int size;
        if (edtInsert.getText() != null)
            size = edtInsert.getText().toString().length();
        else
            size = 0;
        if (position < size)
            edtInsert.setSelection(position);
        else
            edtInsert.setSelection(size);
    }

    private int getStartPosition() {
        return edtInsert.getSelectionStart();
    }

    private int getEndPosition() {
        return edtInsert.getSelectionEnd();
    }
}
