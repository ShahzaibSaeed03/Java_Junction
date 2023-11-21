package com.example.javajunction.AdopterClass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javajunction.ModelClass.Add_To_cart_model;
import com.example.javajunction.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Add_to_cart_adopter extends RecyclerView.Adapter<Add_to_cart_adopter.ViewHolder> {

    private List<Add_To_cart_model> originalList;

    AdapterView.OnItemClickListener mListener;

    public Add_to_cart_adopter(List<Add_To_cart_model> originalList) {
        this.originalList = originalList;
    }

    public void updateData(List<Add_To_cart_model> newData) {
        if (originalList != null) {
            originalList.clear();
            originalList.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (originalList != null && position < originalList.size()) {
            Add_To_cart_model model = originalList.get(position);
            if (model != null) {
                holder.imageView.setImageResource(model.getImage());
                holder.title.setText(model.getTitle());
                holder.desc.setText(model.getDescription());
                holder.price.setText(model.getPrice());

                final int finalPosition = position; // Declare finalPosition

                holder.Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.imageView.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Are you sure you want to delete the item from the cart?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item from both the list and the Firebase Realtime Database
                                removeItem(finalPosition); // Use finalPosition
                            }

                            private void removeItem(int position) {
                                // Check if the position is within the bounds of the list
                                if (finalPosition >= 0 && finalPosition < originalList.size()) {
                                    // Get the key of the item in the Firebase Realtime Database
                                    String itemKey = originalList.get(finalPosition).getKey();

                                    // Check if itemKey is not null before using it in the path
                                    if (itemKey != null && !itemKey.isEmpty()) {
                                        // Remove the item from Firebase Realtime Database
                                        FirebaseDatabase.getInstance().getReference().child("cart")
                                                .child(itemKey).removeValue();

                                        // Remove the item from the list
                                        originalList.remove(finalPosition);

                                        // Notify the adapter about the data change
                                        notifyItemRemoved(finalPosition);
                                    } else {
                                        // Handle the case where itemKey is null or empty
                                        Log.e("RemoveItem", "ItemKey is null or empty for position: " + finalPosition);
                                    }
                                }
                            }

                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Check if the position is within the bounds of the list
                                if (finalPosition >= 0 && finalPosition < originalList.size()) {
                                    // Remove the item from the list
                                    originalList.remove(finalPosition);
                                    // Notify the adapter about the data change
                                    notifyItemRemoved(finalPosition);

                                    // Optionally, you can save the updated list to SharedPreferences here.

                                    // Get the key of the item in the Firebase Realtime Database
                                    String itemKey = originalList.get(finalPosition).getKey(); // Use finalPosition

                                    // Remove the item from Firebase Realtime Database
                                    FirebaseDatabase.getInstance().getReference().child("cart")
                                            .child(itemKey).removeValue();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return originalList != null ? originalList.size() : 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView , Delete;
        TextView title, desc, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_pic);
            title = itemView.findViewById(R.id.product_Name);
            desc = itemView.findViewById(R.id.cart_desc);
            price = itemView.findViewById(R.id.cart_price);
            Delete = itemView.findViewById(R.id.Delete_item);
        }
    }

}
