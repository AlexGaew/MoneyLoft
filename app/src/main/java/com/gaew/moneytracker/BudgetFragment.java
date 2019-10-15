package com.gaew.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {
    public static final int REQUEST_CODE = 100;
    public static final String COLOR = "color";
    public static final String TYPE = "fragmentType";

    private ItemsAdapter mAdapter;

    private Api mApi;


    public static BudgetFragment newInstancee(int colorId, String type) {

        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle bundle = new Bundle(); //структура для хранения ключей
        bundle.putInt(COLOR, colorId);
        bundle.putString(TYPE, type);
        budgetFragment.setArguments(bundle);
        return budgetFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = ((LoftApp) getActivity().getApplication()).getApi();
        loadItems();
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
                startActivityForResult(new Intent(getActivity(), AddItemActivity.class), REQUEST_CODE);


            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.budget_item_list);


        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mAdapter = new ItemsAdapter(getArguments().getInt(COLOR));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int price;
        try {
            price = Integer.parseInt(data.getStringExtra("price"));

        } catch (NumberFormatException e) {
            price = 0;
        }
        final int realPrice = price;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final String name = data.getStringExtra("name");


            final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN,"");

            Call<Status> call = mApi.addItem(new AddItemRequest(name, getArguments().getString(TYPE), price), token);
            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {

                    if (response.body().getStatus().equals("success")) {

                        mAdapter.addItem(new Item(name, realPrice));
                    }

                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    t.printStackTrace();

                }
            });


        //    mAdapter.addItem(new Item(name, price));

        }
    }
    private void loadItems(){
        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN,"");
       Call<List<Item>> items = mApi.getItems(getArguments().getString(TYPE), token);  //когда пойдут айтемы с сервера мы должны в методе ОНреспонсе заполнить
        // наш адаптер новыми данными

       items.enqueue(new Callback<List<Item>>() {
           @Override
           public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

               List<Item> items = response.body();
               for (Item item: items){
                   mAdapter.addItem(item);




               }

           }

           @Override
           public void onFailure(Call<List<Item>> call, Throwable t) {

           }
       });


    }
}
