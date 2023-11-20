package com.example.javajunction.AdopterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.ModelClass.CoffeeModel;
import com.example.javajunction.R;

import java.util.ArrayList;
import java.util.List;

public class CoffeAdapter extends RecyclerView.Adapter<CoffeAdapter.ViewHolder> {


    private List<CoffeeModel> filteredList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CoffeAdapter(List<CoffeeModel> list, Context context) {
        this.filteredList = list; // Copy the original list to the filtered list initially
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoffeeModel model = filteredList.get(position);
        holder.imageView.setImageResource(model.getImage());
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.price.setText(model.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterByTitle(List<CoffeeModel> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();


    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, desc, price;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageCoffee);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            price = itemView.findViewById(R.id.rice);
        }
    }
}