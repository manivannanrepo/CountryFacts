package com.wipro.pes.countryfacts.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wipro.pes.countryfacts.R;
import com.wipro.pes.countryfacts.model.Row;

import java.util.ArrayList;
import java.util.List;

public class CountryFactsRecyclerViewAdapter extends RecyclerView.Adapter<CountryFactsRecyclerViewAdapter.CountryFactsViewHolder> {

    private final Context context;
    private List<Row> rowList = new ArrayList<>();

    public CountryFactsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Row> rowList) {
        this.rowList = rowList;
    }

    @NonNull
    @Override
    public CountryFactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View countryFactsListItemView = layoutInflater.inflate(R.layout.country_facts_list_item, viewGroup, false);
        return new CountryFactsViewHolder(countryFactsListItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryFactsViewHolder countryFactsViewHolder, int position) {
        countryFactsViewHolder.title.setText(rowList.get(position).getTitle());
        countryFactsViewHolder.description.setText(rowList.get(position).getDescription());
        Glide.with(context).load(rowList.get(position).getImageHref()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(countryFactsViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }


    class CountryFactsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView image;

        CountryFactsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            description = itemView.findViewById(R.id.description_tv);
            image = itemView.findViewById(R.id.image_iv);
        }
    }
}
