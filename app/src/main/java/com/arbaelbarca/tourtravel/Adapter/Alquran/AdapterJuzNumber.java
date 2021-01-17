package com.arbaelbarca.tourtravel.Adapter.Alquran;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.R;

import java.util.List;

public class AdapterJuzNumber extends RecyclerView.Adapter<AdapterJuzNumber.ViewHolder> {
    Context context;
    List<String> stringList;

    int index = 0;
    ActionClick click;

    public AdapterJuzNumber(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_number_juz, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txtNumber.setText("Juz " + stringList.get(i));

        viewHolder.rlJuzNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                index = i;
                click.clickActionJuz(view, i);
            }
        });

        if (index == i) {
            viewHolder.rlJuzNumber.setBackgroundResource(R.drawable.bg_juz);
        } else {
            viewHolder.rlJuzNumber.setBackgroundResource(R.drawable.bg_juz_grey);

        }
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber;
        RelativeLayout rlJuzNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rlJuzNumber = itemView.findViewById(R.id.rlBgJuzNumber);
            txtNumber = itemView.findViewById(R.id.numberJuz);
        }
    }

    public interface ActionClick {
        void clickActionJuz(View view, int pos);
    }

    public void click(ActionClick actionClick) {
        click = actionClick;
    }
}
