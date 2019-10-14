package com.gaew.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class BudgetFragment extends Fragment {
    public static final int REQUEST_CODE = 100;
    public static final String COLOR = "color";
    private ItemsAdapter mAdapter;


    public static BudgetFragment newInstancee(int colorId) {

        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle(); //структура для хранения ключей
        bundle.putInt(COLOR, colorId);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null); //имя нового лайаута
        Button callAddButton = view.findViewById(R.id.button_add_item_activity); //ищем дочерние вью во вью самого фрагмента
        callAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),AddItemActivity.class),REQUEST_CODE);



            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.budget_item_list);


        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));

        mAdapter = new ItemsAdapter(getArguments().getInt(COLOR));
        recyclerView.setAdapter(mAdapter);
        mAdapter.addItem(new Item("Молоко",2));
        mAdapter.addItem(new Item("Хлеб",1));
        mAdapter.addItem(new Item("Pice",100));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int price;
        try {
            price=Integer.parseInt(data.getStringExtra("price"));

        }
        catch (NumberFormatException e){
            price=0;
        }
        if (requestCode==REQUEST_CODE && resultCode == Activity.RESULT_OK){
            mAdapter.addItem(new Item(data.getStringExtra("name"),price));

        }
    }
}
