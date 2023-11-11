package com.example.javajunction.AdopterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.R;

import java.util.List;
public class Add_to_cart_adopter extends RecyclerView.Adapter<Add_to_cart_adopter.ViewHolder> {

    private List<Add_To_cart_model> originalList;

    public Add_to_cart_adopter(List<Add_To_cart_model> originalList) {
        this.originalList = originalList;
    }

    public void updateData(List<Add_To_cart_model> newData) {
        originalList.clear();
        originalList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Add_To_cart_model model = originalList.get(position);
        holder.imageView.setImageResource(model.getImage());
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.price.setText(model.getPrice());
    }

    @Override
    public int getItemCount() {
        return originalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, desc, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_pic);
            title = itemView.findViewById(R.id.product_Name);
            desc = itemView.findViewById(R.id.cart_desc);
            price = itemView.findViewById(R.id.cart_price);
        }
    }
}
