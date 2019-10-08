package com.gaew.moneytracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    private ItemsAdapter mAdapter;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button callAddButton = findViewById(R.id.button_add_item_activity);
        callAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,AddItemActivity.class),REQUEST_CODE);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.budget_item_list);



       recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        mAdapter = new ItemsAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.addItem(new Item("Молоко",70));
        mAdapter.addItem(new Item("hbr",70));
        mAdapter.addItem(new Item("wev",70));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int price;
        try {
            price=Integer.parseInt(data.getStringExtra("price"));

        }
        catch (NumberFormatException e){
            price=0;
        }
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            mAdapter.addItem(new Item(data.getStringExtra("name"),price));

        }
    }
}
