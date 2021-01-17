package com.arbaelbarca.tourtravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbaelbarca.tourtravel.Model.ModelMenu;
import com.arbaelbarca.tourtravel.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    List<ModelMenu> menuList;
    ActionClick click;

    public MenuAdapter(Context context, List<ModelMenu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    public void recreate(ModelMenu menu) {
        this.menuList.add(menu);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu_main, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ModelMenu menu = menuList.get(i);
        viewHolder.txtTitle.setText(menu.getTitle());

        Picasso.with(context)
                .load(menu.getImage())
                .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.clickAction(v, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tvName);
            imageView = itemView.findViewById(R.id.civImage);
        }
    }

    public interface ActionClick {
        void clickAction(View view, int pos);
    }

    public void Click(ActionClick actionClick) {
        this.click = actionClick;
    }
}
