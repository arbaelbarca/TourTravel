package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.R;
import com.arbaelbarca.tourtravel.ResponseNearby.ResultsItem;

import java.util.List;

public class AdapterPlaceNearby extends RecyclerView.Adapter<AdapterPlaceNearby.ViewHolder> {
    Context context;
    List<ResultsItem> resultsItems;

    public AdapterPlaceNearby(Context context, List<ResultsItem> resultsItems) {
        this.context = context;
        this.resultsItems = resultsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_place, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ResultsItem item = resultsItems.get(i);
        viewHolder.txtTitle.setText(item.getName());
        viewHolder.txtSnippet.setText(item.getVicinity());


    }

    @Override
    public int getItemCount() {
        return resultsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtSnippet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSnippet = itemView.findViewById(R.id.txtSnippetPlace);
            txtTitle = itemView.findViewById(R.id.txtTitlePlace);
        }
    }


}
