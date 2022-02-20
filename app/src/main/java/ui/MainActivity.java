package ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.maru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import di.DI;
import events.DeleteReunionEvent;
import model.Reunion;
import model.Room;
import service.ReunionApiService;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    ReunionListRecyclerViewAdapter mReunionRecycleAdapter;
    ReunionApiService mApiService;
    List<Reunion> mReunion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_filter_list_24));
        mApiService = DI.getReunionApiService();

        mRecyclerView = findViewById(R.id.reunion_list);
        mReunionRecycleAdapter = new ReunionListRecyclerViewAdapter(mReunion);
        mRecyclerView.setAdapter(mReunionRecycleAdapter);

        mReunion = new ArrayList<>(mApiService.getReunion());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new ReunionListRecyclerViewAdapter(mReunion));


        mFloatingActionButton = findViewById(R.id.add_reunion);
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddReunionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReunion.clear();
        mReunion.addAll(mApiService.getReunion());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.filter_menu, menu);

    MenuItem menuItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
    searchView.setQueryHint("taper le nom de la salle rechercher");

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mReunion.clear();
            mReunion.addAll(mApiService.getReunionRoomFilter(newText));
            mRecyclerView.getAdapter().notifyDataSetChanged();
            return false;
        }
    });

    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filterDate:
                dateDialog();
                return true;
            case R.id.filterdefaut:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetFilter() {
        mReunion.clear();
        mReunion.addAll(mApiService.getReunion());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void salleDialog() {

    }

    private void initList() {
        mRecyclerView.setAdapter(new ReunionListRecyclerViewAdapter(mApiService.getReunion()));
    }

    private void dateDialog() {
        int selectedYear = 2022;
        int selectedMonth = 0;
        int selectedDayOfMonth = 20;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mReunion.clear();
                mReunion.addAll(mApiService.getReunionDateFilter(cal.getTime()));
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

        };

// Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

// Show
        datePickerDialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteReunionEvent(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        initList();
    }




}