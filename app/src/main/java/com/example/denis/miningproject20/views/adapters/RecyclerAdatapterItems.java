package com.example.denis.miningproject20.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

//    final int VIEW_TYPE_NAME_POOL = 1;
    final int VIEW_TYPE_WORKER = 0;
    private final String LOG_TAG = "MY_LOG: " + RecyclerAdatapterItems.class.getSimpleName();

    List<WorkerEthermine> list = new ArrayList<>();

    public void addItemsToRecycler(List<WorkerEthermine> list){
        this.list.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder => viewType = " + viewType);
        switch (viewType){

            case VIEW_TYPE_WORKER:
                View vWorker = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_workers, parent, false);
                return new WorkerViewHolder(vWorker);

//            case VIEW_TYPE_NAME_POOL:
//                View namePool = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_list_workers, parent, false);
//                return new NamePoolViewHolder(namePool);
        }
        throw new IllegalArgumentException("onCreateViewHolder => No delegate found");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

// TODO change this when use more than one pool

            WorkerViewHolder workerHolder = (WorkerViewHolder) holder;

            WorkerEthermine workerEthermine = list.get(position);

            workerHolder.tvCurrentHashRate.setText(workerEthermine.getHashrate());
            workerHolder.tvNameWorker.setText(workerEthermine.getWorker());
            workerHolder.tvReportedHashRate.setText(workerEthermine.getReportedHashRate());
            workerHolder.tvValidShares.setText(workerEthermine.getValidShares().toString());
            workerHolder.tvInvalidShares.setText(workerEthermine.getInvalidShares().toString());
            workerHolder.tvStaleShares.setText(workerEthermine.getStaleShares().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("h:mm", Locale.getDefault());
            String dateTime = sdf.format(workerEthermine.getWorkerLastSubmitTime());

            workerHolder.tvLastSeen.setText(dateTime);


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void addWorkersEthermineToRecycler(List<WorkerEthermine> list) {
        this.list.addAll(list);
    }


    private static class WorkerViewHolder extends RecyclerView.ViewHolder{

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

//    private class NamePoolViewHolder extends RecyclerView.ViewHolder{
//
//        TextView tvNameWorker;
//
//        NamePoolViewHolder(View itemView) {
//            super(itemView);
//            tvNameWorker = (TextView) itemView.findViewById(R.id.tv_name_worker);
//        }
//    }
}
