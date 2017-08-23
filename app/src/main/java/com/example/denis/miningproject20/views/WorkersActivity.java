package com.example.denis.miningproject20.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.denis.miningproject20.ItemsRecyclerView;
import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.views.adapters.RecyclerAdatapterItems;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;

import java.util.ArrayList;
import java.util.List;

public class WorkersActivity extends AppCompatActivity  {

    List<ItemsRecyclerView> listItemsRecycler = new ArrayList<>();
    boolean flag = false;
    private RecyclerAdatapterItems adapterWorkers;
    private final String MY_LOG = "MY_LOG: " + WorkersActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe){
            Log.d(MY_LOG, "setDisplayHomeAsUpEnabled doesn't work.");
            npe.printStackTrace();
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_for_workers);
        final LinearLayoutManager verticalLayoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        adapterWorkers = new RecyclerAdatapterItems();
        recyclerView.setAdapter(adapterWorkers);

//        if(getIntent().getBooleanExtra(Repository.class.getSimpleName(), false)
//                && Repository.getInstance().getLastResponsesEthermine().get(0) != null){
//
//            Log.d(MY_LOG, "getLastResponseEthermine() -> workers: " + Repository.getInstance().getLastResponsesEthermine().get(0));
//            setNumberOfWorkersInBaseFragment((Repository.getInstance()
//                    .getLastResponsesEthermine().get(0)
//                    .getWorkers()
//                    .getWorkersEthermine()));
//        }
    }

    public void setWorkers(List<WorkerEthermine> workers) {
        adapterWorkers.addItemsToRecycler(workers);
        adapterWorkers.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_update_data:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return true;
        }
    }
}
