package com.gaew.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mPriceEditText;

    private String mPrice;
    private String mName;
    private Button mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mPriceEditText = findViewById(R.id.price_edit_text);
        mNameEditText = findViewById(R.id.name_edit_text);
        mNameEditText.addTextChangedListener(new TextWatcher() {  // add TextWatcher, change button color
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mName = editable.toString();
                checkEditHasText();

            }
        });
        mPriceEditText = findViewById(R.id.price_edit_text);
        mPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPrice=editable.toString();
                checkEditHasText();

            }
        });


        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(mName)&& !TextUtils.isEmpty(mPrice)){
                    setResult(RESULT_OK,  new Intent().putExtra("name",mName).putExtra("price",mPrice));
                    finish();

                }

            }
        });

    }
    public void checkEditHasText(){
        mAddButton.setEnabled(!TextUtils.isEmpty(mName)&& !TextUtils.isEmpty(mPrice));

    }
}

