package com.example.denis.miningproject20.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denis.miningproject20.ItemsRecyclerView;
import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.models.NameOfPools;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by denis on 09.07.17.
 */

public class RecyclerAdatapterItems extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_TYPE_NAME_POOL = 0;
    final int VIEW_TYPE_WORKER = 1;
    private final String LOG = RecyclerAdatapterItems.class.getSimpleName();

    List<ItemsRecyclerView> list = new ArrayList<>();

    public void addItemsToRecycler(List<ItemsRecyclerView> list){
        this.list.addAll(list);
    }
    public void addItemToRecycler(ItemsRecyclerView item){
        list.add(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){

            case VIEW_TYPE_WORKER:
                View worker = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_workers, parent, false);
                return new WorkerViewHolder(worker);

            case VIEW_TYPE_NAME_POOL:
                View namePool = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_workers, parent, false);
                return new NamePoolViewHolder(namePool);
        }
        throw new IllegalArgumentException("onCreateViewHolder => No delegate found");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

// TODO change this when use more than one pool

        if(holder instanceof WorkerViewHolder){

            WorkerEthermine workerEthermine = null;
            WorkerViewHolder workerHolder = (WorkerViewHolder) holder;

            ItemsRecyclerView item = (WorkerEthermine) list.get(position);

            workerEthermine = (WorkerEthermine) item;
            workerHolder.tvCurrentHashRate.setText(workerEthermine.getHashrate());
            workerHolder.tvNameWorker.setText(workerEthermine.getWorker());
            workerHolder.tvReportedHashRate.setText(workerEthermine.getReportedHashRate());
            workerHolder.tvValidShares.setText(workerEthermine.getValidShares().toString());
            workerHolder.tvInvalidShares.setText(workerEthermine.getInvalidShares().toString());
            workerHolder.tvStaleShares.setText(workerEthermine.getStaleShares().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("h:mm", Locale.getDefault());
            String dateTime = sdf.format(workerEthermine.getWorkerLastSubmitTime());

            workerHolder.tvLastSeen.setText(dateTime);


        } else if (holder instanceof NamePoolViewHolder){

            NamePoolViewHolder namePoolHolder = (NamePoolViewHolder) holder;
            NameOfPools namePool = null;

            for(ItemsRecyclerView item : list)
                if(item instanceof NameOfPools) {
                    namePool = (NameOfPools) item;
                    break;
                }

            if(namePool == null)
                throw new RuntimeException("Not finded name of pool");

            namePoolHolder.tvNameWorker.setText(namePool.getNameOfPool());
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position) instanceof NameOfPools)
            return VIEW_TYPE_NAME_POOL;

        else if(list.get(position) instanceof WorkerEthermine)
            return VIEW_TYPE_WORKER;

        throw new IllegalArgumentException("getItemViewType => No delegate found.");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    private class WorkerViewHolder extends RecyclerView.ViewHolder{

        TextView tvNameWorker;
        TextView tvCurrentHashRate;
        TextView tvReportedHashRate;
        TextView tvValidShares;
        TextView tvInvalidShares;
        TextView tvStaleShares;
        TextView tvLastSeen;

        WorkerViewHolder(View itemView) {
            super(itemView);
            this.tvNameWorker = (TextView) itemView.findViewById(R.id.tv_name_worker);
            this.tvCurrentHashRate = (TextView) itemView.findViewById(R.id.tv_current_hashrate_worker);
            this.tvReportedHashRate = (TextView) itemView.findViewById(R.id.tv_reported_hashrate_worker);
            this.tvLastSeen = (TextView) itemView.findViewById(R.id.tv_last_seen);
            this.tvValidShares = (TextView) itemView.findViewById(R.id.tv_number_valid_shares);
            this.tvInvalidShares = (TextView) itemView.findViewById(R.id.tv_number_invalid_workers);
            this.tvStaleShares = (TextView) itemView.findViewById(R.id.tv_number_stale_workers);
        }
    }

    private class NamePoolViewHolder extends RecyclerView.ViewHolder{

        TextView tvNameWorker;

        NamePoolViewHolder(View itemView) {
            super(itemView);
            tvNameWorker = (TextView) itemView.findViewById(R.id.tv_name_worker);
        }
    }
}
