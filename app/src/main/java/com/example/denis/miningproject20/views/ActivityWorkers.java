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
import com.example.denis.miningproject20.service.MyService;
import com.example.denis.miningproject20.views.adapters.RecyclerAdatapterItems;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ActivityWorkers extends AppCompatActivity implements IWorkersView {

    List<ItemsRecyclerView> listItemsRecycler = new ArrayList<>();
    boolean flag = false;
    private RecyclerAdatapterItems adapterWorkers;
    private final String MY_LOG = "MY_LOG: " + ActivityWorkers.class.getSimpleName();

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

        if(getIntent().getBooleanExtra(RepositoryViews.class.getSimpleName(), false)
                && RepositoryViews.getInstance().getLastResponseEthermine() != null){

            Log.d(MY_LOG, "getLastResponseEthermine() -> workers: " + RepositoryViews.getInstance().getLastResponseEthermine());
            setWorkers((RepositoryViews.getInstance()
                    .getLastResponseEthermine()
                    .getWorkers()
                    .getWorkersEthermine()));
        }
    }

    @Override
    public void setWorkers(List<WorkerEthermine> workers) {
        listItemsRecycler.addAll(workers);
        adapterWorkers.addItemsToRecycler(listItemsRecycler);
        adapterWorkers.notifyDataSetChanged();
    }

    @Subscribe
    public void getNotification(boolean notification){
        if(!flag)
            setWorkers(MyService.Repository.getInstance()
                    .getLastResponseEthermine()
                    .getWorkers()
                    .getWorkersEthermine());

        flag = true;
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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
