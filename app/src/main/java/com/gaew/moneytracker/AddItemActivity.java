package com.gaew.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mPriceEditText = findViewById(R.id.price_edit_text);
        mNameEditText = findViewById(R.id.name_edit_text);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEditText.getText().toString();
                String price = mPriceEditText.getText().toString();
                if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(price)){
                    setResult(RESULT_OK,  new Intent().putExtra("name",name).putExtra("price",price));
                    finish();

                }

            }
        });

    }
}

