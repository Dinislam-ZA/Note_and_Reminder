package com.example.note_application_neko_ru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.note_application_neko_ru.adapter.MainAdapter;
import com.example.note_application_neko_ru.adapter.RcvItemList;
import com.example.note_application_neko_ru.db.AppExecutor;
import com.example.note_application_neko_ru.db.MyDbManager;
import com.example.note_application_neko_ru.db.OnDataRecived;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataRecived {

    private MyDbManager mydbmanager;
    private EditText edTitle,edDisc;
    public RecyclerView RCview;
    private MainAdapter myadapter;
    public FloatingActionButton addButton;

    public void init(){
        mydbmanager = new MyDbManager(this);
        RCview = findViewById(R.id.rcView);
        addButton = findViewById(R.id.add_button);
        myadapter = new MainAdapter(this);
        RCview.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(RCview);
        RCview.setAdapter(myadapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();

        mydbmanager.OpenDb();
        ReadFromDB("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        MenuItem item = menu.findItem(R.id.Search_view);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {

                ReadFromDB(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mydbmanager.CloseDb();
    }

    private void ReadFromDB(final String text){

        AppExecutor.getInstance().getSubFlow().execute(new Runnable() {
            @Override
            public void run() {
                mydbmanager.GetFromDB(text, MainActivity.this);

            }
        });
    }

    public void OnClickAdd(View view){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                myadapter.DeleteItem(viewHolder.getAdapterPosition(),mydbmanager);
            }
        });
    }

    @Override
    public void OnReceivedInterface(List<RcvItemList> list) {

        AppExecutor.getInstance().getMainFlow().execute(new Runnable() {
            @Override
            public void run() {
                myadapter.updateAdapter(list);
            }
        });
    }
}